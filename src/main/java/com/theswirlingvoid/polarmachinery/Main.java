package com.theswirlingvoid.polarmachinery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.theswirlingvoid.polarmachinery.block.ModBlocks;
import com.theswirlingvoid.polarmachinery.item.items.ModItems;
import com.theswirlingvoid.polarmachinery.screens.ModHandledScreens;
import com.theswirlingvoid.polarmachinery.screens.ModScreenHandlers;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class Main implements ModInitializer, ClientModInitializer {

	public static final String MOD_ID = "polarmachinery";

	public static final ItemGroup IGROUP = FabricItemGroupBuilder.create(new Identifier(MOD_ID, "all"))
		.icon(() -> new ItemStack(Items.BLAZE_ROD))
		.build();
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("modid");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		//* REGISTER ALL SIDES HERE */
		ModHandledScreens.registerHandledScreens();
		//ITEMS
		ModItems.registerItems();

		ModBlocks.registerBlocksAndBlockItems();
		ModBlocks.registerBlockEntities();
	}
	
	@Override
	public void onInitializeClient() {
		//* */ REGISTER ON CLIENT ONLY
		ModScreenHandlers.registerScreenHandlers();
		//ScreenRegistry.register(Screens.TOOL_INV_SCREEN_HANDLER_TYPE, ToolInventoryScreenHandler::new);
	}

	
}
