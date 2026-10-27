package io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.client.model.geo.AgingGeoModel;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.WeevilEntity;
import io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo.entity.base.CACGeoEntityRenderer;

public class WeevilRenderer extends CACGeoEntityRenderer<WeevilEntity> {

    public WeevilRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new AgingGeoModel<>(CrittersAndCompanions.createId("weevil")));
    }
}
