package io.github.bonsaistudi0s.crittersandcompanions.common.mixin.behaviour;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Bucketable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Bucketable.class)
public interface BucketableMixin {

    @Inject(
            method = "saveDefaultDataToBucketTag(Lnet/minecraft/world/entity/Mob;Lnet/minecraft/world/item/ItemStack;)V",
            at = @At("HEAD")
    )
    private static void saveBehaviourData(Mob mob, ItemStack bucket, CallbackInfo ci) {
        var output = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, mob.registryAccess());
        mob.getBehaviours().forEach(it -> it.save(output));
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, bucket, nbt -> nbt.merge(output.buildResult()));
    }

    @Inject(
            method = "loadDefaultDataFromBucketTag(Lnet/minecraft/world/entity/Mob;Lnet/minecraft/nbt/CompoundTag;)V",
            at = @At("HEAD")
    )
    private static void saveBehaviourData(Mob mob, CompoundTag nbt, CallbackInfo ci) {
        var input = TagValueInput.create(ProblemReporter.DISCARDING, mob.registryAccess(), nbt);
        mob.getBehaviours().forEach(it -> it.read(input));
    }


}
