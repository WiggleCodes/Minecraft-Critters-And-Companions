package io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;

import io.github.bonsaistudi0s.crittersandcompanions.client.model.geo.ShimaEnagaModel;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.ShimaEnagaEntity;
import io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo.entity.base.CACGeoEntityRenderer;

public class ShimaEnagaRenderer extends CACGeoEntityRenderer<ShimaEnagaEntity> {

    public ShimaEnagaRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ShimaEnagaModel());
    }
}
