package io.github.bonsaistudi0s.crittersandcompanions.common.extension;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import io.github.bonsaistudi0s.crittersandcompanions.client.renderer.SilkLeashRenderer;

/**
 * Added to entity render states by a mixin, so silk leashes can be drawn from the render state.
 */
public interface ISilkLeashRenderState {

    @Nullable
    List<SilkLeashRenderer.LeashData> cac$getSilkLeashes();

    void cac$setSilkLeashes(@Nullable List<SilkLeashRenderer.LeashData> leashes);
}
