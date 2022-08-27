package com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.component;

import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.enums.TemperatureType;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.context.OperationStrengthContext;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.context.TemperatureStorage;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.temperatureinterface.ITemperatureStorage;

public class PolarStorageComponent implements ITemperatureStorage {
	private final TemperatureType tempType;
	private final TemperatureStorage tempStorage;

	public PolarStorageComponent(TemperatureType type, float maxTemperature)
	{
		this.tempType = type;
		this.tempStorage = new TemperatureStorage(0, maxTemperature);
	}

	@Override
	public TemperatureType getTemperatureType() {
		return tempType;
	}

	@Override
	public TemperatureStorage getTemperatureStorage() {
		return tempStorage;
	}
}
