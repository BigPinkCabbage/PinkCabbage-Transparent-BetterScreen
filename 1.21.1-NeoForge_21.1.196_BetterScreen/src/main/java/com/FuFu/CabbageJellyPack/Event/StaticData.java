package com.FuFu.CabbageJellyPack.Event;


import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.common.NeoForge;

public class StaticData {
    public static void init()
    {
        NeoForge.EVENT_BUS.register(StaticData.class);
    }
    public static int currentleftPos = -1;
    public static int currenttopPos = -1;
    public static int currentimageHeight = -1;
    public static int currentimageWidth = -1;
    public static float InventoryAlpha = 0.5f;
    public static float SettingScreenAlpha = 1.0f;
    public static boolean cabbagepinkpack1_0_1$hasRenderedHotbarOnce = false;

    public static int CONTAINERROWS;

    public static boolean HasPressed = false; // 控制只触发一次

    @SubscribeEvent
    public static void cabbagepinkpack1_0_1$onPlayerLogin(ClientPlayerNetworkEvent.LoggingIn event) {
        cabbagepinkpack1_0_1$hasRenderedHotbarOnce = false;
    }

}
