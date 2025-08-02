package com.FuFu.CabbageJellyPack.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static com.FuFu.CabbageJellyPack.Event.StaticData.InventoryAlpha;


@Mixin(Gui.class)
public class TransparentHotbarUIMixin {
    @Shadow
    @Final
    @Mutable

    private static final ResourceLocation HOTBAR_SPRITE = ResourceLocation.tryBuild("minecraft", "textures/gui/sprites/hud/hotbar.png");
    private static final ResourceLocation HOTBAR_SELECTION_SPRITE = ResourceLocation.tryBuild("minecraft", "textures/gui/sprites/hud/hotbar_selection.png");
    @Redirect(
            method = "renderItemHotbar",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V"
            )
    )
    private void redirectHotbarBackground(
            GuiGraphics guiGraphics,
            ResourceLocation sprite,  // 注意：1.21.1 可能不再需要 Function 参数
            int x, int y, int width, int height
    ){
        // 只对快捷栏背景生效
        if (HOTBAR_SPRITE.equals(sprite)) {
            guiGraphics.pose().pushPose();
            RenderSystem.enableBlend();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, InventoryAlpha);
            // 使用 blit 手动绘制热键栏背景纹理
            guiGraphics.blit(
                    HOTBAR_SPRITE,                  // 纹理位置
                    x, y,                    // 屏幕位置
                    0, 0,              // 纹理偏移
                    width, height,          // 宽高
                    width, height            // 纹理图大小（注意：Atlas 中热键栏为 256x256）// ARGB颜色（含透明度）
            );
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.disableBlend();
            guiGraphics.pose().popPose();
        } else {
            guiGraphics.blit( sprite, x, y,0,0, width, height, width, height);
        }
    }
}