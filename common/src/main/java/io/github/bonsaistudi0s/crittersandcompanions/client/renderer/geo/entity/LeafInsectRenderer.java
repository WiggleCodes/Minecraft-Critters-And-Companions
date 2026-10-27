package io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;

import io.github.bonsaistudi0s.crittersandcompanions.client.model.geo.LeafInsectModel;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.LeafInsectEntity;
import io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo.entity.base.CACGeoEntityRenderer;

public class LeafInsectRenderer extends CACGeoEntityRenderer<LeafInsectEntity> {

    public LeafInsectRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new LeafInsectModel());
    }
}
