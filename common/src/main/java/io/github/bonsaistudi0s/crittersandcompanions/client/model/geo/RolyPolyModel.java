package io.github.bonsaistudi0s.crittersandcompanions.client.model.geo;

import org.jspecify.annotations.Nullable;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.client.util.AnimationUtils;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.RolyPolyEntity;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.GeoRenderState;

public class RolyPolyModel extends AgedVariantGeoModel<RolyPolyEntity> implements CustomBoneAnimations {

    public RolyPolyModel() {
        super(CrittersAndCompanions.createId("roly_poly"));
    }

    @Override
    public void addAdditionalStateData(RolyPolyEntity animatable, @Nullable Object relatedObject, GeoRenderState renderState) {
        super.addAdditionalStateData(animatable, relatedObject, renderState);
        var animName = AnimationUtils.getCurrentAnimationName(animatable, animatable.getId());
        if (animName != null) {
            renderState.addGeckolibData(CACDataTickets.CURRENT_ANIMATION, animName);
        }
    }

    @Override
    public void adjustModelBones(GeoRenderState renderState, BoneSnapshots snapshots) {
        var animName = renderState.getGeckolibData(CACDataTickets.CURRENT_ANIMATION);
        switch (animName) {
            case "walk", "dance_2", "flipped" -> toggleLegs(snapshots, true);
            case null, default -> toggleLegs(snapshots, false);
        }
    }

    private void toggleLegs(BoneSnapshots snapshots, boolean useAnimatedLegs) {
        snapshots.ifPresent("left_legs", bone -> AnimationUtils.setHidden(bone, useAnimatedLegs));
        snapshots.ifPresent("left_legs_walk", bone -> AnimationUtils.setHidden(bone, !useAnimatedLegs));
        snapshots.ifPresent("right_legs", bone -> AnimationUtils.setHidden(bone, useAnimatedLegs));
        snapshots.ifPresent("right_legs_walk", bone -> AnimationUtils.setHidden(bone, !useAnimatedLegs));
    }
}
