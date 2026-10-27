package io.github.bonsaistudi0s.crittersandcompanions.client.model.geo;

import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.entity.AgeableMob;

import org.jspecify.annotations.Nullable;

import java.util.function.BiFunction;

import io.github.bonsaistudi0s.crittersandcompanions.common.entity.brain.behaviour.BehaviourDriven;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.brain.behaviour.VariantBehaviour;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.renderer.base.GeoRenderState;

public class AgingVariantGeoModel<T extends AgeableMob & GeoAnimatable & BehaviourDriven> extends AgingGeoModel<T> {

    private final BiFunction<Integer, Boolean, Identifier> texture;

    public AgingVariantGeoModel(Identifier id) {
        super(id);
        this.texture = Util.memoize((variant, baby) -> {
            var realId = baby ? id.withPrefix("baby_") : id;
            return buildFormattedTexturePath(realId.withSuffix("_" + variant));
        });
    }

    @Override
    public void addAdditionalStateData(T animatable, @Nullable Object relatedObject, GeoRenderState renderState) {
        super.addAdditionalStateData(animatable, relatedObject, renderState);
        renderState.addGeckolibData(CACDataTickets.VARIANT, animatable.behaviour(VariantBehaviour.class).getVariant());
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return texture.apply(renderState.getOrDefaultGeckolibData(CACDataTickets.VARIANT, 0) + 1, isBaby(renderState));
    }
}
