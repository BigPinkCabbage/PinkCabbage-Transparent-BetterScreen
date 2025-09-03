package com.FuFu.CabbageJellyPack.LoggedInText;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerLoggedInHandler {
    @SubscribeEvent
    public static void onPlayerLogin(ClientPlayerNetworkEvent.LoggingIn event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;

        if (player != null) {
            // 构建完整的消息组件
            MutableComponent message = Component.translatable("message.login.welcome")
                    .append("\n")
                    .append(Component.translatable("message.login.contact_instructions"))
                    .append("\n")
                    .append(Component.translatable("message.login.mod_menu_steps"))
                    .append("\n")
                    .append(Component.translatable("message.login.command_help"))
                    .append("\n")
                    .append(Component.translatable("message.login.enjoy"));

            // 在客户端显示消息
            player.displayClientMessage(message, false);
        }
    }
}