package com.theswirlingvoid.polarmachinery.block.block.thermalpipe;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.util.math.BlockPos;

public class PipeNetwork {
	private Set<BlockPos> pipeBlocks;
	private Set<BlockPos> connectionEndings;


	public PipeNetwork() {
		this.pipeBlocks = new HashSet<>();
		this.connectionEndings = new HashSet<>();
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
		/**
		 * TODO: IMPLEMENT
		 * CHECK IF THE BLOCK POSITIONS FOR EACH CONNECTION ENDING ARE HEAT/COLD SOURCES,
		 * AND SUM UP THEIR HEAT VALUE IF SO. IF THEY'RE NOT HEAT/COLD SOURCES, MAKE ITS HEAT VALUE NEUTRAL, THAT BEING 0
		 **/
		return 0;
	}

	public float performHeatEquation()
	{
		//TODO MAKE EQUATION
		return 0;
	}
}
