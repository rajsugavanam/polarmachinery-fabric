package com.theswirlingvoid.polarmachinery.configuration.thermalpipe;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.theswirlingvoid.polarmachinery.configuration.BaseConfiguration;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ThermalPipeConfiguration extends BaseConfiguration {

	
	private static String PIPE_NETWORK_DIR_PATH;
	private static final String FILE_FORMAT = ".json";
	
	public static final String NETWORK_KEY = "Network";

	public ThermalPipeConfiguration(World world) {
		super(world);
		PIPE_NETWORK_DIR_PATH = getModConfigDir() + "/thermalpipe/thermalpipenetworks";
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
		return new File(getDimensionPath(dimension)).listFiles();
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

	public File getNetworkFileFromID(World world, int id)
	{

		String fileIdString = Integer.toString(id);
		String dimName = getDimensionNameFromWorld(world);

		File newFile = new File(PIPE_NETWORK_DIR_PATH + "/" + dimName + "/" + fileIdString + FILE_FORMAT);

		return newFile;
	}

	public Set<Long> posToLongSetElement(BlockPos pos)
	{
		Set<Long> ts = new HashSet<>();
		ts.add(pos.asLong());
		return ts;
	}


	
	@Override
	protected void setBasePath() {
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
