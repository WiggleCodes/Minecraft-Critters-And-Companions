package io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;

import io.github.bonsaistudi0s.crittersandcompanions.client.model.geo.JumpingSpiderModel;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.JumpingSpiderEntity;
import io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo.entity.base.CACGeoEntityRenderer;

public class JumpingSpiderRenderer extends CACGeoEntityRenderer<JumpingSpiderEntity> {

    public JumpingSpiderRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new JumpingSpiderModel());
    }
}
