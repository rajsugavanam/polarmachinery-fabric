package com.theswirlingvoid.polarmachinery.configuration.thermalpipe;

import java.io.*;
import java.util.*;

import com.theswirlingvoid.polarmachinery.block.block.thermalpipe.PipeNetwork;
import com.theswirlingvoid.polarmachinery.configuration.BaseConfiguration;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.json.JSONArray;
import org.json.JSONObject;

public class ThermalPipeConfiguration extends BaseConfiguration {

	// is set in getBasePath
	private static String PIPE_NETWORK_DIR_PATH;
	private static final String FILE_FORMAT = ".json";
	
	public static final String NETWORK_KEY = "Network";

	public ThermalPipeConfiguration(World world) {
		super(world);
	}

	public List<File> getPipeRegionFiles(World world) {
		return super.getConfigurationFiles();
	}

	private String getDimensionNameFromWorld(World world)
	{
		return world.getRegistryKey().getValue().toString().split(":")[1];
	}

	private String getDimensionPath(World dimension)
	{
		return (PIPE_NETWORK_DIR_PATH + "/" + getDimensionNameFromWorld(dimension));
	}

	public File[] getDimensionRegionFiles(World dimension)
	{
		// this removes the .DS_Store from this list and lists all other files.
		// screw you too, MacOS!
		File[] files = new File(getDimensionPath(dimension)).listFiles(
				(dir, name) -> (!name.equals(".DS_Store"))
		);

		if (files != null)
		{
			return files;
		}
		return new File[] {};
	}

	private UUID getNewNetworkId(World dimension)
	{
		// String dimName = getDimensionNameFromWorld(dimension);

		// int newFileId = 0;
		// while (true)
		// {
		// 	String directoryPath = PIPE_NETWORK_DIR_PATH + "/" + dimName;
		// 	String checkFilePath = directoryPath + "/" + newFileId + FILE_FORMAT;

		// 	if (!new File(checkFilePath).exists())
		// 	{
		// 		return newFileId;
		// 	}
		// 	newFileId++;
		// }
		return UUID.randomUUID();
	}


	// returns the new file
	public File createNewNetworkFile(World world)
	{

		UUID fileId = getNewNetworkId(world);
		String fileIdString = fileId.toString();

		String dimName = getDimensionNameFromWorld(world);

		File dir = new File(PIPE_NETWORK_DIR_PATH + "/" + dimName);
		File newFile = new File(dir.toPath().toString() + "/" + fileIdString + FILE_FORMAT);

		try {
			dir.mkdirs();
			newFile.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return newFile;
	}

	public Optional<File> matchPipeEndToFile(World dimension, BlockPos pos)
	{
		try {
			File[] dataFilesPaths = getDimensionRegionFiles(dimension);

			// if there are no region files
			if (dataFilesPaths.length == 0)
				return Optional.empty();

			// get the region file matching the parameters
			for (File dataFile : dataFilesPaths)
			{
				if (fileMatchesPipePos(dataFile, pos))
					return Optional.of(dataFile);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return Optional.empty();

	}

	public void deleteFileWithMatchingPipe(World dimension, BlockPos pos)
	{
		File[] dataFilesPaths = getDimensionRegionFiles(dimension);

		// get the region file matching the parameters
		for (File dataFile : dataFilesPaths)
		{
			try
			{
				if (fileMatchesPipePos(dataFile, pos))
				{
					if (!dataFile.delete())
					{
						System.out.println("File delete failed! Emptying out file instead.");
						emptyOutFile(dataFile);
						dataFile.deleteOnExit();
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	private void emptyOutFile(File file)
	{
		try (FileWriter writer = new FileWriter(file)) {
			writer.write("");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private boolean fileMatchesPipePos(File file, BlockPos pos)
	{
		Optional<Object> jsonValue = getValueFromFile(file, PipeNetwork.PIPE_BLOCKS_KEY);

		if (jsonValue.isPresent())
		{
			Set<Object> pipePositions = new HashSet<>( ((JSONArray) jsonValue.get()).toList() );

			return pipePositions.contains(pos.asLong());
		}

		return false;
	}

	public Set<Long> posToLongSetElement(BlockPos pos)
	{
		Set<Long> ts = new HashSet<>();
		ts.add(pos.asLong());
		return ts;
	}


	
	@Override
	protected void setBasePath() {
		PIPE_NETWORK_DIR_PATH = getModConfigDir() + "/thermalpipe/thermalpipenetworks";
		super.setBasePath(PIPE_NETWORK_DIR_PATH);
	}

	@Override
	protected void addConfigurationFiles() {
		super.addConfigurationFiles(getConfigurationFileNames());
	}

	private List<String> getConfigurationFileNames()
	{
		ArrayList<String> configFiles = new ArrayList<>();

		// configFiles.add(PIPE_REGION_FILENAME);

		return configFiles;
	}
}
