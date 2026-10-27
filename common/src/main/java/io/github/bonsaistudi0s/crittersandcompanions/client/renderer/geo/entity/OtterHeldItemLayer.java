package io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo.entity;

import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;

import org.jspecify.annotations.Nullable;

import java.util.function.BiConsumer;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.OtterEntity;
import com.geckolib.cache.model.GeoBone;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.base.PerBoneRender;
import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.renderer.layer.GeoRenderLayer;

/**
 * Renders the item an otter is holding, positioned relative to the parent of the "held_item" bone.
 */
public class OtterHeldItemLayer extends GeoRenderLayer<OtterEntity, Void, LivingEntityRenderState> {

    private static final DataTicket<ItemStackRenderState> HELD_ITEM = DataTicket.create(CrittersAndCompanions.MODID + ":otter_held_item", ItemStackRenderState.class);

    private final ItemModelResolver itemModelResolver;

    public OtterHeldItemLayer(EntityRendererProvider.Context context, GeoRenderer<OtterEntity, Void, LivingEntityRenderState> renderer) {
        super(renderer);
        this.itemModelResolver = context.getItemModelResolver();
    }

    @Override
    public void addRenderData(OtterEntity animatable, @Nullable Void relatedObject, LivingEntityRenderState renderState, float partialTick) {
        var itemState = new ItemStackRenderState();
        this.itemModelResolver.updateForLiving(itemState, animatable.getMainHandItem(), ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, animatable);
        renderState.addGeckolibData(HELD_ITEM, itemState);
    }

    @Override
    public void addPerBoneRender(RenderPassInfo<LivingEntityRenderState> renderPassInfo, BiConsumer<GeoBone, PerBoneRender<LivingEntityRenderState>> consumer) {
        var itemState = renderPassInfo.getGeckolibData(HELD_ITEM);
        if (!renderPassInfo.willRender() || itemState == null || itemState.isEmpty()) {
            return;
        }

        // The item was positioned relative to the bone's parent, so it doesn't follow the held_item bone's own transforms
        renderPassInfo.model().getBone("held_item").map(GeoBone::parent).ifPresent(parent -> consumer.accept(parent, (passInfo, bone, renderTasks) -> {
            var stack = passInfo.poseStack();
            bone.translateAwayFromPivotPoint(stack);
            stack.scale(0.5f, 0.5f, 0.5f);
            stack.translate(0.05D, 0.2D, -0.9D);
            stack.rotateDegrees(Axis.XP, -90.0F);
            stack.rotateDegrees(Axis.YP, 180.0F);

            if (passInfo.renderState().isBaby) {
                stack.translate(0.0D, -0.6D, 0.0D);
            } else {
                stack.translate(0.0D, -0.125D, 0.0D);
            }

            itemState.submit(stack, renderTasks, passInfo.packedLight(), OverlayTexture.NO_OVERLAY, passInfo.renderState().outlineColor);
        }));
    }
}
