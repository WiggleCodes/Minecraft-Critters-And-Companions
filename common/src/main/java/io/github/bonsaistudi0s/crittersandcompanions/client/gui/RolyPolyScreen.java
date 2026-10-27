package io.github.bonsaistudi0s.crittersandcompanions.client.gui;

import static io.github.bonsaistudi0s.crittersandcompanions.common.menu.RolyPolyMenu.PLAYER_INV_START_Y;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.common.menu.RolyPolyMenu;

public class RolyPolyScreen extends AbstractContainerScreen<RolyPolyMenu> {

    private static final Identifier TEXTURE =
            CrittersAndCompanions.createId("textures/gui/roly_poly_chest.png");

    private static final int BUTTON_X = 152;
    private static final int BUTTON_Y = 6;
    private static final int BUTTON_WIDTH = 18;
    private static final int BUTTON_HEIGHT = 18;

    private static final WidgetSprites REMOVE_CHEST_SPRITES = new WidgetSprites(
            CrittersAndCompanions.createId("roly_poly/remove_chest"),
            CrittersAndCompanions.createId("roly_poly/remove_chest_highlighted")
    );

    public RolyPolyScreen(RolyPolyMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, 176, 166);
    }

    @Override
    protected void init() {
        super.init();
        inventoryLabelY = PLAYER_INV_START_Y - 11;
        addRenderableWidget(new ImageButton(
                leftPos + BUTTON_X, topPos + BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT,
                REMOVE_CHEST_SPRITES,
                btn -> {
                    var mc = Minecraft.getInstance();
                    if (mc.gameMode == null) {
                        return;
                    }

                    mc.gameMode.handleInventoryButtonClick(menu.containerId, 0);
                    this.onClose();
                }
        ));
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(graphics, mouseX, mouseY, partialTick);
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, leftPos, topPos, 0.0F, 0.0F, imageWidth, imageHeight, 256, 256);
    }
}
