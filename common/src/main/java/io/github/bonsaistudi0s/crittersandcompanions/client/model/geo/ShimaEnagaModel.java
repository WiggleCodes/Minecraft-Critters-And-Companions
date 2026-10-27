package io.github.bonsaistudi0s.crittersandcompanions.client.model.geo;

import net.minecraft.util.Mth;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.client.util.AnimationUtils;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.ShimaEnagaEntity;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.GeoRenderState;

public class ShimaEnagaModel extends AgingGeoModel<ShimaEnagaEntity> implements CustomBoneAnimations {

    public ShimaEnagaModel() {
        super(CrittersAndCompanions.createId("shima_enaga"));
    }

    @Override
    public void adjustModelBones(GeoRenderState renderState, BoneSnapshots snapshots) {
        snapshots.ifPresent("head_rotation", head -> {
            AnimationUtils.setRotX(head, (AnimationUtils.getHeadPitch(renderState) + 10F) * Mth.DEG_TO_RAD * 0.6F);
            AnimationUtils.setRotY(head, AnimationUtils.getNetHeadYaw(renderState) * Mth.DEG_TO_RAD * 0.6F);
        });
    }
}
