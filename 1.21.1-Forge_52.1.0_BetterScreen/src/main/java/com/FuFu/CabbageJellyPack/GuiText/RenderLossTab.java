package com.FuFu.CabbageJellyPack.GuiText;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.recipebook.RecipeBookTabButton;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class RenderLossTab {
    private static final ResourceLocation RESET_SEARCH_SPRITE = ResourceLocation.tryBuild("minecraft", "textures/gui/sprites/icon/search.png");
    public static void RenderLoss (GuiGraphics graphics, RecipeBookTabButton button) {

        List<ItemStack> icons = button.getCategory().getIconItems();
        int offset = button.isStateTriggered() ? -2 : 0;

        // 重新绘制物品图标（覆盖原版渲染）
        if (icons.size() == 1) {
            graphics.renderFakeItem(icons.getFirst(), button.getX() + 9 + offset, button.getY() + 5);
        } else if (icons.size() == 2) {
            graphics.renderFakeItem(icons.get(0), button.getX() + 3 + offset, button.getY() + 5);
            graphics.renderFakeItem(icons.get(1), button.getX() + 14 + offset, button.getY() + 5);
        }



        Minecraft mc = Minecraft.getInstance();
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        if (RESET_SEARCH_SPRITE != null) {
            graphics.blit(RESET_SEARCH_SPRITE, (screenWidth - 147) / 2 - 101 + 25, (screenHeight - 166) / 2 + 14, 0,0,12, 12,12,12);
        }
    }
}