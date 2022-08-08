package com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.component;

import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.enums.TemperatureType;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.context.OperationStrengthContext;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.context.TemperatureStorage;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.machine.MachineEquations;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.temperatureinterface.ITemperatureProvider;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.temperatureinterface.ITemperatureReceiver;

public class InterfacingComponent extends PolarStorageComponent implements ITemperatureProvider, ITemperatureReceiver {


	public InterfacingComponent(TemperatureType type, float maxTemperature) {
		super(type, maxTemperature);
	}

	@Override
	public float provideTemperature(int pipes) {
		float tempToProvide = MachineEquations.tempProvideEquation(
				super.getTempStorage().getCurrentTemperature(),
				super.getStrengthContext().getOperationStrength()
		);
		// subtract the provided temp from the storage
		super.getTempStorage().setCurrentTemperature(
				this.getTempStorage().getCurrentTemperature()-tempToProvide
		);

		return tempToProvide;
	}

	@Override
	public TemperatureType getTemperatureType() {
		return super.getTempType();
	}

	@Override
	public TemperatureStorage getTemperatureStorage() {
		return super.getTempStorage();
	}

	@Override
	public OperationStrengthContext getOperationStrengthContext() {
		return super.getStrengthContext();
	}

	@Override
	public void receiveTemperature(float temperature) {
		super.getTempStorage().setCurrentTemperature(temperature);
	}
}
