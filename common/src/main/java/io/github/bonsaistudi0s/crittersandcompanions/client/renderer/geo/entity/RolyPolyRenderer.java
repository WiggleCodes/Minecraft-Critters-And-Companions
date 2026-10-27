package io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;

import io.github.bonsaistudi0s.crittersandcompanions.client.model.geo.RolyPolyModel;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.RolyPolyEntity;
import io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo.entity.base.CACGeoEntityRenderer;

public class RolyPolyRenderer extends CACGeoEntityRenderer<RolyPolyEntity> {

    public RolyPolyRenderer(EntityRendererProvider.Context context) {
        super(context, new RolyPolyModel());
        withRenderLayer(new RolyPolyChestLayer(this));
    }

}
