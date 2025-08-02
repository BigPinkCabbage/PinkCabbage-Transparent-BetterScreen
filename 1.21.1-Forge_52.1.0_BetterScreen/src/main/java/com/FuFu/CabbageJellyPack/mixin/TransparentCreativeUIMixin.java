package com.FuFu.CabbageJellyPack.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.FuFu.CabbageJellyPack.Event.StaticData.InventoryAlpha;

@Mixin(CreativeModeInventoryScreen.class)
    public abstract class TransparentCreativeUIMixin {

    //在物品渲染前设置透明度，但是标签图标仍然存在
    @Inject(method = "renderBg", at = @At("HEAD"))
    private void injectRenderBg(GuiGraphics guiGraphics, float p_282504_, int p_282089_, int p_282249_, CallbackInfo ci) {
        guiGraphics.pose().pushPose();
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, InventoryAlpha);
    }

    // 在渲染背景纹理后还原
    @Inject(method = "renderBg", at = @At("TAIL"))
    private void restoreColor(GuiGraphics guiGraphics, float p_282504_, int p_282089_, int p_282249_, CallbackInfo ci) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
        guiGraphics.pose().popPose();

    }
}
