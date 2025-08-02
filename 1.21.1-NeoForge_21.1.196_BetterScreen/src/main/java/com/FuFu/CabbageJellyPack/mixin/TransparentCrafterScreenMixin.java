package com.FuFu.CabbageJellyPack.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CrafterScreen;
import net.minecraft.world.inventory.CrafterMenu;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Overwrite;
import static com.FuFu.CabbageJellyPack.Event.StaticData.*;
import static com.FuFu.CabbageJellyPack.GuiText.RenderLossUI.RESET_CRAFTER;

@Mixin(CrafterScreen.class)
public class TransparentCrafterScreenMixin extends AbstractContainerScreen<CrafterMenu> {
    public TransparentCrafterScreenMixin(CrafterMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    /**
     * 重写 renderBg
     * @author PinkCabbage
     * @reason want to disable original rendering to allow custom transparent rendering externally.
     */
    @Overwrite
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;

        guiGraphics.pose().pushPose();
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, InventoryAlpha);
        if (RESET_CRAFTER!= null) {
            guiGraphics.blit(RESET_CRAFTER, i, j, 0, 0, this.imageWidth, this.imageHeight);
        }
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
        guiGraphics.pose().popPose();
    }
}
