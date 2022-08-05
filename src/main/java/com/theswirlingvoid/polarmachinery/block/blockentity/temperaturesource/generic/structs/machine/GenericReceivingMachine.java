package com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.machine;

import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.enums.TemperatureType;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.context.OperationStrengthContext;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.context.TemperatureStorage;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.temperatureinterface.ITemperatureReceiver;

public class GenericReceivingMachine implements ITemperatureReceiver {

	@Override
	public TemperatureStorage getTemperatureStorage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TemperatureType getTemperatureType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void receiveTemperature(float temperature) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public OperationStrengthContext getOperationStrengthContext() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
