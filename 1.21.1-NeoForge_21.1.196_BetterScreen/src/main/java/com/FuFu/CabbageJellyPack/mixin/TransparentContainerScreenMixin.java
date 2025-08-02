package com.FuFu.CabbageJellyPack.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ChestMenu;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import static com.FuFu.CabbageJellyPack.Event.StaticData.*;
import static com.FuFu.CabbageJellyPack.GuiText.RenderLossUI.RESET_CONTAINER;

@Mixin(ContainerScreen.class)
public class TransparentContainerScreenMixin extends AbstractContainerScreen<ChestMenu> implements MenuAccess<ChestMenu> {
    public TransparentContainerScreenMixin(ChestMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageHeight = 114 + pMenu.getRowCount() * 18;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    /**
     * 重写 renderBg，跳过对 CONTAINER_TEXTURE 的 blit 渲染
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
        if (RESET_CONTAINER != null) {
            guiGraphics.blit(RESET_CONTAINER, i, j, 0, 0, this.imageWidth, CONTAINERROWS * 18 + 17);
            guiGraphics.blit(RESET_CONTAINER, i, j + CONTAINERROWS * 18 + 17, 0, 126, this.imageWidth, 96);
        }
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
        guiGraphics.pose().popPose();
    }
}

