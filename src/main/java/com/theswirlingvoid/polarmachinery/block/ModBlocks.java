package com.theswirlingvoid.polarmachinery.block;

import com.theswirlingvoid.polarmachinery.block.block.thermalpipe.ThermalPipe;

import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.hot.Burner;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
	// applicator
	public static final Block THERMAL_PIPE = new ThermalPipe();
	public static final BlockItem THERMAL_PIPE_BLOCKITEM = ThermalPipe.getBlockItem();


//	GENERATION, RECEIVING, INTERFACING MACHINES
	public static final Block BURNER = new Burner();

	public static void registerBlocksAndBlockItems()
	{
		Registry.register(Registry.BLOCK, ThermalPipe.getIdentifier(), THERMAL_PIPE);
		Registry.register(Registry.ITEM, ThermalPipe.getIdentifier(), THERMAL_PIPE_BLOCKITEM);
	}

	public static void registerBlockEntities()
	{

	}
}
