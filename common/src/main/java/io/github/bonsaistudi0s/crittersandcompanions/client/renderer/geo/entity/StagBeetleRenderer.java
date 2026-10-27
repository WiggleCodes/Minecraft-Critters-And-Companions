package io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.client.model.geo.AgingVariantGeoModel;
import io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo.entity.base.CACGeoEntityRenderer;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.StagBeetleEntity;
import com.geckolib.renderer.base.GeoRenderState;

public class StagBeetleRenderer extends CACGeoEntityRenderer<StagBeetleEntity> {
    public StagBeetleRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new AgingVariantGeoModel<>(CrittersAndCompanions.createId("stag_beetle")));
    }

    @Override
    protected float getDeathMaxRotation(GeoRenderState renderState) {
        return 0.0F;
    }
}
