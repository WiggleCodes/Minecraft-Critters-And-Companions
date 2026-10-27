package io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.client.model.geo.VariantGeoModel;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.DumboOctopusEntity;
import io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo.entity.base.CACGeoEntityRenderer;

public class DumboOctopusRenderer extends CACGeoEntityRenderer<DumboOctopusEntity> {

    public DumboOctopusRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new VariantGeoModel<>(CrittersAndCompanions.createId("dumbo_octopus")));
    }
}
