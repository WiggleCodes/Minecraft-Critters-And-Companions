package io.github.bonsaistudi0s.crittersandcompanions.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.client.model.GrapplingHookModel;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.GrapplingHookEntity;

public class GrapplingHookRenderer extends EntityRenderer<GrapplingHookEntity, GrapplingHookRenderer.State> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(CrittersAndCompanions.createId("grappling_hook"), "main");
    private static final Identifier TEXTURE = CrittersAndCompanions.createId("textures/entity/grappling_hook.png");
    private static final int STRING_COLOR = ARGB.color(255, 193, 184, 205);

    private final GrapplingHookModel hookModel;

    public static class State extends EntityRenderState {
        public boolean visible;
        public Vec3 lineOriginOffset = Vec3.ZERO;
    }

    public GrapplingHookRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.hookModel = new GrapplingHookModel(context.bakeLayer(LAYER_LOCATION));
    }

    private static void stringVertex(float p_174119_, float p_174120_, float p_174121_, VertexConsumer vertexConsumer, PoseStack.Pose pose, float p_174124_, float p_174125_, float width) {
        float f = p_174119_ * p_174124_;
        float f1 = p_174120_ * (p_174124_ * p_174124_ + p_174124_) * 0.5F + 0.25F;
        float f2 = p_174121_ * p_174124_;
        float f3 = p_174119_ * p_174125_ - f;
        float f4 = p_174120_ * (p_174125_ * p_174125_ + p_174125_) * 0.5F + 0.25F - f1;
        float f5 = p_174121_ * p_174125_ - f2;
        float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
        f3 /= f6;
        f4 /= f6;
        f5 /= f6;
        vertexConsumer.addVertex(pose, f, f1, f2).setColor(STRING_COLOR).setNormal(pose, f3, f4, f5).setLineWidth(width);
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(GrapplingHookEntity entity, State state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);

        state.visible = entity.getOwner() instanceof Player && entity.isFocused();
        if (!state.visible) {
            state.lineOriginOffset = Vec3.ZERO;
            return;
        }

        Player player = (Player) entity.getOwner();
        int handOffset = player.getMainArm() == HumanoidArm.RIGHT ? 1 : -1;
        if (!player.getMainHandItem().is(entity.getItem().getItem())) {
            handOffset = -handOffset;
        }

        float f = player.getSwingAnimation(partialTicks);
        float f1 = Mth.sin(Mth.sqrt(f) * (float) Math.PI);
        float f2 = Mth.lerp(partialTicks, player.yBodyRotO, player.yBodyRot) * ((float) Math.PI / 180F);
        double d0 = Mth.sin(f2);
        double d1 = Mth.cos(f2);
        double d2 = (double) handOffset * 0.35;
        double d4;
        double d5;
        double d6;
        float f3;
        if (this.entityRenderDispatcher.options.getCameraType().isFirstPerson() && player == Minecraft.getInstance().player) {
            float fov = this.entityRenderDispatcher.options.fov().get().intValue();
            double d7 = 960.0D / (double) fov;
            Vec3 vec3 = this.entityRenderDispatcher.camera.getNearPlane(fov).getPointOnPlane((float) handOffset * 0.825F, -0.08F);
            vec3 = vec3.scale(d7);
            vec3 = vec3.yRot(f1 * 0.5F);
            vec3 = vec3.xRot(-f1 * 0.7F);
            d4 = Mth.lerp(partialTicks, player.xo, player.getX()) + vec3.x;
            d5 = Mth.lerp(partialTicks, player.yo, player.getY()) + vec3.y;
            d6 = Mth.lerp(partialTicks, player.zo, player.getZ()) + vec3.z;
            f3 = player.getEyeHeight();
        } else {
            d4 = Mth.lerp(partialTicks, player.xo, player.getX()) - d1 * d2 - d0 * 0.8D;
            d5 = player.yo + (double) player.getEyeHeight() + (player.getY() - player.yo) * (double) partialTicks - 0.45D;
            d6 = Mth.lerp(partialTicks, player.zo, player.getZ()) - d0 * d2 + d1 * 0.8D;
            f3 = player.isCrouching() ? -0.1875F : 0.0F;
        }

        double hookPosX = Mth.lerp(partialTicks, entity.xo, entity.getX());
        double hookPosY = Mth.lerp(partialTicks, entity.yo, entity.getY()) + 0.4D;
        double hookPosZ = Mth.lerp(partialTicks, entity.zo, entity.getZ());
        state.lineOriginOffset = new Vec3(d4 - hookPosX, d5 - hookPosY + f3, d6 - hookPosZ);
    }

    @Override
    public void submit(State state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        if (state.visible) {
            poseStack.pushPose();
            poseStack.pushPose();
            poseStack.translate(0.0D, -1.25D, 0.0D);
            submitNodeCollector.submitModel(this.hookModel, Unit.INSTANCE, poseStack, this.hookModel.renderType(TEXTURE), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
            poseStack.popPose();

            float f4 = (float) state.lineOriginOffset.x;
            float f5 = (float) state.lineOriginOffset.y;
            float f6 = (float) state.lineOriginOffset.z;
            float width = Minecraft.getInstance().gameRenderer.gameRenderState().windowRenderState.appropriateLineWidth;
            submitNodeCollector.submitCustomGeometry(poseStack, RenderTypes.lines(), (pose, buffer) -> {
                stringVertex(f4, f5, f6, buffer, pose, 0.0F, 1.0F, width);
                stringVertex(f4, f5, f6, buffer, pose, 1.0F, 2.0F, width);
            });
            poseStack.popPose();
        }

        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    @Override
    protected boolean affectedByCulling(GrapplingHookEntity entity) {
        return false;
    }
}
