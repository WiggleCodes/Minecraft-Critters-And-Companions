package io.github.bonsaistudi0s.crittersandcompanions.common.mixin.behaviour;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.bonsaistudi0s.crittersandcompanions.common.entity.brain.behaviour.Behaviour;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.brain.behaviour.BehaviourDriven;

@Mixin(Mob.class)
public class MobMixin implements BehaviourDriven {

    @Inject(
            method = "addAdditionalSaveData(Lnet/minecraft/world/level/storage/ValueOutput;)V",
            at = @At("HEAD")
    )
    private void saveBehaviourData(ValueOutput output, CallbackInfo ci) {
        getBehaviours().forEach(it -> it.save(output));
    }

    @Inject(
            method = "readAdditionalSaveData(Lnet/minecraft/world/level/storage/ValueInput;)V",
            at = @At("HEAD")
    )
    private void readBehaviourData(ValueInput input, CallbackInfo ci) {
        getBehaviours().forEach(it -> it.read(input));
    }

    @Inject(
            method = "aiStep()V",
            at = @At("HEAD")
    )
    private void behaviourAiStep(CallbackInfo ci) {
        getBehaviours().forEach(Behaviour::aiStep);
    }

    @Inject(
            method = "tick()V",
            at = @At("HEAD")
    )
    private void behaviourServerTick(CallbackInfo ci) {
        var self = (Mob) (Object) this;
        if (self.level().isClientSide()) return;
        getBehaviours().forEach(Behaviour::serverTick);
    }

    @Inject(
            method = "defineSynchedData(Lnet/minecraft/network/syncher/SynchedEntityData$Builder;)V",
            at = @At("HEAD")
    )
    private void defineSyncedBehaviourData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        getBehaviours().forEach(it -> it.defineSyncedData(builder));
    }

    @Inject(
            method = "finalizeSpawn(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/world/entity/EntitySpawnReason;Lnet/minecraft/world/entity/SpawnGroupData;)Lnet/minecraft/world/entity/SpawnGroupData;",
            at = @At("HEAD")
    )
    private void defineSyncedBehaviourData(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnType, SpawnGroupData groupData, CallbackInfoReturnable<SpawnGroupData> cir) {
        getBehaviours().forEach(it -> it.finalizeSpawn(level, difficulty, spawnType, groupData));
    }

}
