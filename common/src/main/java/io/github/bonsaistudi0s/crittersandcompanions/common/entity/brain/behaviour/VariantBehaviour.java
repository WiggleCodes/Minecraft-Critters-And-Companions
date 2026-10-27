package io.github.bonsaistudi0s.crittersandcompanions.common.entity.brain.behaviour;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.List;

public class VariantBehaviour implements Behaviour {
    private final Mob owner;
    private final EntityDataAccessor<Integer> dataAccessor;
    private final int count;

    public VariantBehaviour(Mob owner, EntityDataAccessor<Integer> dataAccessor, int count) {
        this.owner = owner;
        this.dataAccessor = dataAccessor;
        this.count = count;
    }

    public int getVariant() {
        return owner.getEntityData().get(dataAccessor);
    }

    public void setVariant(int variant) {
        owner.getEntityData().set(dataAccessor, Math.clamp(variant, 0, count - 1));
    }

    @Override
    public void defineSyncedData(SynchedEntityData.Builder builder) {
        builder.define(dataAccessor, 0);
    }

    @Override
    public void finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnType, SpawnGroupData groupData) {
        if (spawnType == EntitySpawnReason.BUCKET) return;
        setVariant(owner.getRandom().nextInt(0, count));
    }

    @Override
    public void read(ValueInput input) {
        setVariant(input.getIntOr("Variant", 0));
    }

    @Override
    public void save(ValueOutput output) {
        output.putInt("Variant", getVariant());
    }

    /**
     * Stores the variant on a bucket as custom model data, so the bucket's item model can pick the matching texture.
     */
    public void saveToBucket(ItemStack bucket) {
        bucket.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(List.of((float) getVariant()), List.of(), List.of(), List.of()));
    }

    public void inherit(Mob parentA, Mob parentB) {
        var inherited = owner.getRandom().nextBoolean()
                ? parentA
                : parentB;

        setVariant(inherited.behaviour(VariantBehaviour.class).getVariant());
    }

}
