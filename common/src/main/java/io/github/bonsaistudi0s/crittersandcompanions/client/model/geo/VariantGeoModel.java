package io.github.bonsaistudi0s.crittersandcompanions.client.model.geo;

import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import org.jspecify.annotations.Nullable;

import java.util.function.Function;

import io.github.bonsaistudi0s.crittersandcompanions.common.entity.brain.behaviour.BehaviourDriven;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.brain.behaviour.VariantBehaviour;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.model.DefaultedEntityGeoModel;
import com.geckolib.renderer.base.GeoRenderState;

public class VariantGeoModel<T extends GeoAnimatable & BehaviourDriven> extends DefaultedEntityGeoModel<T> {

    private final Function<Integer, Identifier> texture;

    public VariantGeoModel(Identifier id) {
        super(id);
        this.texture = Util.memoize(variant -> buildFormattedTexturePath(id.withSuffix("_" + variant)));
    }

    @Override
    public void addAdditionalStateData(T animatable, @Nullable Object relatedObject, GeoRenderState renderState) {
        super.addAdditionalStateData(animatable, relatedObject, renderState);
        renderState.addGeckolibData(CACDataTickets.VARIANT, animatable.behaviour(VariantBehaviour.class).getVariant());
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return texture.apply(renderState.getOrDefaultGeckolibData(CACDataTickets.VARIANT, 0) + 1);
    }
}
