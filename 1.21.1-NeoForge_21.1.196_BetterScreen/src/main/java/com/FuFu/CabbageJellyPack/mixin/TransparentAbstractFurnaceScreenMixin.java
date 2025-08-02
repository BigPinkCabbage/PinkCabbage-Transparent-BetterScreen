package com.FuFu.CabbageJellyPack.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.*;

import static com.FuFu.CabbageJellyPack.Event.StaticData.*;


@Mixin(AbstractFurnaceScreen.class)
public abstract class TransparentAbstractFurnaceScreenMixin extends AbstractContainerScreen {


    public TransparentAbstractFurnaceScreenMixin(AbstractContainerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    /**
     * 重写 renderBg
     * @author PinkCabbage
     * @reason want to disable original rendering to allow custom transparent rendering externally.
     */

    @Overwrite
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = this.leftPos;
        int j = this.topPos;

        if (Minecraft.getInstance().screen instanceof AbstractContainerScreen<?> furnacescreen) {
            AbstractContainerMenu menu = furnacescreen.getMenu();
            if (menu instanceof AbstractFurnaceMenu furnaceMenu &&
                    furnacescreen instanceof AbstractFurnaceScreenAccessor accessor) {

                ResourceLocation flameSprite = accessor.getLitProgressSprite();
                ResourceLocation arrowSprite = accessor.getBurnProgressSprite();
                ResourceLocation Texture = accessor.getTexture();

                guiGraphics.pose().pushPose();
                RenderSystem.enableBlend();
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, InventoryAlpha);
                guiGraphics.blit(Texture, i, j, 0, 0, this.imageWidth, this.imageHeight);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.disableBlend();
                guiGraphics.pose().popPose();

                if (furnaceMenu.isLit()) {
                    int k = 14;
                    int l = Mth.ceil(furnaceMenu.getLitProgress() * 13.0F) + 1;
                    guiGraphics.blitSprite(flameSprite, 14, 14, 0, 14 - l, i + 56, j + 36 + 14 - l, 14, l);
                }

                int i1 = 24;
                int j1 = Mth.ceil(furnaceMenu.getBurnProgress() * 24.0F);
                guiGraphics.blitSprite(arrowSprite, 24, 16, 0, 0, i + 79, j + 34, j1, 16);
            }
        }
    }
}

