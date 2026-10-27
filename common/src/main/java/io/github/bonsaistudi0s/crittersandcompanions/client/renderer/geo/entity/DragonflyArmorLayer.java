package io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo.entity;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;

import org.jspecify.annotations.Nullable;

import io.github.bonsaistudi0s.crittersandcompanions.client.model.geo.CACDataTickets;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.DragonflyEntity;
import io.github.bonsaistudi0s.crittersandcompanions.common.item.DragonflyArmorItem;
import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.renderer.layer.GeoRenderLayer;

public class DragonflyArmorLayer extends GeoRenderLayer<DragonflyEntity, Void, LivingEntityRenderState> {

    public DragonflyArmorLayer(GeoRenderer<DragonflyEntity, Void, LivingEntityRenderState> renderer) {
        super(renderer);
    }

    @Override
    public void addRenderData(DragonflyEntity animatable, @Nullable Void relatedObject, LivingEntityRenderState renderState, float partialTick) {
        if (animatable.getArmor().getItem() instanceof DragonflyArmorItem item) {
            renderState.addGeckolibData(CACDataTickets.ARMOR_TEXTURE, item.getTexture());
        }
    }

    @Override
    public void submitRenderTask(RenderPassInfo<LivingEntityRenderState> renderPassInfo, SubmitNodeCollector renderTasks) {
        var texture = renderPassInfo.getGeckolibData(CACDataTickets.ARMOR_TEXTURE);
        if (!renderPassInfo.willRender() || texture == null) {
            return;
        }

        this.renderer.submitRenderTasks(renderPassInfo, renderTasks.order(1), RenderTypes.entityCutout(texture));
    }
}
