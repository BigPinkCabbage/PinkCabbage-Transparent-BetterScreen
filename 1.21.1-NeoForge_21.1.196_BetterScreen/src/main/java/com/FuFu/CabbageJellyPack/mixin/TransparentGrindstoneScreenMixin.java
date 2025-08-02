package com.FuFu.CabbageJellyPack.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.GrindstoneScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.GrindstoneMenu;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

import static com.FuFu.CabbageJellyPack.Event.StaticData.*;
import static com.FuFu.CabbageJellyPack.GuiText.RenderLossUI.RESET_GRINDSTONE;

@Mixin(GrindstoneScreen.class)
public abstract class TransparentGrindstoneScreenMixin extends AbstractContainerScreen<GrindstoneMenu> {
    @Unique
    private static final ResourceLocation ERROR_SPRITE = ResourceLocation.withDefaultNamespace("container/grindstone/error");
    public TransparentGrindstoneScreenMixin(GrindstoneMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
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
        if (RESET_GRINDSTONE != null) {
            guiGraphics.blit(RESET_GRINDSTONE, i, j, 0, 0, this.imageWidth, this.imageHeight);
        }
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
        guiGraphics.pose().popPose();

        if ((this.menu.getSlot(0).hasItem() || this.menu.getSlot(1).hasItem()) && !this.menu.getSlot(2).hasItem()) {
            guiGraphics.blitSprite(ERROR_SPRITE, i + 92, j + 31, 28, 21);
        }
    }
}
