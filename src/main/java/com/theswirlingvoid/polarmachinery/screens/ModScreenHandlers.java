package com.theswirlingvoid.polarmachinery.screens;

import com.theswirlingvoid.polarmachinery.item.utility.applicator.screen.inventory.ApplicatorScreenHandler;

import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.registry.Registry;

public class ModScreenHandlers {

	public static final int SWITCHER_INVENTORY_SIZE = 8;
	public static ScreenHandlerType<ApplicatorScreenHandler> TOOL_INV_SCREEN_HANDLER_TYPE = new ScreenHandlerType<>((syncId, playerInventory) -> new ApplicatorScreenHandler(syncId, playerInventory, ItemStack.EMPTY));

	public static void registerScreenHandlers()
	{
		Registry.register(Registry.SCREEN_HANDLER, ApplicatorScreenHandler.getIdentifier(), ModScreenHandlers.TOOL_INV_SCREEN_HANDLER_TYPE);
	}
}
