package com.FuFu.CabbageJellyPack.GuiText;

import com.FuFu.CabbageJellyPack.mixin.RecipeBookTabButtonAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeBookTabButton;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class RenderLossTab {
    private static final ResourceLocation RESET_SEARCH_SPRITE = ResourceLocation.tryBuild("minecraft", "textures/gui/sprites/icon/search.png");
    public static void RenderLoss (GuiGraphics graphics, RecipeBookTabButton button, float alpha) {
        RecipeBookTabButtonAccessor accessor = (RecipeBookTabButtonAccessor) button;
        RecipeBookComponent.TabInfo tabInfo = accessor.getTabInfo();


        int x = button.getX();
        int y = button.getY();
        int offset = button.isStateTriggered() ? -2 : 0;

        //WidgetSprites sprites = accessor.getSprites();
        //graphics.blitSprite(RenderType::guiTextured,sprites.get(true, button.isStateTriggered()), x + offset, y, button.getWidth(), button.getHeight());

        // 图标贴图（不透明）
        ItemStack primary = tabInfo.primaryIcon();
        Optional<ItemStack> secondaryOpt = tabInfo.secondaryIcon();

        if (secondaryOpt.isPresent()) {
            graphics.renderFakeItem(primary, x + 3 + offset, y + 5);
            graphics.renderFakeItem(secondaryOpt.get(), x + 14 + offset, y + 5);
        } else {
            graphics.renderFakeItem(primary, x + 9 + offset, y + 5);
        }

        Minecraft mc = Minecraft.getInstance();
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        if (RESET_SEARCH_SPRITE != null) {
            graphics.blit(RenderType::guiTextured, RESET_SEARCH_SPRITE, (screenWidth - 147) / 2 - 101 + 25, (screenHeight - 166) / 2 + 14, 0,0,12, 12,12,12);
        }
    }
}