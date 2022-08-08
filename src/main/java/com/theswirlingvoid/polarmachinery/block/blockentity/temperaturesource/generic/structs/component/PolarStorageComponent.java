package com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.component;

import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.enums.TemperatureType;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.context.OperationStrengthContext;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.context.TemperatureStorage;

public abstract class PolarStorageComponent {
	private final TemperatureType tempType;
	private final TemperatureStorage tempStorage;
	private final OperationStrengthContext strengthContext = new OperationStrengthContext();

	public PolarStorageComponent(TemperatureType type, float maxTemperature)
	{
		this.tempType = type;
		this.tempStorage = new TemperatureStorage(0, maxTemperature);
	}

	public TemperatureType getTempType() {
		return tempType;
	}

	public TemperatureStorage getTempStorage() {
		return tempStorage;
	}

	public OperationStrengthContext getStrengthContext() {
		return strengthContext;
	}
}
