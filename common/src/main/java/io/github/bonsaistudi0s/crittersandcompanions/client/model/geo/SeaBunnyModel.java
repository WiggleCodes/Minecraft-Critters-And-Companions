package io.github.bonsaistudi0s.crittersandcompanions.client.model.geo;

import org.jspecify.annotations.Nullable;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.client.util.AnimationUtils;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.SeaBunnyEntity;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.brain.behaviour.ClimbingBehaviour;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.GeoRenderState;

public class SeaBunnyModel extends AgingVariantGeoModel<SeaBunnyEntity> implements CustomBoneAnimations {

    public SeaBunnyModel() {
        super(CrittersAndCompanions.createId("sea_bunny"));
    }

    @Override
    public void addAdditionalStateData(SeaBunnyEntity animatable, @Nullable Object relatedObject, GeoRenderState renderState) {
        super.addAdditionalStateData(animatable, relatedObject, renderState);
        renderState.addGeckolibData(CACDataTickets.CLIMBING, animatable.behaviour(ClimbingBehaviour.class).isClimbing());
    }

    @Override
    public void adjustModelBones(GeoRenderState renderState, BoneSnapshots snapshots) {
        snapshots.ifPresent("main", mainBone -> {
            AnimationUtils.setRotY(mainBone, AnimationUtils.getNetHeadYaw(renderState) * ((float) Math.PI / 180.0F));

            if (renderState.getOrDefaultGeckolibData(CACDataTickets.CLIMBING, false)) {
                AnimationUtils.setRotX(mainBone, 90.0F * ((float) Math.PI / 180.0F));
            } else {
                AnimationUtils.setRotX(mainBone, 0F);
            }
        });
    }
}
