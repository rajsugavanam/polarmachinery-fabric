package com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.machine;

import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.enums.TemperatureType;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.context.OperationStrengthContext;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.context.TemperatureStorage;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.temperatureinterface.ITemperatureProvider;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.temperatureinterface.ITemperatureReceiver;

public class GenericInterfacingMachine implements ITemperatureProvider, ITemperatureReceiver {

	private TemperatureStorage temperatureStorage;
	private OperationStrengthContext strengthContext;

	@Override
	public float provideTemperature() {
		return 0;
	}

	@Override
	public TemperatureType getTemperatureType() {
		return null;
	}

	@Override
	public TemperatureStorage getTemperatureStorage() {
		return temperatureStorage;
	}

	@Override
	public OperationStrengthContext getOperationStrengthContext() {
		return strengthContext;
	}

	@Override
	public void receiveTemperature(float temperature) {
		// TODO Auto-generated method stub
	}
	
}
