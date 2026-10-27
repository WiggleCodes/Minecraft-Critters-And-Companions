package io.github.bonsaistudi0s.crittersandcompanions.client;

import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.network.chat.Component;

import dev.architectury.event.events.client.ClientPlayerEvent;
import dev.architectury.registry.client.gui.MenuScreenRegistry;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import io.github.bonsaistudi0s.crittersandcompanions.client.gui.RolyPolyScreen;
import io.github.bonsaistudi0s.crittersandcompanions.client.model.BubbleModel;
import io.github.bonsaistudi0s.crittersandcompanions.client.model.GrapplingHookModel;
import io.github.bonsaistudi0s.crittersandcompanions.client.renderer.BubbleLayer;
import io.github.bonsaistudi0s.crittersandcompanions.client.renderer.GrapplingHookRenderer;
import io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo.entity.*;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.projectiles.MudBallProjectile;
import io.github.bonsaistudi0s.crittersandcompanions.common.registry.CACEntities;
import io.github.bonsaistudi0s.crittersandcompanions.common.registry.CACMenuTypes;
import com.geckolib.renderer.GeoEntityRenderer;

public class CrittersAndCompanionsClient {

    public static void init() {
        registerEntityRenderers();
        registerEntityLayers();

        ClientPlayerEvent.CLIENT_PLAYER_JOIN.register(player -> player.sendSystemMessage(
                Component.literal("Critters and Companions re-developed by WigglyWooOfficials").withStyle(ChatFormatting.GREEN)
        ));
    }

    private static void registerEntityRenderers() {
        EntityRendererRegistry.register(CACEntities.OTTER, OtterRenderer::new);
        EntityRendererRegistry.register(CACEntities.JUMPING_SPIDER, JumpingSpiderRenderer::new);
        EntityRendererRegistry.register(CACEntities.KOI_FISH, KoiFishRenderer::new);
        EntityRendererRegistry.register(CACEntities.DRAGONFLY, DragonflyRenderer::new);
        EntityRendererRegistry.register(CACEntities.SEA_BUNNY, SeaBunnyRenderer::new);
        EntityRendererRegistry.register(CACEntities.SHIMA_ENAGA, ShimaEnagaRenderer::new);
        EntityRendererRegistry.register(CACEntities.FERRET, FerretRenderer::new);
        EntityRendererRegistry.register(CACEntities.DUMBO_OCTOPUS, DumboOctopusRenderer::new);
        EntityRendererRegistry.register(CACEntities.LEAF_INSECT, LeafInsectRenderer::new);
        EntityRendererRegistry.register(CACEntities.RED_PANDA, RedPandaRenderer::new);

        EntityRendererRegistry.register(CACEntities.LADYBUG, LadybugRenderer::new);
        EntityRendererRegistry.register(CACEntities.STAG_BEETLE, StagBeetleRenderer::new);
        EntityRendererRegistry.register(CACEntities.ROLY_POLY, RolyPolyRenderer::new);
        EntityRendererRegistry.register(CACEntities.SNAIL, SnailRenderer::new);
        EntityRendererRegistry.register(CACEntities.STICK_BUG, StickBugRenderer::new);
        EntityRendererRegistry.register(CACEntities.WEEVIL, WeevilRenderer::new);

        EntityRendererRegistry.register(CACEntities.GRAPPLING_HOOK, GrapplingHookRenderer::new);
        EntityRendererRegistry.register(CACEntities.MUD_BALL, context -> new GeoEntityRenderer<MudBallProjectile, EntityRenderState>(context, CACEntities.MUD_BALL.get()));
    }

    private static void registerEntityLayers() {
        EntityModelLayerRegistry.register(BubbleLayer.LAYER_LOCATION, BubbleModel::createLayer);
        EntityModelLayerRegistry.register(GrapplingHookRenderer.LAYER_LOCATION, GrapplingHookModel::createLayer);
    }

    /**
     * Only used on Fabric, NeoForge registers menu screens through its own event.
     */
    public static void registerMenuScreens() {
        MenuScreenRegistry.registerScreenFactory(CACMenuTypes.ROLY_POLY_CHEST.get(), RolyPolyScreen::new);
    }
}
