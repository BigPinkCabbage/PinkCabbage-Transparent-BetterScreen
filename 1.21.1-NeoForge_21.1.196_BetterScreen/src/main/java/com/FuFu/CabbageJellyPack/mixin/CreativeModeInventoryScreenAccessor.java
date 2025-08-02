package com.FuFu.CabbageJellyPack.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.client.gui.CreativeTabsScreenPage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CreativeModeInventoryScreen.class)
public interface CreativeModeInventoryScreenAccessor {
    // 字段访问器
    @Accessor("scrollOffs")
    float getScrollOffset();

    @Accessor("searchBox")
    EditBox getSearchBox();

    @Accessor("selectedTab")
    CreativeModeTab getSelectedTab();

    @Accessor("currentPage")
    CreativeTabsScreenPage getCurrentPage();

    // 方法调用器
    @Invoker("renderTabButton")
    void invokeRenderTabButton(GuiGraphics guiGraphics, CreativeModeTab tab);
}