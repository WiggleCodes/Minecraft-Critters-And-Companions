package io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;

import io.github.bonsaistudi0s.crittersandcompanions.client.model.geo.OtterModel;
import io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo.entity.base.CACGeoEntityRenderer;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.OtterEntity;

public class OtterRenderer extends CACGeoEntityRenderer<OtterEntity> {
    public OtterRenderer(EntityRendererProvider.Context context) {
        super(context, new OtterModel());
        withRenderLayer(new OtterHeldItemLayer(context, this));
    }
}
