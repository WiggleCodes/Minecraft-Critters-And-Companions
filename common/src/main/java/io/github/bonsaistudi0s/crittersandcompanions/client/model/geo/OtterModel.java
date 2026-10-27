package io.github.bonsaistudi0s.crittersandcompanions.client.model.geo;

import net.minecraft.util.Mth;

import org.jspecify.annotations.Nullable;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.client.util.AnimationUtils;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.OtterEntity;
import com.geckolib.constant.DataTickets;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.GeoRenderState;

public class OtterModel extends AgingGeoModel<OtterEntity> implements CustomBoneAnimations {

    public OtterModel() {
        super(CrittersAndCompanions.createId("otter"));
    }

    @Override
    public void addAdditionalStateData(OtterEntity animatable, @Nullable Object relatedObject, GeoRenderState renderState) {
        super.addAdditionalStateData(animatable, relatedObject, renderState);
        renderState.addGeckolibData(CACDataTickets.IN_WATER, animatable.isInWater());
        renderState.addGeckolibData(CACDataTickets.FLOATING, animatable.isFloating());
        renderState.addGeckolibData(CACDataTickets.EATING, animatable.isEating());
    }

    @Override
    public void adjustModelBones(GeoRenderState renderState, BoneSnapshots snapshots) {
        var inWater = renderState.getOrDefaultGeckolibData(CACDataTickets.IN_WATER, false);
        var floating = renderState.getOrDefaultGeckolibData(CACDataTickets.FLOATING, false);
        var eating = renderState.getOrDefaultGeckolibData(CACDataTickets.EATING, false);

        // This bone rotation predicate should be done separately as it could be overlapped with the net head azimuth
        snapshots.ifPresent("main", root -> {
            if (!inWater && !floating) {
                // Hard bypasses all rotations of the core bone on land so the whole body stays in the default rotation,
                // thus preventing random pitch spikes (that basically happen when the otter is moving its head). Another
                // option is to cage the rots based on the otter state (more robust), but this impl requires less code
                AnimationUtils.setRotX(root, 0);
                AnimationUtils.setRotY(root, 0);
                AnimationUtils.setRotZ(root, 0); // Prevents wobble on +Z coordinates (probably residual?)
            }
        });

        // Enables the yaw by default everywhere everytime, while the pitch is only computed inside water
        snapshots.ifPresent(inWater ? "main" : "head", headOrMain -> {
            if (eating || floating) {
                return;
            }

            // Yaw is clamped within a minimal side rotation to avoid jittery which could lead to the head bone
            // to tweak a bit
            float yaw = Mth.clamp(AnimationUtils.getNetHeadYaw(renderState), -35.0F, 35.0F);
            // Pitch is only computed inside water as it's the only condition where the otter should rotate
            float pitch = inWater ? Mth.clamp(AnimationUtils.getHeadPitch(renderState), -90.0F, 90.0F) : 0.0F;

            // Damping
            if (!inWater && renderState.getOrDefaultGeckolibData(DataTickets.IS_MOVING, false)) yaw *= 0.6F;

            AnimationUtils.setRotY(headOrMain, yaw * Mth.DEG_TO_RAD);
            AnimationUtils.setRotX(headOrMain, pitch * Mth.DEG_TO_RAD);
        });
    }
}
