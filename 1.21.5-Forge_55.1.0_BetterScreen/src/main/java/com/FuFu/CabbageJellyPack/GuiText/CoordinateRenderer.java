package com.FuFu.CabbageJellyPack.GuiText;

import com.FuFu.CabbageJellyPack.key.Events;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.ServerTickRateManager;
import net.minecraft.world.TickRateManager;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class CoordinateRenderer {

    private static long lastRTUpdateTime = 0;
    private static String cachedRTText = "";
    private static int cachedRTColor = 0xFFFFFF;

    public static void render(GuiGraphics guiGraphics) {
        if (!Events.showCoordinates) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        Level level = mc.level;
        LocalPlayer player = mc.player;

        // 左上角坐标
        if (level != null && player != null && level.isClientSide) {
            int xi = (int) player.getX();
            int yi = (int) player.getY();
            int zi = (int) player.getZ();

            Component coords = Component.translatable("hud.cabbagemod.position", xi, yi, zi);
            guiGraphics.drawString(
                    mc.font,
                    coords,
                    5,
                    5,
                    0xFFFFFF,
                    true
            );
        }

        // 右下角 RT 信息每 2 秒更新一次
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastRTUpdateTime >= 2000) {
            lastRTUpdateTime = currentTime;

            String s1 = "";
            TickRateManager tickRateManager = (level != null) ? level.tickRateManager() : null;
            if (tickRateManager != null && tickRateManager.isSteppingForward()) {
                s1 = Component.translatable("text.coordinates.frozen_stepping").getString();
            }
            else if (tickRateManager != null && tickRateManager.isFrozen()) {
                s1 = Component.translatable("text.coordinates.frozen").getString();
            }
            IntegratedServer integratedServer = mc.getSingleplayerServer();
            float tickTime = 0;
            if (integratedServer != null) {
                ServerTickRateManager serverTickRateManager = integratedServer.tickRateManager();
                boolean flag = serverTickRateManager.isSprinting();
                if (flag) {
                    s1 = Component.translatable("text.coordinates.sprinting").getString();
                }

                tickTime = integratedServer.getCurrentSmoothedTickTime();
                String tickTimeText = (tickRateManager != null && !flag)
                        ? String.format(Locale.ROOT, "%.1f", tickRateManager.millisecondsPerTick())
                        : "-";
                cachedRTText = String.format(Locale.ROOT, "%s : %.1f/%s ms%s",
                        Component.translatable("hud.cabbagemod.rt").getString(),
                        tickTime, tickTimeText, s1);
            } else {
                ClientPacketListener connection = mc.getConnection();
                if (connection != null) {
                    cachedRTText = String.format(Locale.ROOT, "\"%s\" %s%s",
                            connection.serverBrand(),
                            Component.translatable("text.coordinates.server").getString(),
                            s1);
                }
            }
            // 设置颜色
            if (tickTime <= 10) {
                cachedRTColor = 0x00FF00; // 绿色
            } else if (tickTime <= 20) {
                cachedRTColor = 0xFFFFFF; // 白色
            } else if (tickTime <= 30) {
                cachedRTColor = 0xFFA500; // 橙色
            } else {
                cachedRTColor = 0xFF0000; // 红色
            }
        }
        // 每帧绘制缓存的 RT 信息
        int pX = mc.getWindow().getGuiScaledWidth() - mc.font.width(cachedRTText) - 5;
        int pY = mc.getWindow().getGuiScaledHeight() - 12;
        guiGraphics.drawString(
                mc.font,
                cachedRTText,
                pX,
                pY,
                cachedRTColor,
                true
        );
    }
}

