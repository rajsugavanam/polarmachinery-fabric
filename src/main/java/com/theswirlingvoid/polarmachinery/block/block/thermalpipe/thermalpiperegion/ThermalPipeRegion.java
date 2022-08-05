package com.theswirlingvoid.polarmachinery.block.block.thermalpipe.thermalpiperegion;

import java.util.Set;

public class ThermalPipeRegion {
	private Set<Long> region;
	// private HashMap<BlockPos, Boolean> pipeEnds;
	
	public ThermalPipeRegion(Set<Long> region) {
		this.region = region;
	}

	
	public Set<Long> getRegion() {
		return region;
	}

	public void setRegion(Set<Long> region) {
		this.region = region;
	}


	// public HashMap<BlockPos, Boolean> getPipeEnds() {
	// 	return pipeEnds;
	// }
	// public void setPipeEnds(HashMap<BlockPos, Boolean> pipeEnds) {
	// 	this.pipeEnds = pipeEnds;
	// }

}
