package io.github.bonsaistudi0s.crittersandcompanions.common.mixin;

import net.minecraft.client.renderer.entity.state.EntityRenderState;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

import io.github.bonsaistudi0s.crittersandcompanions.client.renderer.SilkLeashRenderer;
import io.github.bonsaistudi0s.crittersandcompanions.common.extension.ISilkLeashRenderState;

@Mixin(EntityRenderState.class)
public class EntityRenderStateMixin implements ISilkLeashRenderState {

    @Unique
    private @Nullable List<SilkLeashRenderer.LeashData> cac$silkLeashes;

    @Override
    public @Nullable List<SilkLeashRenderer.LeashData> cac$getSilkLeashes() {
        return this.cac$silkLeashes;
    }

    @Override
    public void cac$setSilkLeashes(@Nullable List<SilkLeashRenderer.LeashData> leashes) {
        this.cac$silkLeashes = leashes;
    }
}
