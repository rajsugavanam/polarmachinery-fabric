package com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.machine;

import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.enums.TemperatureType;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.blocksandbentities.generic.PipeConnectionEndBlock;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.component.GeneratingComponent;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.component.InterfacingComponent;

public abstract class InterfacingMachine extends PipeConnectionEndBlock {

	InterfacingComponent interfacingComponent;

	public InterfacingMachine(Settings settings, TemperatureType tempType, float maxTemperature) {
		super(settings);
		interfacingComponent = new InterfacingComponent(tempType, maxTemperature);
	}
}
