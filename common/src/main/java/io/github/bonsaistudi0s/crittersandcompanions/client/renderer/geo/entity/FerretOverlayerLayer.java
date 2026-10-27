package io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo.entity;

import com.google.common.collect.ImmutableMap;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;

import org.jspecify.annotations.Nullable;

import java.util.Map;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.client.model.geo.CACDataTickets;
import io.github.bonsaistudi0s.crittersandcompanions.common.api.CACColors;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.FerretEntity;
import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.renderer.layer.GeoRenderLayer;

public class FerretOverlayerLayer extends GeoRenderLayer<FerretEntity, Void, LivingEntityRenderState> {

    private static final Map<DyeColor, Identifier> TEXTURES = createTextures(false);
    private static final Map<DyeColor, Identifier> BABY_TEXTURES = createTextures(true);

    private static Map<DyeColor, Identifier> createTextures(boolean baby) {
        var base = baby ? "baby_ferret" : "ferret";
        var map = new ImmutableMap.Builder<DyeColor, Identifier>();
        CACColors.supported().forEach(dye -> {
            var id = CrittersAndCompanions.createId("textures/entity/%s_tamed_overlay_%s.png".formatted(base, dye.getSerializedName()));
            map.put(dye, id);
        });
        return map.build();
    }

    public FerretOverlayerLayer(GeoRenderer<FerretEntity, Void, LivingEntityRenderState> renderer) {
        super(renderer);
    }

    @Override
    public void addRenderData(FerretEntity animatable, @Nullable Void relatedObject, LivingEntityRenderState renderState, float partialTick) {
        var color = animatable.getCollarColor();
        if (color != null) {
            renderState.addGeckolibData(CACDataTickets.COLLAR_COLOR, color);
        }
    }

    @Override
    public void submitRenderTask(RenderPassInfo<LivingEntityRenderState> renderPassInfo, SubmitNodeCollector renderTasks) {
        var color = renderPassInfo.getGeckolibData(CACDataTickets.COLLAR_COLOR);
        if (!renderPassInfo.willRender() || color == null) return;

        var map = renderPassInfo.renderState().isBaby ? BABY_TEXTURES : TEXTURES;
        var texture = map.get(color);
        if (texture == null) return;

        this.renderer.submitRenderTasks(renderPassInfo, renderTasks.order(1), RenderTypes.entityCutoutCull(texture));
    }
}
