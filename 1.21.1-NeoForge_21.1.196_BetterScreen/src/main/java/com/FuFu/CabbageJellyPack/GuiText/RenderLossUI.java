package com.FuFu.CabbageJellyPack.GuiText;

import com.FuFu.CabbageJellyPack.mixin.ContainerScreenAccesssor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.common.NeoForge;

import static com.FuFu.CabbageJellyPack.Event.StaticData.*;


public class RenderLossUI extends Screen{
    public static void initEvent()
    {
        NeoForge.EVENT_BUS.register(RenderLossUI.class);
    }

    public static final ResourceLocation RESET_SHULKERBOX = ResourceLocation.tryBuild("minecraft", "textures/gui/container/shulker_box.png");
    public static final ResourceLocation RESET_GRINDSTONE = ResourceLocation.tryBuild("minecraft", "textures/gui/container/grindstone.png");
    public static final ResourceLocation RESET_HOPPER = ResourceLocation.tryBuild("minecraft","textures/gui/container/hopper.png");
    public static final ResourceLocation RESET_CONTAINER = ResourceLocation.tryBuild("minecraft","textures/gui/container/generic_54.png");

    public static final ResourceLocation RESET_CRAFTING = ResourceLocation.tryBuild("minecraft","textures/gui/container/crafting_table.png");
    public static final ResourceLocation RESET_CRAFTER = ResourceLocation.tryBuild("minecraft","textures/gui/container/crafter.png");

    public static final ResourceLocation RESET_DISPENSER = ResourceLocation.tryBuild("minecraft", "textures/gui/container/dispenser.png");

    public static final ResourceLocation RESET_BREWING_STAND = ResourceLocation.tryBuild("minecraft", "textures/gui/container/brewing_stand.png");
    public static final ResourceLocation RESET_FUEL_LENGTH_SPRITE = ResourceLocation.tryBuild("minecraft","textures/gui/sprites/container/brewing_stand/fuel_length.png");
    public static final ResourceLocation RESET_BREW_PROGRESS_SPRITE = ResourceLocation.tryBuild("minecraft","textures/gui/sprites/container/brewing_stand/brew_progress.png");
    public static final ResourceLocation RESET_BUBBLES_SPRITE = ResourceLocation.tryBuild("minecraft","textures/gui/sprites/container/brewing_stand/bubbles.png");


    protected RenderLossUI(Component pTitle) {
        super(pTitle);
    }

    @SubscribeEvent
    public static void onScreenRender(ScreenEvent.Render.Post event) {

        if (Minecraft.getInstance().screen instanceof ContainerScreen containerscreen) {
            // 使用 screen 实例
            ContainerScreenAccesssor screenMixin = (ContainerScreenAccesssor) containerscreen;
            CONTAINERROWS = screenMixin.getContainerRows();

        }

        GuiGraphics guiGraphics = event.getGuiGraphics();
        Minecraft mc = Minecraft.getInstance();
        Player player = Minecraft.getInstance().player;

        if (mc.player == null) return;
        if (openCreativeMode) {
            if (player != null && player.isCreative()) {
                guiGraphics.pose().pushPose();
                guiGraphics.pose().translate(0, 0, 100);
                ItemStack compassStack = new ItemStack(Items.COMPASS);
                guiGraphics.renderItem(compassStack, currentleftPos + currentimageWidth - 21, currenttopPos - 19);

                // 钻石镐
                ItemStack diamondPickaxe = new ItemStack(Items.DIAMOND_PICKAXE);
                guiGraphics.renderItem(diamondPickaxe, currentleftPos + 5, currenttopPos + currentimageHeight + 3);

                // 下界合金剑
                ItemStack netheriteSword = new ItemStack(Items.NETHERITE_SWORD);
                guiGraphics.renderItem(netheriteSword, currentleftPos + 32, currenttopPos + currentimageHeight + 3);

                // 金苹果
                ItemStack goldenApple = new ItemStack(Items.GOLDEN_APPLE);
                guiGraphics.renderItem(goldenApple, currentleftPos + 59, currenttopPos + currentimageHeight + 3);

                ItemStack ironIngot = new ItemStack(Items.IRON_INGOT);
                guiGraphics.renderItem(ironIngot, currentleftPos + 86, currenttopPos + currentimageHeight + 3);
                // 猪的刷怪蛋
                ItemStack pigSpawnEgg = new ItemStack(Items.PIG_SPAWN_EGG);
                guiGraphics.renderItem(pigSpawnEgg, currentleftPos + 113, currenttopPos + currentimageHeight + 3);

                guiGraphics.pose().popPose();
            }
        }
    }

    static boolean openCreativeMode = false;
    static boolean FirstOpenCreativeMode = false;


    @SubscribeEvent
    public static void CreativeInventoryOpen(ScreenEvent.Opening event) {
        if (event.getScreen() instanceof CreativeModeInventoryScreen) {
            // 玩家打开了创造模式物品栏
            openCreativeMode = true;
            FirstOpenCreativeMode = true;
        }
    }
    @SubscribeEvent
    public static void CreativeInventoryClose(ScreenEvent.Closing event) {
        if (event.getScreen() instanceof CreativeModeInventoryScreen) {
            openCreativeMode = false;
        }
    }
}
