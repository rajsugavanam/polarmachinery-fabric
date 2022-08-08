package com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.machine;

import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.enums.TemperatureType;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.blocksandbentities.generic.PipeConnectionEndBlock;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.component.InterfacingComponent;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.component.ReceivingComponent;

public abstract class ReceivingMachine extends PipeConnectionEndBlock {

	ReceivingComponent receivingComponent;

	public ReceivingMachine(Settings settings, TemperatureType tempType, float maxTemperature) {
		super(settings);
		receivingComponent = new ReceivingComponent(tempType, maxTemperature);
	}
}
