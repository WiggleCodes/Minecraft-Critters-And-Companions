package io.github.bonsaistudi0s.crittersandcompanions.neoforge.client;

import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.client.CrittersAndCompanionsClient;
import io.github.bonsaistudi0s.crittersandcompanions.client.gui.RolyPolyScreen;
import io.github.bonsaistudi0s.crittersandcompanions.client.renderer.BubbleLayer;
import io.github.bonsaistudi0s.crittersandcompanions.common.config.CACCommonConfig;
import io.github.bonsaistudi0s.crittersandcompanions.common.registry.CACMenuTypes;

@Mod(value = CrittersAndCompanions.MODID, dist = Dist.CLIENT)
public class CrittersAndCompanionsNeoForgeClient {

    public CrittersAndCompanionsNeoForgeClient(ModContainer container, IEventBus modEventBus) {
        CrittersAndCompanionsClient.init();
        modEventBus.addListener(this::registerScreens);
        modEventBus.addListener(this::addLayers);

        container.registerExtensionPoint(
                IConfigScreenFactory.class,
                (IConfigScreenFactory) (modContainer, parent) -> CACCommonConfig.createConfigScreen(parent)
        );
    }

    private void addLayers(EntityRenderersEvent.AddLayers event) {
        for (var skin : event.getSkins()) {
            var renderer = event.getPlayerRenderer(skin);
            if (renderer != null) {
                @SuppressWarnings("unchecked")
                var playerRenderer = (RenderLayerParent<AvatarRenderState, PlayerModel>) (Object) renderer;
                renderer.addLayer(new BubbleLayer(playerRenderer, event.getEntityModels()));
            }
        }
    }

    private void registerScreens(RegisterMenuScreensEvent event) {
        event.register(CACMenuTypes.ROLY_POLY_CHEST.get(), RolyPolyScreen::new);
    }
}
