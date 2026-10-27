package io.github.bonsaistudi0s.crittersandcompanions.common.mixin;

import net.minecraft.core.UUIDUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import io.github.bonsaistudi0s.crittersandcompanions.common.config.CACCommonConfig;
import io.github.bonsaistudi0s.crittersandcompanions.common.extension.ISilkLeashState;
import io.github.bonsaistudi0s.crittersandcompanions.common.item.PearlNecklaceItem;
import io.github.bonsaistudi0s.crittersandcompanions.common.item.SilkLeashItem;
import io.github.bonsaistudi0s.crittersandcompanions.common.network.CACPacketHandler;
import io.github.bonsaistudi0s.crittersandcompanions.common.network.ClientboundSilkLeashStatePacket;
import io.github.bonsaistudi0s.crittersandcompanions.common.registry.CACBlocks;
import io.github.bonsaistudi0s.crittersandcompanions.common.registry.CACItems;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.objects.ObjectObjectImmutablePair;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements ISilkLeashState {

    @Unique
    private final Pair<Set<UUID>, Set<UUID>> savedLeashState = new ObjectObjectImmutablePair<>(new HashSet<>(), new HashSet<>());

    @Unique
    private boolean needsLeashStateLoad;

    @Unique
    private int leashStateLoadDelay;

    @Unique
    private final Set<LivingEntity> leashingEntities = new HashSet<>();

    @Unique
    private final Set<LivingEntity> leashedByEntities = new HashSet<>();

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(at = @At("TAIL"), method = "addAdditionalSaveData")
    private void onAddAdditionalSaveData(ValueOutput output, CallbackInfo callbackInfo) {
        if (!this.level().isClientSide()) {
            output.store("LeashingEntities", UUIDUtil.CODEC.listOf(), getLeashingEntities().stream().map(Entity::getUUID).toList());
            output.store("LeashedByEntities", UUIDUtil.CODEC.listOf(), getLeashedByEntities().stream().map(Entity::getUUID).toList());
        }
    }

    @Inject(at = @At("TAIL"), method = "readAdditionalSaveData")
    private void onReadAdditionalSaveData(ValueInput input, CallbackInfo callbackInfo) {
        if (!this.level().isClientSide()) {
            List<UUID> leashingEntitiesList = input.read("LeashingEntities", UUIDUtil.CODEC.listOf()).orElse(List.of());
            this.savedLeashState.first().addAll(leashingEntitiesList);

            List<UUID> leashedByEntitiesList = input.read("LeashedByEntities", UUIDUtil.CODEC.listOf()).orElse(List.of());
            this.savedLeashState.second().addAll(leashedByEntitiesList);

            if (!leashingEntitiesList.isEmpty() || !leashedByEntitiesList.isEmpty()) {
                this.needsLeashStateLoad = true;
            }
        }
    }

    @Inject(at = @At("TAIL"), method = "tick")
    private void onTick(CallbackInfo callbackInfo) {
        if (!this.level().isClientSide()) {
            if (this.needsLeashStateLoad) {
                leashStateLoadDelay++;

                if (leashStateLoadDelay >= 20) {
                    Set<LivingEntity> leashingEntities = getLeashingEntities();
                    Set<LivingEntity> leashedByEntities = getLeashedByEntities();

                    for (UUID uuid : this.savedLeashState.first()) {
                        LivingEntity entity = (LivingEntity) ((ServerLevel) this.level()).getEntity(uuid);
                        if (entity != null) {
                            leashingEntities.add(entity);
                        }
                    }
                    this.savedLeashState.first().clear();

                    for (UUID uuid : this.savedLeashState.second()) {
                        LivingEntity entity = (LivingEntity) ((ServerLevel) this.level()).getEntity(uuid);
                        if (entity != null) {
                            leashedByEntities.add(entity);
                        }
                    }
                    this.savedLeashState.second().clear();

                    this.sendLeashState();
                    this.needsLeashStateLoad = false;
                    leashStateLoadDelay = 0;
                }
            }

            int entitiesBefore = getLeashingEntities().size();

            getLeashingEntities().removeIf(leashedEntity -> {
                Vec3 distance = this.position().subtract(leashedEntity.position());
                double distanceSqr = distance.lengthSqr();

                if (distanceSqr > 14.0D) {
                    leashedEntity.setDeltaMovement(leashedEntity.getDeltaMovement().add(distance
                            .scale(0.1D)
                            .add(0.0D, 0.1D, 0.0D)));

                    if (distanceSqr > 28.0D) {
                        ISilkLeashState leashedLeashState = (ISilkLeashState) leashedEntity;
                        leashedLeashState.getLeashedByEntities().remove(this);
                        leashedLeashState.sendLeashState();
                        return true;
                    }

                }

                return false;
            });

            int unleashedEntities = entitiesBefore - getLeashingEntities().size();

            if (unleashedEntities > 0) {
                ItemEntity leadEntity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), new ItemStack(CACItems.SILK_LEAD.get(), unleashedEntities));
                this.level().addFreshEntity(leadEntity);
                this.sendLeashState();
            }
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;broadcastEntityEvent(Lnet/minecraft/world/entity/Entity;B)V", ordinal = 0, shift = At.Shift.BY, by = 1), method = "die")
    private void onDie(DamageSource source, CallbackInfo callbackInfo) {
        int unleashedStates = 0;
        unleashedStates += Math.max(0, SilkLeashItem.updateLeashStates((LivingEntity) ((Entity) this), null) - 1);
        unleashedStates += Math.max(0, SilkLeashItem.updateLeashStates(null, (LivingEntity) ((Entity) this)) - 1);
        if (unleashedStates > 0) {
            ItemEntity leadEntity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), new ItemStack(CACItems.SILK_LEAD.get(), unleashedStates));
            this.level().addFreshEntity(leadEntity);
        }
    }

    @Unique
    private boolean cac$isOnSeaBunnySlime() {
        return this.getBlockStateOn().is(CACBlocks.SEA_BUNNY_SLIME_BLOCK.get());
    }

    // Sea bunny slime blocks let entities move as if they were swimming.
    @Inject(at = @At("RETURN"), method = "shouldTravelInFluid(Lnet/minecraft/world/level/material/FluidState;)Z", cancellable = true)
    private void travelInFluidOnSlime(FluidState fluidState, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValueZ() && cac$isOnSeaBunnySlime()) {
            cir.setReturnValue(true);
        }
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInWater()Z"), method = "travelInFluid(Lnet/minecraft/world/phys/Vec3;)V")
    private boolean redirectIsInWater(LivingEntity entity) {
        return this.isInWater() || cac$isOnSeaBunnySlime();
    }

    @ModifyVariable(at = @At(value = "LOAD"), method = "aiStep()V", ordinal = 0)
    private boolean modifyWaterFlag(boolean flag) {
        return flag || cac$isOnSeaBunnySlime();
    }

    @ModifyArg(method = "travelInWater", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;moveRelative(FLnet/minecraft/world/phys/Vec3;)V", ordinal = 0))
    private float modifySwimSpeed(float original) {
        return PearlNecklaceItem.getWearing(this)
                .map(PearlNecklaceItem::getLevel)
                .map(level -> original + (float) (original * (level * CACCommonConfig.HANDLER.instance().necklace.swimSpeed)))
                .orElse(original);
    }

    @Override
    public void sendLeashState() {
        CACPacketHandler.sendToTrackingAndSelf(this,
                new ClientboundSilkLeashStatePacket(
                        new ClientboundSilkLeashStatePacket.LeashData(
                                this.getId(),
                                new IntArrayList(getLeashingEntities().stream().mapToInt(Entity::getId).toArray()),
                                new IntArrayList(getLeashedByEntities().stream().mapToInt(Entity::getId).toArray())
                        )
                ));
    }

    @Override
    public Set<LivingEntity> getLeashingEntities() {
        return this.leashingEntities;
    }

    @Override
    public Set<LivingEntity> getLeashedByEntities() {
        return this.leashedByEntities;
    }

}
