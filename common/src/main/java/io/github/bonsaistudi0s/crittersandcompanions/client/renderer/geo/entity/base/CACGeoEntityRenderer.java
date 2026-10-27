package io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo.entity.base;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;

import io.github.bonsaistudi0s.crittersandcompanions.client.model.geo.CustomBoneAnimations;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.RenderPassInfo;

/**
 * Base renderer for the mod's living entities, which lets models apply their own procedural bone animations.
 */
public class CACGeoEntityRenderer<T extends LivingEntity & GeoAnimatable> extends GeoEntityRenderer<T, LivingEntityRenderState> {

    public CACGeoEntityRenderer(EntityRendererProvider.Context context, GeoModel<T> model) {
        super(context, model);
    }

    @Override
    public void adjustModelBonesForRender(RenderPassInfo<LivingEntityRenderState> renderPassInfo, BoneSnapshots snapshots) {
        super.adjustModelBonesForRender(renderPassInfo, snapshots);

        if (getGeoModel() instanceof CustomBoneAnimations model) {
            model.adjustModelBones(renderPassInfo.renderState(), snapshots);
        }
    }
}
