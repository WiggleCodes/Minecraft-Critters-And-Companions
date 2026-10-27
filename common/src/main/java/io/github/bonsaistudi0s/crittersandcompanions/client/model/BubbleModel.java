package io.github.bonsaistudi0s.crittersandcompanions.client.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.Mth;

public class BubbleModel extends Model<BubbleModel.State> {
    public final ModelPart bubble;

    /**
     * @param headPose The pose of the head the bubble is placed around
     * @param ageInTicks The age of the bubble's wearer, used for the wobble animation
     */
    public record State(PartPose headPose, float ageInTicks) {
    }

    public BubbleModel(ModelPart root) {
        super(root, RenderTypes::entityCutoutCull);
        this.bubble = root.getChild("bubble");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("bubble", CubeListBuilder.create().texOffs(0, 0).addBox(-5.5F, -9.0F, -5.5F, 11.0F, 11.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(State state) {
        super.setupAnim(state);
        this.bubble.loadPose(state.headPose());

        float wobbleH = Mth.cos(state.ageInTicks() / 4.0F) * 0.2F;
        float wobbleV = Mth.sin(state.ageInTicks() / 4.0F) * 0.1F;
        this.bubble.xScale += wobbleH;
        this.bubble.yScale += wobbleV;
        this.bubble.zScale += wobbleV;
    }
}
