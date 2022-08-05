package com.theswirlingvoid.polarmachinery.block.block.thermalpipe.thermalpiperegion;

import java.io.File;
import java.util.Set;

import com.theswirlingvoid.polarmachinery.configuration.thermalpipe.ThermalPipeConfiguration;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ThermalPipeRegionManager {

	public static ThermalPipeConfiguration config;

	public ThermalPipeRegionManager(World world)
	{
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

	public static void createNewRegion(World world, BlockPos pos)
	{
		File newFile = config.createNewNetworkFile(world);
		
		config.writeJSONEntryToFile(newFile, ThermalPipeConfiguration.NETWORK_KEY, config.posToLongSetElement(pos));
	}

	// public static void appendToNetwork(World world, BlockPos pos, int id)
	// {
	// 	File configFile = config.getNetworkFileFromID(world, id);

	// 	config.writeJSONEntryToFile(configFile, ThermalPipeConfiguration.NETWORK_KEY, config.posToLongSetElement(pos));
	// }

	public static ThermalPipeRegion readNetwork(World world, int id)
	{
		File regFile = config.getNetworkFileFromID(world, id);
		Set<Long> positions = (Set<Long>) config.getValueFromFile(regFile, ThermalPipeConfiguration.NETWORK_KEY);

		return new ThermalPipeRegion(positions);
	}

	// public static Set<ThermalPipeRegion> getAdjacentRegions(World world, BlockPos pos)
	// {

	// }



	// public static ArrayList<ThermalPipeRegion> getAdjacentPipeRegions(World world, ArrayList<BlockPos> positionsToCheck)
	// {
	// 	if (!world.isClient){
	// 		List<File> files = config.getConfigurationFiles();

	// 		File[] dimensionRegions = ThermalPipeConfiguration.getDimensionRegionFiles(world);

	// 		for (File regionFile : dimensionRegions)
	// 		{

	// 			// ThermalPipeConfiguration.readAllJsonFromFile(regionFile);
	// 		}
	// 	}
	// }
}
