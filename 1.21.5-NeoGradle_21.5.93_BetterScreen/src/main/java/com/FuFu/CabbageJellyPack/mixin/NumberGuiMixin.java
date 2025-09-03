package com.FuFu.CabbageJellyPack.mixin;


import com.FuFu.CabbageJellyPack.Screen.SettingsIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.FuFu.CabbageJellyPack.Event.StaticData.HasPressed;


@Mixin(Gui.class)
public class NumberGuiMixin {

    @Inject(method = "<init>", at = @At("RETURN"))
    private void LoadJson(Minecraft mc, CallbackInfo ci) {
        if(!HasPressed) {
           SettingsIO.load();
        }
        HasPressed = true;
    }

    //------------------------------------------------------------------------------------
    /*
    @Inject(
            method = "renderItemHotbar",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Gui;renderSlot(Lnet/minecraft/client/gui/GuiGraphics;IILnet/minecraft/client/DeltaTracker;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V",
                    ordinal = 1
            ),
            cancellable = true
    )
    private void skipOffhandSlot(GuiGraphics pGuiGraphics, DeltaTracker pDeltaTracker, CallbackInfo ci) {
        ci.cancel();
    }
    */

        @Redirect(
                method = "renderItemHotbar",
                at = @At(
                        value = "INVOKE",
                        target = "Lnet/minecraft/world/entity/player/Player;getOffhandItem()Lnet/minecraft/world/item/ItemStack;"
                )
        )
        private ItemStack disableOffhandRendering(Player player) {
            return ItemStack.EMPTY; // 强制返回空物品堆栈
        }

}