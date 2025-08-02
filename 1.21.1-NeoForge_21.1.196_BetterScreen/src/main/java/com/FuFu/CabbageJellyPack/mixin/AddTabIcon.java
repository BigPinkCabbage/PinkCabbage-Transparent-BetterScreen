package com.FuFu.CabbageJellyPack.mixin;

import com.FuFu.CabbageJellyPack.Event.StaticData;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(CreativeModeInventoryScreen.class)

public abstract class AddTabIcon extends AbstractContainerScreen<AbstractContainerMenu> {

    public AddTabIcon(Container pMenu, Inventory pPlayerInventory, Component pTitle) {
        super((AbstractContainerMenu) pMenu, pPlayerInventory, pTitle);
    }

    @Inject(
            method = "renderTabButton",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;renderItem(Lnet/minecraft/world/item/ItemStack;II)V"
            )
    )
    public void onRenderTabButton(GuiGraphics pGuiGraphics, CreativeModeTab pCreativeModeTab, CallbackInfo ci) {
        StaticData.currentleftPos = leftPos;
        StaticData.currenttopPos = topPos;
        StaticData.currentimageHeight = imageHeight;
        StaticData.currentimageWidth = imageWidth;
    }
}


