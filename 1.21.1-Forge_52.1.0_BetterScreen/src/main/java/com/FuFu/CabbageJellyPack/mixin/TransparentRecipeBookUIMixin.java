package com.FuFu.CabbageJellyPack.mixin;

import com.FuFu.CabbageJellyPack.GuiText.RenderLossTab ;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeBookTabButton;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static com.FuFu.CabbageJellyPack.Event.StaticData.InventoryAlpha;


@Mixin(RecipeBookComponent.class)
public class TransparentRecipeBookUIMixin {



    // 在绘制时
    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V",
                    ordinal = 0
            )
    )
    private void setAlphaBeforeBlit(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        guiGraphics.pose().pushPose();
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, InventoryAlpha);
    }




    // 在 filterButton 渲染前
    @Inject(
            method = "render",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/gui/screens/recipebook/RecipeBookComponent;filterButton:Lnet/minecraft/client/gui/components/StateSwitchingButton;",
                    opcode = Opcodes.GETFIELD
            )
    )
    private void setAlphaBeforeFilterButton(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
        guiGraphics.pose().popPose();
    }





    // 在 RecipeBookPage渲染
    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/recipebook/RecipeBookPage;render(Lnet/minecraft/client/gui/GuiGraphics;IIIIF)V"
            )
    )
    private void renderTransparentTabs(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {

        // 使用 Mixin Accessor 接口获取 tabButtons
        RecipeBookComponentAccessor accessor = (RecipeBookComponentAccessor) this;
        List<RecipeBookTabButton> tabButtons = accessor.getTabButtons();
        ClientRecipeBook recipeBook = accessor.getBooks();

        for (RecipeBookTabButton button : tabButtons) {
            // 1. 检查是否是搜索标签（始终显示）
            /*待改正
            if (button.getCategory() instanceof SearchRecipeBookCategory) {
                RenderLossTab.RenderLoss(guiGraphics, button, InventoryAlpha);
                continue;
            }


             */
            // 2. 检查玩家是否拥有该分类的配方
            if (button.updateVisibility(recipeBook)) {
                RenderLossTab.RenderLoss(guiGraphics, button);
            }
        }

        EditBox searchBox = accessor.getSearchBox();

        if (searchBox != null) {
            // 渲染搜索框
            searchBox.render(guiGraphics, mouseX, mouseY, partialTick);


        }

    }

}





