package io.github.bonsaistudi0s.crittersandcompanions.client.model.geo;

import net.minecraft.util.Mth;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.client.util.AnimationUtils;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.JumpingSpiderEntity;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.GeoRenderState;

public class JumpingSpiderModel extends AgingVariantGeoModel<JumpingSpiderEntity> implements CustomBoneAnimations {

    public JumpingSpiderModel() {
        super(CrittersAndCompanions.createId("jumping_spider"));
    }

    @Override
    public void adjustModelBones(GeoRenderState renderState, BoneSnapshots snapshots) {
        snapshots.ifPresent("head_rotation", head -> {
            AnimationUtils.setRotX(head, AnimationUtils.getHeadPitch(renderState) * Mth.DEG_TO_RAD);
            AnimationUtils.setRotY(head, AnimationUtils.getNetHeadYaw(renderState) * Mth.DEG_TO_RAD);
        });
    }
}
