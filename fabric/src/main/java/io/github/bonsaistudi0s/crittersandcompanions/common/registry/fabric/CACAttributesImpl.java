package io.github.bonsaistudi0s.crittersandcompanions.common.registry.fabric;

import net.minecraft.world.entity.LivingEntity;

@SuppressWarnings("unused")
public class CACAttributesImpl {

    public static double getSwimSpeed(LivingEntity entity) {
        // Fabric has no swim speed attribute, so use the default multiplier.
        return 1.0D;
    }
}
