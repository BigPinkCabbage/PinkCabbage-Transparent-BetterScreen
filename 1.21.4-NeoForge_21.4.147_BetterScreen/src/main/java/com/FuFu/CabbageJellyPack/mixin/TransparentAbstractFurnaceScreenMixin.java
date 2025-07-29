package com.FuFu.CabbageJellyPack.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.RecipeBookMenu;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import static com.FuFu.CabbageJellyPack.Event.StaticData.*;


@Mixin(AbstractFurnaceScreen.class)
public abstract class TransparentAbstractFurnaceScreenMixin extends AbstractRecipeBookScreen {


    public TransparentAbstractFurnaceScreenMixin(RecipeBookMenu pMenu, RecipeBookComponent pRecipeBookComponent, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pRecipeBookComponent, pPlayerInventory, pTitle);
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

        FURNACE_I = i;
        FURNACE_J = j;
        FURNACE_LabelX = this.titleLabelX;
        FURNACEHEIGHT = this.imageHeight;

                AbstractFurnaceScreenAccessor accessor = (AbstractFurnaceScreenAccessor) this;
                ResourceLocation flameSprite = accessor.getLitProgressSprite();
                ResourceLocation arrowSprite = accessor.getBurnProgressSprite();
                if (menu instanceof AbstractFurnaceMenu furnaceMenu){
                    guiGraphics.pose().pushPose();
                    guiGraphics.pose().translate(0, 0, 500);
                if (furnaceMenu.isLit()){
                    int l = Mth.ceil(furnaceMenu.getLitProgress() * 13.0F) + 1;
                    guiGraphics.blitSprite(RenderType::guiTextured, flameSprite, 14, 14, 0, 14 - l, FURNACE_I + 56, FURNACE_J + 36 + 14 - l, 14, l);
                }
                int j1 = Mth.ceil(furnaceMenu.getBurnProgress() * 24.0F);
                guiGraphics.blitSprite(RenderType::guiTextured, arrowSprite, 24, 16, 0, 0, FURNACE_I + 79, FURNACE_J + 34, j1, 16);
                guiGraphics.pose().popPose();
            }
        }
    }




