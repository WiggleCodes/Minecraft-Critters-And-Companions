package io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo.entity;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

import org.jspecify.annotations.Nullable;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.client.model.geo.CACDataTickets;
import io.github.bonsaistudi0s.crittersandcompanions.client.model.geo.RolyPolyChestModel;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.RolyPolyEntity;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.brain.behaviour.ChestBehaviour;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.renderer.layer.GeoRenderLayer;

public class RolyPolyChestLayer extends GeoRenderLayer<RolyPolyEntity, Void, LivingEntityRenderState> {

    private static final Identifier TEXTURE = CrittersAndCompanions.createId("textures/entity/roly_poly_chest.png");

    private final GeoModel<RolyPolyEntity> chestModel;

    public RolyPolyChestLayer(GeoRenderer<RolyPolyEntity, Void, LivingEntityRenderState> renderer) {
        super(renderer);
        this.chestModel = new RolyPolyChestModel();
    }

    @Override
    public void addRenderData(RolyPolyEntity animatable, @Nullable Void relatedObject, LivingEntityRenderState renderState, float partialTick) {
        renderState.addGeckolibData(CACDataTickets.HAS_CHEST, animatable.behaviour(ChestBehaviour.class).hasChest());
    }

    @Override
    public void submitRenderTask(RenderPassInfo<LivingEntityRenderState> renderPassInfo, SubmitNodeCollector renderTasks) {
        if (!renderPassInfo.willRender() || !renderPassInfo.getOrDefaultGeckolibData(CACDataTickets.HAS_CHEST, false)) {
            return;
        }

        var chestBakedModel = this.chestModel.getBakedModel(this.chestModel.getModelResource(renderPassInfo.renderState()));
        var packedLight = renderPassInfo.packedLight();
        var packedOverlay = renderPassInfo.packedOverlay();
        var renderColor = renderPassInfo.renderColor();

        renderTasks.order(1).submitCustomGeometry(renderPassInfo.poseStack(), RenderTypes.entityCutout(TEXTURE), (pose, vertexConsumer) -> {
            var poseStack = renderPassInfo.poseStack();

            poseStack.pushPose();
            poseStack.last().set(pose);
            renderPassInfo.renderPosed(() -> {
                var chestBones = chestBakedModel.boneLookup().get().values();

                // sync bones with main model's body
                for (var chestBone : chestBones) {
                    renderPassInfo.model().getBone(chestBone.name()).ifPresent(mainBone -> chestBone.frameSnapshot = mainBone.frameSnapshot);
                }

                try {
                    chestBakedModel.render(renderPassInfo, vertexConsumer, packedLight, packedOverlay, renderColor);
                } finally {
                    for (var chestBone : chestBones) {
                        chestBone.frameSnapshot = null;
                    }
                }
            });
            poseStack.popPose();
        });
    }
}
