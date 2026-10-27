package io.github.bonsaistudi0s.crittersandcompanions.common.entity;

import com.geckolib.animation.object.LoopType;

import com.geckolib.animatable.manager.AnimatableManager;

import com.geckolib.animation.object.PlayState;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.animal.rabbit.Rabbit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import org.jetbrains.annotations.Nullable;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.brain.behaviour.BabyHealthPenaltyBehaviour;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.brain.behaviour.Behaviours;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.brain.behaviour.TameableBehaviour;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.brain.behaviour.VariantBehaviour;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.brain.goal.*;
import io.github.bonsaistudi0s.crittersandcompanions.common.registry.AnimalTags;
import io.github.bonsaistudi0s.crittersandcompanions.common.registry.CACEntities;
import io.github.bonsaistudi0s.crittersandcompanions.common.registry.CACSounds;
import io.github.bonsaistudi0s.crittersandcompanions.common.util.EntityUtils;
import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animation.*;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.util.GeckoLibUtil;

public class FerretEntity extends TamableAnimal implements GeoEntity {

    private static final EntityDataAccessor<Boolean> SLEEPING = SynchedEntityData.defineId(
            FerretEntity.class,
            EntityDataSerializers.BOOLEAN
    );
    private static final EntityDataAccessor<Boolean> DIGGING = SynchedEntityData.defineId(
            FerretEntity.class,
            EntityDataSerializers.BOOLEAN
    );
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(
            FerretEntity.class,
            EntityDataSerializers.INT
    );
    private static final EntityDataAccessor<Integer> DATA_COLLAR_COLOR = SynchedEntityData.defineId(
            FerretEntity.class,
            EntityDataSerializers.INT
    );

    private static final int TRANSITION_TICK_TIME = 4;
    private static final RawAnimation DIG_ANIM = RawAnimation.begin().then("dig", LoopType.PLAY_ONCE);
    private static final RawAnimation SIT_ANIM = RawAnimation.begin().thenLoop("sit");
    private static final RawAnimation SLEEP_ANIM = RawAnimation.begin().thenLoop("sleep");
    private static final RawAnimation SWIM_ANIM = RawAnimation.begin().thenLoop("swim");
    private static final RawAnimation RUN_ANIM = RawAnimation.begin().thenLoop("run");
    private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");

    public static final AnimalTags TAGS = AnimalTags.create(CACEntities.FERRET.getKey());
    public static final TagKey<Block> DIG_GROUNDS_TAG = TagKey.create(
            Registries.BLOCK,
            CrittersAndCompanions.createId("ferret_dig_grounds")
    );

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    protected BlockState stateToDig;
    protected int digCooldown;

    public FerretEntity(EntityType<? extends FerretEntity> entityType, Level level) {
        super(entityType, level);
        EntityUtils.applyAwarenessMaluses(this);
    }

    @Override
    public void registerBehaviours(Behaviours behaviours) {
        behaviours.add(new VariantBehaviour(this, VARIANT, 2));
        behaviours.add(new TameableBehaviour(this, TAGS));
        behaviours.add(new BabyHealthPenaltyBehaviour(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.34D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SLEEPING, false);
        builder.define(DIGGING, false);
        builder.define(DATA_COLLAR_COLOR, DyeColor.RED.getId());
    }

    @Nullable
    public BlockState getDiggingState() {
        return stateToDig;
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new TamableAnimal.TamableAnimalPanicGoal(1.5D));
        goalSelector.addGoal(2, new FerretDigGoal(this));
        goalSelector.addGoal(3, new SitWhenOrderedToGoal(this));
        goalSelector.addGoal(4, new TamableRelaxOnOwnerGoal<>(this, FerretEntity::isSleeping, this::setSleeping));
        goalSelector.addGoal(
                5,
                new AvoidEntityGoal<>(
                        this,
                        LivingEntity.class,
                        8.0F,
                        1.6D,
                        1.4D,
                        (livingEntity) -> livingEntity.is(this.getLastHurtByMob()) && !livingEntity.is(this.getOwner())
                )
        );
        goalSelector.addGoal(6, new BreedGoal(this, 1.1D));
        goalSelector.addGoal(7, new MeleeAttackGoal(this, 1.2D, true));
        goalSelector.addGoal(8, TAGS.temptGoal(this));
        goalSelector.addGoal(9, new TamableLieOnBedGoal<>(this, 1.1D, 8, FerretEntity::isSleeping, this::setSleeping));
        goalSelector.addGoal(10, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F));
        goalSelector.addGoal(11, new TameableFollowParentGoal(this, 1.0D));
        goalSelector.addGoal(12, new TamableSitOnBlockGoal(this, 1.1D));
        goalSelector.addGoal(13, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        goalSelector.addGoal(14, TAGS.sittingTemptGoal(this));
        goalSelector.addGoal(15, new LookAtPlayerGoal(this, Player.class, 8.0F));
        goalSelector.addGoal(16, new RandomLookAroundGoal(this));

        targetSelector.addGoal(
                0,
                new NearestAttackableTargetGoal<>(
                        this,
                        Animal.class,
                        10,
                        false,
                        false,
                        (livingEntity, level) -> {
                            if (livingEntity instanceof TamableAnimal tamableAnimal && tamableAnimal.isTame()) {
                                return false;
                            }

                            return livingEntity instanceof Chicken || livingEntity instanceof Rabbit;
                        }
                )
        );
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putBoolean("Sleeping", isSleeping());
        if (getCollarColor() != null) {
            output.putInt("CollarColor", getCollarColor().getId());
        }
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.setSleeping(input.getBooleanOr("Sleeping", false));
        input.getInt("CollarColor").ifPresent(id -> this.setCollarColor(DyeColor.byId(id)));
    }

