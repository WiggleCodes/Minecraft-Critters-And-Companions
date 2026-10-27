package io.github.bonsaistudi0s.crittersandcompanions.client.model.geo;

import net.minecraft.util.Mth;

import org.jspecify.annotations.Nullable;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.client.util.AnimationUtils;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.LeafInsectEntity;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.GeoRenderState;

public class LeafInsectModel extends AgingVariantGeoModel<LeafInsectEntity> implements CustomBoneAnimations {

    public LeafInsectModel() {
        super(CrittersAndCompanions.createId("leaf_insect"));
    }

    @Override
    public void addAdditionalStateData(LeafInsectEntity animatable, @Nullable Object relatedObject, GeoRenderState renderState) {
        super.addAdditionalStateData(animatable, relatedObject, renderState);
        var lerpedWeight = Mth.lerp(renderState.getPartialTick(), animatable.lookBlendWeightO, animatable.lookBlendWeight);
        renderState.addGeckolibData(CACDataTickets.LOOK_BLEND_WEIGHT, lerpedWeight);
    }

    @Override
    public void adjustModelBones(GeoRenderState renderState, BoneSnapshots snapshots) {
        snapshots.ifPresent("head_rotation", headRotation -> {
            var proceduralPitch = AnimationUtils.getHeadPitch(renderState) * Mth.DEG_TO_RAD;
            var proceduralYaw = AnimationUtils.getNetHeadYaw(renderState) * Mth.DEG_TO_RAD;
            var lerpedWeight = renderState.getOrDefaultGeckolibData(CACDataTickets.LOOK_BLEND_WEIGHT, 1.0f);

            if (lerpedWeight < 0.99f) {
                var animationPitch = AnimationUtils.getRotX(headRotation);
                var animationYaw = AnimationUtils.getRotY(headRotation);
                AnimationUtils.setRotX(headRotation, Mth.lerp(lerpedWeight, animationPitch, proceduralPitch));
                AnimationUtils.setRotY(headRotation, Mth.lerp(lerpedWeight, animationYaw, proceduralYaw));
            } else {
                AnimationUtils.setRotX(headRotation, proceduralPitch);
                AnimationUtils.setRotY(headRotation, proceduralYaw);
            }
        });
    }
}
