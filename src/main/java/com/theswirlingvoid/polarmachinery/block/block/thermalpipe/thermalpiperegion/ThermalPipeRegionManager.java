package com.theswirlingvoid.polarmachinery.block.block.thermalpipe.thermalpiperegion;

import com.theswirlingvoid.polarmachinery.block.block.thermalpipe.PipeNetwork;
import com.theswirlingvoid.polarmachinery.configuration.thermalpipe.ThermalPipeConfiguration;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.io.File;
import java.util.Optional;

public class ThermalPipeRegionManager {

	public static ThermalPipeConfiguration config;
	private final World world;

	public ThermalPipeRegionManager(World world)
	{
		this.world = world;
		config = new ThermalPipeConfiguration(world);
	}

	// public static int determineRegionID(World world, BlockPos pos)
	// {
	// 	long longPos = pos.asLong();
	// 	File[] regionFiles = config.getDimensionRegionFiles(world);
	// 	for (int index = 0; index < regionFiles.length; index++)
	// 	{
	// 		ThermalPipeRegion fileRegion = readNetwork(world, index);
	// 		if (fileRegion.getRegion().contains(longPos))
	// 		{
	// 			return index;
	// 		}
	// 	}
	// 	return -1;
	// }

	// public static void searchForRegionContaining(World world, BlockPos pos)
	// {

	// }

	public void saveNetwork(PipeNetwork network)
	{
		File newFile = config.createNewNetworkFile(network.getNetworkWorld());
		config.overwriteJSONToFile(newFile, network.toJSON());
	}

	// public static void appendToNetwork(World world, BlockPos pos, int id)
	// {
	// 	File configFile = config.getNetworkFileFromID(world, id);

	// 	config.writeJSONEntryToFile(configFile, ThermalPipeConfiguration.NETWORK_KEY, config.posToLongSetElement(pos));
	// }

	public Optional<File> readNetworkMatchingPipe(BlockPos pos)
	{
		return config.matchPipeEndToFile(world, pos);
	}

	public void deleteRegionsIncludingPipe(BlockPos pos)
	{
		config.deleteFileWithMatchingPipe(world, pos);
	}
}
