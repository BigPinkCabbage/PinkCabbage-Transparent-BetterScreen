package com.FuFu.CabbageJellyPack.mixin;

import com.FuFu.CabbageJellyPack.GuiText.RenderLossTab;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.recipebook.RecipeBookTabButton;
import net.minecraft.client.renderer.entity.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import static com.FuFu.CabbageJellyPack.Event.StaticData.InventoryAlpha;

@Mixin(RecipeBookTabButton.class)
public class TransparentRecipeBookTabButtonMixin {
    /**
     * Hook 原版的 renderIcon 方法，并在渲染后重新绘制一次物品图标
     */
    @Inject(
            method = "renderIcon",
            at = @At("TAIL") // 在原版渲染完成后执行
    )
    private void onRenderIcon(GuiGraphics guiGraphics, ItemRenderer itemRenderer, CallbackInfo ci) {
        RenderLossTab.RenderLoss(guiGraphics, (RecipeBookTabButton)(Object)this);
    }

    @Inject(
            method = "renderWidget",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V",
                    ordinal = 0
            )
    )
    private void injectTransparency(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        // 设置透明度 (0.7f = 70% 不透明度)
        guiGraphics.pose().pushPose();
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, InventoryAlpha);
    }

    @Inject(
            method = "renderWidget",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V",
                    ordinal = 0,
                    shift = At.Shift.AFTER
            )
    )
    private void resetTransparency(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        // 重置颜色/透明度
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
        guiGraphics.pose().popPose();
    }
}