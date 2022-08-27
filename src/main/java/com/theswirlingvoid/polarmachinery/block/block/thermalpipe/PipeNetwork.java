package com.theswirlingvoid.polarmachinery.block.block.thermalpipe;

import java.util.HashSet;
import java.util.Set;

import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.component.PolarStorageComponent;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.machine.IPipeMachine;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONPropertyName;

public class PipeNetwork {
	private Set<BlockPos> pipeBlocks;
	private Set<BlockPos> connectionEndings;
	private final World networkWorld;

	private static final float DROPOFF_CLOSENESS = 1.18f;
	private static final float DROPOFF_SLOWNESS = 30f;

	public static final String PIPE_BLOCKS_KEY = "PipeBlocks";
	public static final String CONNECTION_ENDINGS_KEY = "ConnectionEndings";
	public static final String TOTAL_PIPES_KEY = "TotalPipes";
	public static final String TOTAL_HEAT_KEY = "TotalHeat";
	public static final String NETWORK_WORLD_KEY = "NetworkWorld";

	public PipeNetwork(World world) {
		this.pipeBlocks = new HashSet<>();
		this.connectionEndings = new HashSet<>();
		this.networkWorld = world;
	}

	public void removePipeBlock(BlockPos pos)
	{
		this.pipeBlocks.remove(pos);
	}

	public World getNetworkWorld() {
		return networkWorld;
	}

	public Set<BlockPos> getPipeBlocks() {
		return pipeBlocks;
	}
	public void setPipeBlocks(Set<BlockPos> foundBlocks) {
		this.pipeBlocks = foundBlocks;
	}
	public void addPipeBlock(BlockPos blockPos) {
		this.pipeBlocks.add(blockPos);
	}

	public Set<BlockPos> getConnectionEndings() {
		return connectionEndings;
	}
	public void setConnectionEndings(Set<BlockPos> connectionEndings) {
		this.connectionEndings = connectionEndings;
	}
	public void addConnectionEnding(BlockPos blockPos) {
		this.connectionEndings.add(blockPos);
	}

	public int getTotalNumPipes()
	{
		return this.pipeBlocks.size();
	}

	/* ------------------------------- HEAT STUFF ------------------------------- */
	public float getTotalHeat()
	{
		float totalHeat = 0;
		for (BlockPos pos : connectionEndings)
		{
			if (networkWorld.getBlockEntity(pos) instanceof IPipeMachine pipeMachine)
			{
				PolarStorageComponent storageComponent = pipeMachine.getStorageComponent();
				if (storageComponent != null)
					totalHeat = totalHeat+storageComponent.getTemperatureStorage().getCurrentTemperature();
			}
		}
		return totalHeat;
	}

	public static float performHeatEquation(float temp, int pipes)
	{
		// here's the equation on desmos if you're comfortable with a little calculus!
		// d is dropoff closeness, s is dropoff slowness
		// https://www.desmos.com/calculator/4nahvrgcwy
		return (float) (
				temp-((temp/DROPOFF_CLOSENESS) *Math.tanh(pipes/DROPOFF_SLOWNESS))
		);
	}

	public JSONObject toJSON()
	{
		JSONObject obj = new JSONObject();

		// just so that single blocks are still arrays
		obj.put(PIPE_BLOCKS_KEY, new JSONArray());
		obj.put(CONNECTION_ENDINGS_KEY, new JSONArray());

		getPipeBlocks().forEach((bp) -> obj.accumulate(PIPE_BLOCKS_KEY, bp.asLong()));
		getConnectionEndings().forEach((ce) -> obj.accumulate(CONNECTION_ENDINGS_KEY, ce.asLong()));

		obj
			.put(TOTAL_PIPES_KEY, getTotalNumPipes())
			.put(TOTAL_HEAT_KEY, getTotalHeat());

		return obj;
	}
}
