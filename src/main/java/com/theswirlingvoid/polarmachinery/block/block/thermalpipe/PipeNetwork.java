package com.theswirlingvoid.polarmachinery.block.block.thermalpipe;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.util.math.BlockPos;
import org.json.JSONPropertyName;

public class PipeNetwork {
	private Set<BlockPos> pipeBlocks;
	private Set<BlockPos> connectionEndings;

	private static final float DROPOFF_CLOSENESS = 1.18f;
	private static final float DROPOFF_SLOWNESS = 30f;

	public PipeNetwork() {
		this.pipeBlocks = new HashSet<>();
		this.connectionEndings = new HashSet<>();
	}


	@JSONPropertyName("PipeBlocks")
	public Set<BlockPos> getPipeBlocks() {
		return pipeBlocks;
	}
	public void setPipeBlocks(Set<BlockPos> foundBlocks) {
		this.pipeBlocks = foundBlocks;
	}
	public void addPipeBlock(BlockPos blockPos) {
		this.pipeBlocks.add(blockPos);
	}

	@JSONPropertyName("ConnectionEndings")
	public Set<BlockPos> getConnectionEndings() {
		return connectionEndings;
	}
	public void setConnectionEndings(Set<BlockPos> connectionEndings) {
		this.connectionEndings = connectionEndings;
	}
	public void addConnectionEnding(BlockPos blockPos) {
		this.connectionEndings.add(blockPos);
	}

	@JSONPropertyName("TotalPipes")
	public int getTotalNumPipes()
	{
		return this.pipeBlocks.size();
	}

	/* ------------------------------- HEAT STUFF ------------------------------- */
	@JSONPropertyName("TotalHeat")
	public float getTotalHeat()
	{
		/**
		 * TODO: IMPLEMENT
		 * CHECK IF THE BLOCK POSITIONS FOR EACH CONNECTION ENDING ARE HEAT/COLD SOURCES,
		 * AND SUM UP THEIR HEAT VALUE IF SO. IF THEY'RE NOT HEAT/COLD SOURCES, MAKE ITS HEAT VALUE NEUTRAL, THAT BEING 0
		 **/
		return 0;
	}

	public float performHeatEquation(float temp, int pipes)
	{
		//here's the equation on desmos if you're comfortable with a little calculus!
		// d is dropoff closeness, s is dropoff slowness
		//https://www.desmos.com/calculator/e8o7qvjktz
		return (float) (temp-(
						Math.pow( temp, 1/(Math.pow(DROPOFF_CLOSENESS, 1/5f)) )
						*Math.tanh(pipes/DROPOFF_SLOWNESS)
				));
	}
}
