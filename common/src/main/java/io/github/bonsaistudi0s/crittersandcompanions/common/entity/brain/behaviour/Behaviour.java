package io.github.bonsaistudi0s.crittersandcompanions.common.entity.brain.behaviour;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

public interface Behaviour {

    default void aiStep() {
    }

    default void serverTick() {
    }

    default void save(ValueOutput output) {
    }

    default void read(ValueInput input) {
    }

    default void defineSyncedData(SynchedEntityData.Builder builder) {

    }

    default void finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnType, SpawnGroupData groupData) {

    }

    default void setRecordPlayingNearby(BlockPos pos, boolean active) {

    }

    default InteractionResult mobInteract(Player player, InteractionHand hand) {
        return InteractionResult.PASS;
    }

    default void dropEquipment() {
    }

    default void travel(Vec3 speed) {
    }

    default void setAge(int age) {
    }

}
