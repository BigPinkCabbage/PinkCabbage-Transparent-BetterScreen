package com.FuFu.CabbageJellyPack.LoggedInText;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import static com.FuFu.CabbageJellyPack.Event.StaticData.InventoryAlpha;
import static com.FuFu.CabbageJellyPack.Event.StaticData.SettingScreenAlpha;

public class CabbageModHelp {
    public static void onRegisterCommands(RegisterClientCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("CabbageMod")
                .requires(source -> source.hasPermission(0))
                .then(Commands.argument("help", StringArgumentType.word())
                        .executes(context -> {
                            context.getSource().sendSuccess(
                                    () -> Component.translatable("command.cabbagemod.help"),
                                    false
                            );
                            return 1;
                        })));

        dispatcher.register(
                Commands.literal("CabbageMod")
                        .then(Commands.literal("InventoryAlpha")
                                .then(Commands.argument("value", FloatArgumentType.floatArg(0.0f, 1.0f))
                                        .executes(InventoryContext -> {
                                            float value = FloatArgumentType.getFloat(InventoryContext, "value");
                                            InventoryAlpha = value;

                                            InventoryContext.getSource().sendSuccess(() ->
                                                            Component.translatable("command.cabbagemod.inventoryalpha.set", value),
                                                    true);
                                            return 1;
                                        })
                                )
                        )
        );

        dispatcher.register(
                Commands.literal("CabbageMod")
                        .then(Commands.literal("SettingScreenAlpha")
                                .then(Commands.argument("value", FloatArgumentType.floatArg(0.1f, 1.0f))
                                        .executes(SettingScreenContext -> {
                                            float value = FloatArgumentType.getFloat(SettingScreenContext, "value");
                                            SettingScreenAlpha = value;

                                            SettingScreenContext.getSource().sendSuccess(() ->
                                                            Component.translatable("command.cabbagemod.settingscreenalpha.set", value),
                                                    true);
                                            return 1;
                                        })
                                )
                        )
        );
    }
}