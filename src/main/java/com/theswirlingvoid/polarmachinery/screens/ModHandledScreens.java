package com.theswirlingvoid.polarmachinery.screens;

import com.theswirlingvoid.polarmachinery.item.utility.applicator.screen.inventory.ApplicatorHandledScreen;

import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class ModHandledScreens {
	
	public static void registerHandledScreens()
	{
		HandledScreens.register(ModScreenHandlers.TOOL_INV_SCREEN_HANDLER_TYPE, ApplicatorHandledScreen::new);
	}
}
