package io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;

import io.github.bonsaistudi0s.crittersandcompanions.client.model.geo.SeaBunnyModel;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.SeaBunnyEntity;
import io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo.entity.base.CACGeoEntityRenderer;

public class SeaBunnyRenderer extends CACGeoEntityRenderer<SeaBunnyEntity> {

    public SeaBunnyRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SeaBunnyModel());
    }
}
