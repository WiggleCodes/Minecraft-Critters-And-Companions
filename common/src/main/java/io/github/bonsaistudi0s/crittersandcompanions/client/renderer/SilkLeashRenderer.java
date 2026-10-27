package io.github.bonsaistudi0s.crittersandcompanions.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

import io.github.bonsaistudi0s.crittersandcompanions.common.extension.ISilkLeashRenderState;
import io.github.bonsaistudi0s.crittersandcompanions.common.extension.ISilkLeashState;

public class SilkLeashRenderer {

    /**
     * Everything needed to draw one silk leash, captured when the entity's render state is extracted.
     */
    public record LeashData(Vec3 offset, float dx, float dy, float dz, int startBlockLight, int startSkyLight, int endSkyLight) {
    }

    public static void extractSilkLeashes(Entity entity, EntityRenderState state, float partialTicks) {
        if (!(state instanceof ISilkLeashRenderState leashRenderState)) {
            return;
        }

        if (entity instanceof LivingEntity livingEntity && entity instanceof ISilkLeashState leashState && !leashState.getLeashedByEntities().isEmpty()) {
            List<LeashData> leashes = new ArrayList<>();
            for (LivingEntity leashedBy : leashState.getLeashedByEntities()) {
                leashes.add(createLeashData(livingEntity, partialTicks, leashedBy));
            }
            leashRenderState.cac$setSilkLeashes(leashes);
        } else {
            leashRenderState.cac$setSilkLeashes(null);
        }
    }

    public static void submitSilkLeashes(EntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector) {
        if (!(state instanceof ISilkLeashRenderState leashRenderState) || leashRenderState.cac$getSilkLeashes() == null) {
            return;
        }

        for (LeashData leash : leashRenderState.cac$getSilkLeashes()) {
            poseStack.pushPose();
            poseStack.translate(leash.offset().x, leash.offset().y, leash.offset().z);
            submitNodeCollector.submitCustomGeometry(poseStack, RenderTypes.leash(), (pose, vertexConsumer) -> buildLeash(leash, pose, vertexConsumer));
            poseStack.popPose();
        }
    }

    private static Vec3 getLeashOffset(Entity entity) {
        if (entity instanceof Leashable leashable) {
            return leashable.getLeashOffset();
        }

        return new Vec3(0.0D, entity.getEyeHeight(), entity.getBbWidth() * 0.4F);
    }

    /**
     * Mostly copied from the vanilla leash renderer in older versions of Minecraft.
     */
    private static LeashData createLeashData(LivingEntity entity, float partialTicks, Entity leashedToEntity) {
        Vec3 vec3 = leashedToEntity.getRopeHoldPosition(partialTicks);
        double d0 = (double) (Mth.lerp(partialTicks, entity.yBodyRotO, entity.yBodyRot) * ((float) Math.PI / 180F)) + (Math.PI / 2D);
        Vec3 vec31 = getLeashOffset(entity);
        double d1 = Math.cos(d0) * vec31.z + Math.sin(d0) * vec31.x;
        double d2 = Math.sin(d0) * vec31.z - Math.cos(d0) * vec31.x;
        double d3 = Mth.lerp(partialTicks, entity.xo, entity.getX()) + d1;
        double d4 = Mth.lerp(partialTicks, entity.yo, entity.getY()) + vec31.y;
        double d5 = Mth.lerp(partialTicks, entity.zo, entity.getZ()) + d2;
        float f = (float) (vec3.x - d3);
        float f1 = (float) (vec3.y - d4);
        float f2 = (float) (vec3.z - d5);
        BlockPos blockpos = BlockPos.containing(entity.getEyePosition(partialTicks));
        BlockPos blockpos1 = BlockPos.containing(leashedToEntity.getEyePosition(partialTicks));
        int i = entity.level().getBrightness(LightLayer.BLOCK, blockpos);
        int k = entity.level().getBrightness(LightLayer.SKY, blockpos);
        int l = entity.level().getBrightness(LightLayer.SKY, blockpos1);
        return new LeashData(new Vec3(d1, vec31.y, d2), f, f1, f2, i, k, l);
    }

    private static void buildLeash(LeashData leash, PoseStack.Pose pose, VertexConsumer vertexConsumer) {
        float f = leash.dx();
        float f2 = leash.dz();
        float f4 = (float) (Mth.invSqrt(f * f + f2 * f2) * 0.025F / 2.0F);
        float f5 = f2 * f4;
        float f6 = f * f4;

        for (int i1 = 0; i1 <= 24; ++i1) {
            addVertexPair(vertexConsumer, pose, leash, 0.025F, f5, f6, i1, false, 0.25F + 0.75F * (i1 / 24.0F));
        }

        for (int j1 = 24; j1 >= 0; --j1) {
            addVertexPair(vertexConsumer, pose, leash, 0.0F, f5, f6, j1, true, 0.25F + 0.75F * (j1 / 24.0F));
        }
    }

    private static void addVertexPair(VertexConsumer vertexConsumer, PoseStack.Pose pose, LeashData leash, float p_174318_, float p_174319_, float p_174320_, int p_174321_, boolean p_174322_, float gradient) {
        float f = (float) p_174321_ / 24.0F;
        int i = (int) Mth.lerp(f, (float) leash.startBlockLight(), (float) 1);
        int j = (int) Mth.lerp(f, (float) leash.startSkyLight(), (float) leash.endSkyLight());
        int k = LightCoordsUtil.pack(i, j);
        float f1 = p_174321_ % 2 == (p_174322_ ? 1 : 0) ? 0.7F : 1.0F;
        float r = 0.75F * f1 * gradient;
        float g = 0.72F * f1 * gradient;
        float b = 0.8F * f1 * gradient;
        float p_174310_ = leash.dx();
        float p_174311_ = leash.dy();
        float p_174312_ = leash.dz();
        float f5 = p_174310_ * f;
        float f6 = p_174311_ > 0.0F ? p_174311_ * f * f : p_174311_ - p_174311_ * (1.0F - f) * (1.0F - f);
        float f7 = p_174312_ * f;
        vertexConsumer.addVertex(pose, f5 - p_174319_, f6 + p_174318_, f7 + p_174320_).setColor(r, g, b, 1.0F).setLight(k);
        vertexConsumer.addVertex(pose, f5 + p_174319_, f6 + (float) 0.025 - p_174318_, f7 - p_174320_).setColor(r, g, b, 1.0F).setLight(k);
    }
}
