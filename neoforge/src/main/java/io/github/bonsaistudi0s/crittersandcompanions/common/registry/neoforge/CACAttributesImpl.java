package io.github.bonsaistudi0s.crittersandcompanions.common.registry.neoforge;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForgeMod;

@SuppressWarnings("unused")
public class CACAttributesImpl {

    public static double getSwimSpeed(LivingEntity entity) {
        return entity.getAttributeValue(NeoForgeMod.SWIM_SPEED);
    }
}
