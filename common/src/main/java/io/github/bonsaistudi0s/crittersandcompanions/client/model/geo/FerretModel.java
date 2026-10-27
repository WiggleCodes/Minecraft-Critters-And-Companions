package io.github.bonsaistudi0s.crittersandcompanions.client.model.geo;

import net.minecraft.util.Mth;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.client.util.AnimationUtils;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.FerretEntity;
import com.geckolib.constant.DataTickets;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.GeoRenderState;

public class FerretModel extends AgingVariantGeoModel<FerretEntity> implements CustomBoneAnimations {

    public FerretModel() {
        super(CrittersAndCompanions.createId("ferret"));
    }

    @Override
    public void adjustModelBones(GeoRenderState renderState, BoneSnapshots snapshots) {
        var head = snapshots.get("head");
        var neck = snapshots.get("head_rotation");
        var moving = renderState.getOrDefaultGeckolibData(DataTickets.IS_MOVING, false);

        if (neck.isPresent() && head.isPresent()) {
            var pitch = AnimationUtils.getHeadPitch(renderState);
            AnimationUtils.setRotX(neck.get(), Math.min(20, pitch) * Mth.DEG_TO_RAD);
            if (pitch > 20 && !moving) AnimationUtils.setRotX(head.get(), (pitch - 30) * Mth.DEG_TO_RAD);
            AnimationUtils.setRotZ(neck.get(), AnimationUtils.getNetHeadYaw(renderState) * Mth.DEG_TO_RAD * -0.5F);
        }
    }
}
