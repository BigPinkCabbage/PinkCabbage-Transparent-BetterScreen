package com.FuFu.CabbageJellyPack;

import com.FuFu.CabbageJellyPack.Event.StaticData;
import com.FuFu.CabbageJellyPack.GuiText.CoordinateRenderer;
import com.FuFu.CabbageJellyPack.GuiText.RenderLossUI;
import com.FuFu.CabbageJellyPack.LoggedInText.CabbageModHelp;
import com.FuFu.CabbageJellyPack.LoggedInText.PlayerLoggedInHandler;
import com.FuFu.CabbageJellyPack.Screen.ClientForgeEvents;
import com.FuFu.CabbageJellyPack.Screen.SettingsIO;
import com.FuFu.CabbageJellyPack.key.Events;
import com.FuFu.CabbageJellyPack.key.KeyBindings;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(value = CabbageJellyPack.MODID)
public class CabbageJellyPack {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "cabbagejellypack";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public CabbageJellyPack(IEventBus modEventBus, ModContainer modContainer) {
        // 注册 mod 生命周期事件
        if (FMLEnvironment.dist.isClient()) {
            modEventBus.addListener(this::commonSetup);
            modEventBus.addListener(this::onRegisterKeyMappings);
            modEventBus.addListener(this::preInitClient);
            modEventBus.addListener(this::onClientSetup);
        }
        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
    private void preInitClient(final FMLClientSetupEvent event)
    {
        PlayerLoggedInHandler.init();
        Events.init();
        ClientForgeEvents.init();
        CabbageModHelp.init();
        StaticData.init();
        RenderLossUI.initEvent();
        SettingsIO.init();
        CoordinateRenderer.init();
    }



    public void onClientSetup(FMLClientSetupEvent event) {
        SettingsIO.load(); // ✅ 游戏启动时加载设置
    }

    public void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(KeyBindings.OPEN_COORDS);
        event.register(KeyBindings.OPEN_SETTINGS);
    }



    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }
        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());
        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }

}
