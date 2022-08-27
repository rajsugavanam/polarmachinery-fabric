package com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.component;

import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.enums.TemperatureType;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.context.OperationStrengthContext;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.temperatureinterface.IOperationStrengthContext;

public class StrengthComponent extends PolarStorageComponent implements IOperationStrengthContext {

	private final OperationStrengthContext strengthContext = new OperationStrengthContext();

	public StrengthComponent(TemperatureType type, float maxTemperature) {
		super(type, maxTemperature);
	}

	@Override
	public OperationStrengthContext getOperationStrengthContext() {
		return strengthContext;
	}
}
