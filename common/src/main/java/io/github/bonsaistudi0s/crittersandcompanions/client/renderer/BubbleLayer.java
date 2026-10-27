package io.github.bonsaistudi0s.crittersandcompanions.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.client.model.BubbleModel;
import io.github.bonsaistudi0s.crittersandcompanions.common.extension.IBubbleState;

public class BubbleLayer extends RenderLayer<AvatarRenderState, PlayerModel> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(CrittersAndCompanions.createId("bubble"), "main");
    private static final Identifier TEXTURE = CrittersAndCompanions.createId("textures/entity/bubble.png");

    private final BubbleModel bubbleModel;

    public BubbleLayer(RenderLayerParent<AvatarRenderState, PlayerModel> parent, EntityModelSet modelSet) {
        super(parent);
        this.bubbleModel = new BubbleModel(modelSet.bakeLayer(LAYER_LOCATION));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, AvatarRenderState state, float yRot, float xRot) {
        var level = Minecraft.getInstance().level;
        if (level == null || !(level.getEntity(state.id) instanceof IBubbleState bubbleState) || !bubbleState.isBubbleActive()) {
            return;
        }

        // The parent model has already been posed for this render state, so the head pose is up to date.
        var bubbleState2 = new BubbleModel.State(this.getParentModel().head.storePose(), state.ageInTicks);
        submitNodeCollector.submitModel(this.bubbleModel, bubbleState2, poseStack, this.bubbleModel.renderType(TEXTURE), lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
    }
}
