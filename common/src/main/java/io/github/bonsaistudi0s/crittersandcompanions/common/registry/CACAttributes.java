package io.github.bonsaistudi0s.crittersandcompanions.common.registry;

import net.minecraft.world.entity.LivingEntity;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class CACAttributes {

    @ExpectPlatform
    public static double getSwimSpeed(LivingEntity entity) {
        throw new AssertionError();
    }
}
