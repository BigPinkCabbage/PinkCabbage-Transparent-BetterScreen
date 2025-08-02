package com.FuFu.CabbageJellyPack.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.CreativeModeTab;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import static com.FuFu.CabbageJellyPack.Event.StaticData.InventoryAlpha;

@Mixin(CreativeModeInventoryScreen.class)
public class TransparentCreativeUIMixin extends EffectRenderingInventoryScreen<CreativeModeInventoryScreen.ItemPickerMenu> {

    public TransparentCreativeUIMixin(LocalPlayer player, FeatureFlagSet enabledFeatures, boolean displayOperatorCreativeTab) {
        super(new CreativeModeInventoryScreen.ItemPickerMenu(player), player.getInventory(), CommonComponents.EMPTY);
        player.containerMenu = this.menu;
        this.imageHeight = 136;
        this.imageWidth = 195;
    }


    /**
     * 重写 renderBg，跳过对 CONTAINER_TEXTURE 的 blit 渲染
     * @author PinkCabbage
     * @reason want to disable original rendering to allow custom transparent rendering externally.
     */
    @Overwrite
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        CreativeModeInventoryScreenAccessor screen = (CreativeModeInventoryScreenAccessor) (Object) this;


        for (CreativeModeTab creativemodetab : screen.getCurrentPage().getVisibleTabs()) {
            if (creativemodetab != screen.getSelectedTab()) {
                guiGraphics.pose().pushPose();
                RenderSystem.enableBlend();
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, InventoryAlpha);
                screen.invokeRenderTabButton(guiGraphics, creativemodetab);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.disableBlend();
                guiGraphics.pose().popPose();
            }
        }



        guiGraphics.pose().pushPose();
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, InventoryAlpha);
        guiGraphics.blit(screen.getSelectedTab().getBackgroundTexture(), this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
        guiGraphics.pose().popPose();

        screen.getSearchBox().render(guiGraphics, mouseX, mouseY, partialTick);

        int j = this.leftPos + 175;
        int k = this.topPos + 18;
        int i = k + 112;

        if (screen.getSelectedTab().canScroll()) {
            ResourceLocation resourcelocation = screen.getSelectedTab().getScrollerSprite(); // this.canScroll() ? SCROLLER_SPRITE : SCROLLER_DISABLED_SPRITE;
            guiGraphics.blitSprite(resourcelocation, j, k + (int)((float)(i - k - 17) * screen.getScrollOffset()), 12, 15);
        }



        if (screen.getCurrentPage().getVisibleTabs().contains(screen.getSelectedTab()))
        {
            guiGraphics.pose().pushPose();
            RenderSystem.enableBlend();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, InventoryAlpha);
            screen.invokeRenderTabButton(guiGraphics, screen.getSelectedTab());
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.disableBlend();
            guiGraphics.pose().popPose();
        }

        if (screen.getSelectedTab().getType() == CreativeModeTab.Type.INVENTORY) {
            InventoryScreen.renderEntityInInventoryFollowsMouse(
                    guiGraphics,
                    this.leftPos + 73,
                    this.topPos + 6,
                    this.leftPos + 105,
                    this.topPos + 49,
                    20,
                    0.0625F,
                    (float)mouseX,
                    (float)mouseY,
                    this.minecraft.player
            );
        }
    }

    /*
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
    */
}