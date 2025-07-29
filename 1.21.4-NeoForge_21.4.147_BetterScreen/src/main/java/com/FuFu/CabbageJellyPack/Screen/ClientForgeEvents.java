package com.FuFu.CabbageJellyPack.Screen;

import com.FuFu.CabbageJellyPack.key.Events;
import com.FuFu.CabbageJellyPack.key.KeyBindings;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;

import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;

@OnlyIn(Dist.CLIENT)
public class ClientForgeEvents {
    public static void init()
    {
        NeoForge.EVENT_BUS.register(ClientForgeEvents.class);
    }
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (event.getKey() == KeyBindings.OPEN_SETTINGS.getKey().getValue() && event.getAction() == 1) {
            Minecraft mc = Minecraft.getInstance();
            /*
            if(!HasPressed) {
                SettingsIO.load();
            }
            HasPressed = true;
            */
            if (mc.screen instanceof MainSettingScreen) {
                mc.setScreen(null); // 如果已经打开，关闭它
            } else {
                mc.setScreen(new MainSettingScreen()); // 否则打开
            }
        }
    }


}
