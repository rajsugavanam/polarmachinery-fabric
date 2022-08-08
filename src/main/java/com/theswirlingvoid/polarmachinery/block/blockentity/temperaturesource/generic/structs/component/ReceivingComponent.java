package com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.component;

import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.enums.TemperatureType;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.context.OperationStrengthContext;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.context.TemperatureStorage;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.temperatureinterface.ITemperatureReceiver;

public class ReceivingComponent extends PolarStorageComponent implements ITemperatureReceiver {

	public ReceivingComponent(TemperatureType type, float maxTemperature) {
		super(type, maxTemperature);
	}

	@Override
	public TemperatureStorage getTemperatureStorage() {

		return null;
	}

	@Override
	public TemperatureType getTemperatureType() {
		return super.getTempType();
	}

	@Override
	public void receiveTemperature(float temperature) {
		super.getTempStorage().setCurrentTemperature(temperature);
	}

	@Override
	public OperationStrengthContext getOperationStrengthContext() {
		return super.getStrengthContext();
	}
	
}
