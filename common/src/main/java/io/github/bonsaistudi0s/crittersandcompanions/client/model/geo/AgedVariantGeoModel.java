package io.github.bonsaistudi0s.crittersandcompanions.client.model.geo;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.AgeableMob;

import io.github.bonsaistudi0s.crittersandcompanions.common.entity.brain.behaviour.BehaviourDriven;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.renderer.base.GeoRenderState;

public class AgedVariantGeoModel<T extends AgeableMob & GeoAnimatable & BehaviourDriven> extends AgingVariantGeoModel<T> {

    private final Identifier babyTexture;

    public AgedVariantGeoModel(Identifier id) {
        super(id);
        this.babyTexture = buildFormattedTexturePath(id.withPrefix("baby_"));
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        if (isBaby(renderState)) return babyTexture;
        return super.getTextureResource(renderState);
    }
}
