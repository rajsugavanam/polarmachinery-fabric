package com.theswirlingvoid.polarmachinery.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.theswirlingvoid.polarmachinery.Main;

import net.minecraft.util.WorldSavePath;
import net.minecraft.world.World;

public abstract class BaseConfiguration {
	
	private static String MOD_CONFIG_DIR;

	protected String instancePath;
	protected List<String> instanceDataPathNames = new ArrayList<>();

	public BaseConfiguration(World world)
	{
		MOD_CONFIG_DIR = world.getServer().getSavePath(WorldSavePath.ROOT).resolve(Main.MOD_ID).toString();
		// a subclass will call this when created
		// subclasses of this do not need their own constructor
		setBasePath();
		addConfigurationFiles();
	}

	// does not take an absolute path. takes a directory path starting from the config path.
	protected void setBasePath(String path)
	{
		// instancePath = MOD_CONFIG_DIR + "/" + path;
		instancePath = path;

		new File(path).mkdirs();
	}

	// does not take an absolute path. takes a directory path starting from the config path.
	protected void addPath(String path)
	{
		new File(instancePath + "/" + path).mkdirs();
	}

	protected void addConfigurationFiles(List<String> fileNames)
	{
		instanceDataPathNames.addAll(fileNames);

		try {

			for (String fileName : fileNames)
			{
				/* ----------------- Initial Config File Setup/Initial Write ---------------- */
				File configFile = getConfigurationFile(fileName);
				if (!configFile.exists())
				{
					configFile.createNewFile();
					
					/* ----------------------------- Write To Files ----------------------------- */
					// writeInitialFile(configFile);
					/* -------------------------------------------------------------------------- */
				}
				/* -------------------------------------------------------------------------- */
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// protected void addConfigurationFile(String filePathAndName)
	// {
	// 	instanceDataPathNames.add(filePathAndName);
	// }

	protected abstract void setBasePath();
	protected abstract void addConfigurationFiles();

	/**
	 * Writes any given subclass's provided initial data to the appropriate configuration file.
	 * @param configFile The file to get initial data of and write to.
	 */
	// private void writeInitialFile(File configFile)
	// {
	// 	FileWriter writer;
	// 	try {
	// 		writer = new FileWriter(configFile);
	// 		writer.write(getInitialData(configFile.getName()));
	// 		writer.close();
	// 	} catch (IOException e) {
	// 		e.printStackTrace();
	// 	}
	// }

	// private void overwriteToFile(File configFile, String data)
	// {
	// 	// overwrites file
	// 	FileWriter writer;
	// 	try {
	// 		writer = new FileWriter(configFile);
	// 		writer.write(data);
	// 		writer.close();
	// 	} catch (IOException e) {
	// 		e.printStackTrace();
	// 	}
	// }

	public void writeJSONEntryToFile(File configFile, String key, Object value)
	{
		try {
			JSONObject currentJson = readAllJSONFromFile(configFile);
			currentJson.accumulate(key, value);

			FileWriter writer = new FileWriter(configFile);
			currentJson.write(writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// public static void writeJsonToFile(File configFile, JSONObject object)
	// {
	// 	try {
	// 		FileWriter fileWriter = new FileWriter(configFile);
	// 		JSONWriter writer = new JSONWriter(fileWriter);

	// 		// gets every key for the json object specified as an iterator
	// 		Iterator<String> objectKeys = object.keys();
	// 		for (int index = 0; index<object.length(); index++)
	// 		{
	// 			// iterate through every key-value pair of the json object, write it to the file writer
	// 			String key = objectKeys.next();
	// 			Object value = object.get(key);
	// 			writer.object().key(key).value(value).endObject();
	// 		}

	// 		// officially write everything to the file
	// 		fileWriter.close();
	// 	} catch (IOException e) {
	// 		e.printStackTrace();
	// 	}
	// }

	// public void combineJsonToFile(File configFile, JSONObject jsonObject)
	// {
	// 	try {
	// 		JSONTokener fileTokener = new JSONTokener(new FileReader(configFile));
	// 		JSONWriter writer = new JSONWriter(new FileWriter(configFile));
	// 		Iterator<String> keyIterator = jsonObject.keys();
	// 		for (int index = 0; index<jsonObject.length(); index++)
	// 		{
	// 			writer.
	// 		}

	// 	} catch (Exception e) {
	// 		e.printStackTrace();
	// 	}
		
	// }

	public JSONObject readAllJSONFromFile(File configFile)
	{
		try {
			return new JSONObject(new JSONTokener(new FileInputStream(configFile)));

		} catch (Exception e) {
			// return blank if nothing found
			return new JSONObject();
		}
	}

	public Object getValueFromFile(File configFile, String key)
	{
		try {
			JSONObject obj = new JSONObject(new JSONTokener(new FileInputStream(configFile)));
			return obj.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			// if the file wasn't found, it'll end up here
			return JSONObject.NULL;
		}
	}

	// I'm not going to allow reading the whole file to a string because that's horribly memory inefficient.
	// I'll only do filestreams.
	// public static Object getJsonValueFromFile(File configFile, String key)
	// {
	// 	try {
	// 		JSONTokener tokener = new JSONTokener(new FileReader(configFile));
	// 		// if the tokener has more stuff
	// 		while (tokener.more())
	// 		{
	// 			JSONObject currentObj = (JSONObject) tokener.nextValue();
	// 			if (!currentObj.isNull(key))
	// 			{
	// 				return currentObj.get(key);
	// 			}
	// 		}
	// 	} catch (Exception e) {
	// 		e.printStackTrace();
	// 		return new JSONObject();
	// 	}
	// 	// if the file wasn't found, it'll end up here
	// 	return new JSONObject();
	// }

	// protected abstract String getInitialData(String filename);

	protected File getConfigurationFile(String configFilename)
	{
		return new File(instancePath + "/" + configFilename);
	}

	public List<File> getConfigurationFiles()
	{
		ArrayList<File> files = new ArrayList<>();
		for (String configFilename : instanceDataPathNames)
		{
			files.add(getConfigurationFile(configFilename));
		}
		return files;
	}


	public String getInstancePath()
	{
		return instancePath;
	}

	public static String getModConfigDir() {
		return MOD_CONFIG_DIR;
	}
}
