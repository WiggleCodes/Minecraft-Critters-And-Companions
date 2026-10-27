package io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.client.model.geo.RedPandaModel;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.RedPandaEntity;
import io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo.entity.base.CACGeoEntityRenderer;

public class RedPandaRenderer extends CACGeoEntityRenderer<RedPandaEntity> {

    public RedPandaRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new RedPandaModel(CrittersAndCompanions.createId("red_panda")));
    }
}
