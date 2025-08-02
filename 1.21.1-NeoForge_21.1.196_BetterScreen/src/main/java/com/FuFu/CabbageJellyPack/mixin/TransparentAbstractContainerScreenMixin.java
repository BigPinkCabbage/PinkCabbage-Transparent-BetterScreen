package com.FuFu.CabbageJellyPack.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.FuFu.CabbageJellyPack.Event.StaticData.InventoryAlpha;

@Mixin(AbstractContainerScreen.class)
public class TransparentAbstractContainerScreenMixin {
    // 在 super.render() 调用前设置透明度
    @Inject(
            method = "renderBackground",
            at = @At("HEAD")
    )
    private void beforeRenderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        guiGraphics.pose().pushPose();
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, InventoryAlpha);
    }

    // 在 renderTooltip() 调用前恢复透明度
    @Inject(
            method = "renderBackground",
            at = @At("RETURN")
    )
    private void AfterRenderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
        guiGraphics.pose().popPose();
    }

    //---------------------------------------------------------------------------------------------------------------------------------------

    @Redirect(
            method = "renderBackground",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;renderTransparentBackground(Lnet/minecraft/client/gui/GuiGraphics;)V"
            )
    )
    private void redirectRenderTransparentBackground(AbstractContainerScreen<?> instance, GuiGraphics guiGraphics) {

    }
}

