package io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;

import io.github.bonsaistudi0s.crittersandcompanions.client.model.geo.FerretModel;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.FerretEntity;
import io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo.entity.base.CACGeoEntityRenderer;

public class FerretRenderer extends CACGeoEntityRenderer<FerretEntity> {

    public FerretRenderer(EntityRendererProvider.Context manager) {
        super(manager, new FerretModel());
        withRenderLayer(new FerretOverlayerLayer(this));
    }

}
