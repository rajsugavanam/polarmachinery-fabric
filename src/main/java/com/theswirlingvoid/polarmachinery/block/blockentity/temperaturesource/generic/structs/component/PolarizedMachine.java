package com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.component;

import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.enums.TemperatureType;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.context.OperationStrengthContext;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.context.TemperatureStorage;

public abstract class PolarizedMachine {
	protected TemperatureType temperatureType;
	protected TemperatureStorage temperatureStorage;
	protected OperationStrengthContext strengthContext = new OperationStrengthContext();

	public PolarizedMachine(TemperatureType type, float maxTemperature)
	{
		this.temperatureType = type;
		this.temperatureStorage = new TemperatureStorage(0, maxTemperature);
	}
}
