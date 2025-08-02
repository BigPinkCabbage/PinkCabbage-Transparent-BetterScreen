package com.FuFu.CabbageJellyPack.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.BrewingStandScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.BrewingStandMenu;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

import static com.FuFu.CabbageJellyPack.Event.StaticData.*;
import static com.FuFu.CabbageJellyPack.GuiText.RenderLossUI.*;

@Mixin(BrewingStandScreen.class)
public class TransparentBrewingStandScreenMixin extends AbstractContainerScreen<BrewingStandMenu> {

    public TransparentBrewingStandScreenMixin(BrewingStandMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }
    @Unique
    private static final int[] cabbagepinkpack1_0_1$BUBBLELENGTHS = new int[]{29, 24, 20, 16, 11, 6, 0};
    /**
     * 重写 renderBg
     *
     * @author PinkCabbage
     * @reason want to disable original rendering to allow custom transparent rendering externally.
     */
    @Overwrite
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;

        int k = this.menu.getFuel();
        int i1 = this.menu.getBrewingTicks();

        if (RESET_BREWING_STAND != null) {
            guiGraphics.pose().pushPose();
            RenderSystem.enableBlend();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, InventoryAlpha);
            guiGraphics.blit(RESET_BREWING_STAND, i, j, 0, 0, this.imageWidth, this.imageHeight);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.disableBlend();
            guiGraphics.pose().popPose();
        }

        int l = Mth.clamp((18 * k + 20 - 1) / 20, 0, 18);
        if (l > 0) {
            if (RESET_FUEL_LENGTH_SPRITE != null) {
                guiGraphics.blit(RESET_FUEL_LENGTH_SPRITE, i + 60, j + 44,0, 0,  l, 4,18, 4 );
            }
        }

        if (i1 > 0) {
            int j1 = (int)(28.0F * (1.0F - (float)i1 / 400.0F));
            if (j1 > 0) {
                if (RESET_BREW_PROGRESS_SPRITE != null) {
                    guiGraphics.blit(RESET_BREW_PROGRESS_SPRITE, i + 97, j + 16,0, 0, 9, j1,9, 28);
                }
            }

            j1 = cabbagepinkpack1_0_1$BUBBLELENGTHS[i1 / 2 % 7];
            if (j1 > 0) {
                if (RESET_BUBBLES_SPRITE != null) {
                    guiGraphics.blit(RESET_BUBBLES_SPRITE, i + 63, j + 14 + 29 - j1,0, 29 - j1,  12, j1,12, 29);
                }
            }
        }

    }
}
