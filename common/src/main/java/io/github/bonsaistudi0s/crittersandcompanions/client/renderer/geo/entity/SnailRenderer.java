package io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

import org.jspecify.annotations.Nullable;

import io.github.bonsaistudi0s.crittersandcompanions.client.model.geo.SnailModel;
import io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo.entity.base.CACGeoEntityRenderer;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.SnailEntity;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.brain.behaviour.ClimbingBehaviour;
import com.geckolib.constant.DataTickets;

public class SnailRenderer extends CACGeoEntityRenderer<SnailEntity> {
    public SnailRenderer(EntityRendererProvider.Context context) {
        super(context, new SnailModel());
    }

    @Override
    public void addRenderData(SnailEntity animatable, @Nullable Void relatedObject, LivingEntityRenderState renderState, float partialTick) {
        super.addRenderData(animatable, relatedObject, renderState, partialTick);

        // Face away from the wall while climbing it
        if (animatable.behaviour(ClimbingBehaviour.class).isClimbing()) {
            var wallFace = animatable.getClimbingWallFace();
            if (wallFace != null) {
                renderState.addGeckolibData(DataTickets.ENTITY_BODY_YAW, wallFace.toYRot());
            }
        }
    }

    @Override
    public float getMotionAnimThreshold(SnailEntity animatable) {
        return 0.005f;
    }
}
