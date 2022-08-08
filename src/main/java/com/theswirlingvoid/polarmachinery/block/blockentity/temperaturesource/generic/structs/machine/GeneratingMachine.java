package com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.machine;

import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.enums.TemperatureType;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.blocksandbentities.generic.PipeConnectionEndBlock;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.component.GeneratingComponent;

public abstract class GeneratingMachine extends PipeConnectionEndBlock {

	GeneratingComponent generatingComponent;

	public GeneratingMachine(Settings settings, TemperatureType tempType, float maxTemperature) {
		super(settings);
		generatingComponent = new GeneratingComponent(tempType, maxTemperature);
	}
}
