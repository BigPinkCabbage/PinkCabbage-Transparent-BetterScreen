package com.FuFu.CabbageJellyPack;

import com.FuFu.CabbageJellyPack.Event.StaticData;
import com.FuFu.CabbageJellyPack.GuiText.RenderLossUI;
import com.FuFu.CabbageJellyPack.LoggedInText.CabbageModHelp;
import com.FuFu.CabbageJellyPack.LoggedInText.PlayerLoggedInHandler;
import com.FuFu.CabbageJellyPack.Screen.ClientForgeEvents;
import com.FuFu.CabbageJellyPack.key.Events;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = CabbageJellyPack.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = CabbageJellyPack.MODID, value = Dist.CLIENT)
public class CabbageJellyPackClient {
    public CabbageJellyPackClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);

    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        CabbageJellyPack.LOGGER.info("HELLO FROM CLIENT SETUP");
        CabbageJellyPack.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());


    }

}