    @Override
    protected int getBaseExperienceReward(ServerLevel level) {
        return this.random.nextInt(2, 5);
    }

    @Override
    protected void customServerAiStep(ServerLevel level) {
        super.customServerAiStep(level);
        if (this.digCooldown > 0) {
            this.digCooldown--;
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.isSleeping()) {
            this.setXRot(0.0F);
            this.yHeadRot = this.yBodyRot;
            this.yHeadRotO = this.yBodyRotO;
        }
    }

    @Override
    public FerretEntity getBreedOffspring(ServerLevel level, AgeableMob other) {
        var baby = CACEntities.FERRET.get().create(level, EntitySpawnReason.BREEDING);
        if (baby == null) return null;

        var owner = this.getOwnerReference();
        if (other instanceof FerretEntity otherFerret) {
            baby.behaviour(VariantBehaviour.class).inherit(this, otherFerret);

            var color = random.nextBoolean() ? getCollarColor() : otherFerret.getCollarColor();
            if (color != null) baby.setCollarColor(color);

            if (owner != null) {
                baby.setOwnerReference(owner);
                baby.setTame(true, false);
            }
        }
        return baby;
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity entity) {
        if (super.doHurtTarget(level, entity)) {
            this.playSound(CACSounds.BITE_ATTACK.get(), this.getSoundVolume(), this.getVoicePitch());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {

        if (isTame() && isOwnedBy(player) && !isSleeping()) {
            var stack = player.getItemInHand(hand);

            var digResult = startDigging(player, stack);
            if (digResult != InteractionResult.PASS) return digResult;

            var dyeColor = stack.get(DataComponents.DYE);
            if (stack.getItem() instanceof DyeItem && dyeColor != null && getCollarColor() != dyeColor) {
                setCollarColor(dyeColor);
                stack.consume(1, player);
                return InteractionResult.SUCCESS;
            }
        }

        return super.mobInteract(player, hand);
    }

    private InteractionResult startDigging(Player player, ItemStack handStack) {
        if (handStack.is(TAGS.tempt()) && !isBaby() && !isInSittingPose()) {
            if (digCooldown <= 0) {
                stateToDig = level().getBlockState(blockPosition().below());

                if (stateToDig.is(DIG_GROUNDS_TAG)) {
                    setDigging(true);
                    digCooldown = 6000;
                    handStack.consume(1, player);
                    return InteractionResult.SUCCESS;
                } else {
                    stateToDig = null;
                }
            }

            return InteractionResult.FAIL;
        }

        return InteractionResult.PASS;
    }

    @Override
    public boolean canFallInLove() {
        return !this.isDigging() && super.canFallInLove();
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(TAGS.food());
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.isSleeping() ? null : CACSounds.FERRET_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return CACSounds.FERRET_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return CACSounds.FERRET_DEATH.get();
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelAccessor, DifficultyInstance difficultyInstance, EntitySpawnReason mobSpawnType, SpawnGroupData spawnGroupData) {
        spawnGroupData = super.finalizeSpawn(levelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
        if (mobSpawnType.equals(EntitySpawnReason.SPAWNER) && this.random.nextFloat() <= 0.2F) {
            for (int i = 0; i < this.random.nextInt(1, 4); i++) {
                var baby = CACEntities.FERRET.get().create(this.level(), EntitySpawnReason.SPAWNER);
                baby.finalizeSpawn(levelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
                baby.snapTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                baby.setBaby(true);
                levelAccessor.addFreshEntity(baby);
            }
        }
        return spawnGroupData;
    }

    private PlayState predicate(AnimationTest<?> event) {
        var controller = event.controller();

        if (this.isDigging()) {
            controller.setTransitionTicks(TRANSITION_TICK_TIME);
            controller.setAnimation(DIG_ANIM);
        } else if (this.isInSittingPose()) {
            controller.setTransitionTicks(0);
            controller.setAnimation(SIT_ANIM);
        } else if (this.isSleeping()) {
            controller.setTransitionTicks(0);
            controller.setAnimation(SLEEP_ANIM);
        } else if (isInWater()) {
            controller.setTransitionTicks(TRANSITION_TICK_TIME);
            controller.setAnimation(SWIM_ANIM);
        } else if (event.isMoving()) {
            controller.setTransitionTicks(TRANSITION_TICK_TIME);
            controller.setAnimation(RUN_ANIM);
        } else {
            if (event.isCurrentAnimation(SIT_ANIM) || event.isCurrentAnimation(SLEEP_ANIM)) {
                controller.setTransitionTicks(0);
            } else {
                controller.setTransitionTicks(TRANSITION_TICK_TIME);
            }

            controller.setAnimation(IDLE_ANIM);
        }

        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>("controller", TRANSITION_TICK_TIME, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public boolean isSleeping() {
        return this.entityData.get(SLEEPING);
    }

    public void setSleeping(boolean sleeping) {
        this.entityData.set(SLEEPING, sleeping);
    }

    public boolean isDigging() {
        return this.entityData.get(DIGGING);
    }

    public void setDigging(boolean digging) {
        this.entityData.set(DIGGING, digging);
        if (!digging) {
            stateToDig = null;
        }
    }

    @Nullable
    public DyeColor getCollarColor() {
        if (!isTame()) return null;
        return DyeColor.byId(entityData.get(DATA_COLLAR_COLOR));
    }

    private void setCollarColor(DyeColor color) {
        entityData.set(DATA_COLLAR_COLOR, color.getId());
    }
}
