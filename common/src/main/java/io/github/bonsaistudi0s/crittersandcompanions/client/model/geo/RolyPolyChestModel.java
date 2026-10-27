package io.github.bonsaistudi0s.crittersandcompanions.client.model.geo;

import net.minecraft.resources.Identifier;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.RolyPolyEntity;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;

public class RolyPolyChestModel extends GeoModel<RolyPolyEntity> {

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return CrittersAndCompanions.createId("entity/roly_poly_chest");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return CrittersAndCompanions.createId("textures/entity/roly_poly_chest.png");
    }

    @Override
    public Identifier getAnimationResource(RolyPolyEntity animatable) {
        return CrittersAndCompanions.createId("entity/roly_poly");
    }
}
