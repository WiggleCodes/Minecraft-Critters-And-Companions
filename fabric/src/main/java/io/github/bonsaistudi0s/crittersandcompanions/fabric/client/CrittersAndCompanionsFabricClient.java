package io.github.bonsaistudi0s.crittersandcompanions.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityRenderLayerRegistrationCallback;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.client.CrittersAndCompanionsClient;
import io.github.bonsaistudi0s.crittersandcompanions.client.renderer.BubbleLayer;

public final class CrittersAndCompanionsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        CrittersAndCompanionsClient.init();
        CrittersAndCompanionsClient.registerMenuScreens();

        LivingEntityRenderLayerRegistrationCallback.EVENT.register((type, renderer, registrationHelper, context) -> {
            if (!(renderer instanceof AvatarRenderer<?> avatarRenderer)) return;

            @SuppressWarnings("unchecked")
            var playerRenderer = (RenderLayerParent<AvatarRenderState, PlayerModel>) (Object) avatarRenderer;
            registrationHelper.register(new BubbleLayer(playerRenderer, context.getModelSet()));
        });

        var resourcePack = CrittersAndCompanions.createId("friendlyart");
        FabricLoader.getInstance().getModContainer(resourcePack.getNamespace()).ifPresent(mod -> {
            ResourceLoader.registerBuiltinPack(resourcePack, mod, PackActivationType.NORMAL);
        });
    }
}
