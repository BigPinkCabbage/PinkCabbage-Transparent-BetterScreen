package com.FuFu.CabbageJellyPack.key;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.common.NeoForge;

public class Events {
    public static void init()
    {
        NeoForge.EVENT_BUS.register(Events.class);
    }
    // 控制坐标显示状态
    public static boolean showCoordinates = false;
    public static boolean showDurability = false;

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (event.getKey() == KeyBindings.OPEN_COORDS.getKey().getValue() && event.getAction() == 1) {
            // 按下O键，切换显示状态
            showCoordinates = !showCoordinates;
        }
    }
}