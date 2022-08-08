package com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.component;

import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.enums.TemperatureType;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.context.OperationStrengthContext;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.context.TemperatureStorage;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.temperatureinterface.ITemperatureProvider;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.temperatureinterface.ITemperatureReceiver;

public class InterfacingComponent extends PolarizedMachine implements ITemperatureProvider, ITemperatureReceiver {


	public InterfacingComponent(TemperatureType type, float maxTemperature) {
		super(type, maxTemperature);
	}

	@Override
	public float provideTemperature() {
		return 0;
	}

	@Override
	public TemperatureType getTemperatureType() {
		return temperatureType;
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
