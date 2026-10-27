package io.github.bonsaistudi0s.crittersandcompanions.client.model.geo;

import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

import org.jspecify.annotations.Nullable;

import io.github.bonsaistudi0s.crittersandcompanions.client.util.AnimationUtils;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.RedPandaEntity;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.GeoRenderState;

public class RedPandaModel extends AgingGeoModel<RedPandaEntity> implements CustomBoneAnimations {

    private final Identifier sleepingTexture;

    public RedPandaModel(Identifier id) {
        super(id);
        this.sleepingTexture = buildFormattedTexturePath(id.withSuffix("_sleeping"));
    }

    private static boolean isSleeping(GeoRenderState renderState) {
        return renderState.getOrDefaultGeckolibData(CACDataTickets.SLEEPING, false);
    }

    @Override
    public void addAdditionalStateData(RedPandaEntity animatable, @Nullable Object relatedObject, GeoRenderState renderState) {
        super.addAdditionalStateData(animatable, relatedObject, renderState);
        renderState.addGeckolibData(CACDataTickets.SLEEPING, animatable.isSleeping());
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        if (!isBaby(renderState) && isSleeping(renderState)) return sleepingTexture;
        return super.getTextureResource(renderState);
    }

    @Override
    public void adjustModelBones(GeoRenderState renderState, BoneSnapshots snapshots) {
        var shouldOverrideAnimationHeadRotation = !isSleeping(renderState);
        if (!shouldOverrideAnimationHeadRotation) {
            return;
        }

        var head = snapshots.get("head");
        var headRotation = snapshots.get("head_rotation");
        var headAxisAlign = snapshots.get("head_axis_align");

        if (head.isPresent() && headRotation.isPresent() && headAxisAlign.isPresent()) {
            // rotation values for head_axis_align to make head_rotation have a world-space rotation of 0, 0, 0
            var axisAlign = AnimationUtils.calculateInverseWorldRotation(snapshots, headAxisAlign.get().getBone());
            AnimationUtils.setRotX(headAxisAlign.get(), axisAlign.x);
            AnimationUtils.setRotY(headAxisAlign.get(), axisAlign.y);
            AnimationUtils.setRotZ(headAxisAlign.get(), axisAlign.z);

            // counteract animation compensation on head for parent bone rotations
            var headSnapshot = head.get();
            AnimationUtils.setRotX(headSnapshot, AnimationUtils.getRotX(headSnapshot) - axisAlign.x);
            AnimationUtils.setRotY(headSnapshot, AnimationUtils.getRotY(headSnapshot) - axisAlign.y);
            AnimationUtils.setRotZ(headSnapshot, AnimationUtils.getRotZ(headSnapshot) - axisAlign.z);

            AnimationUtils.setRotX(headRotation.get(), AnimationUtils.getHeadPitch(renderState) * Mth.DEG_TO_RAD);
            AnimationUtils.setRotY(headRotation.get(), AnimationUtils.getNetHeadYaw(renderState) * Mth.DEG_TO_RAD);
        }
    }
}
