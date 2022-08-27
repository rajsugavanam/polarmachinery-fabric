package com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.component;

import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.enums.TemperatureType;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.context.OperationStrengthContext;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.context.TemperatureStorage;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.machine.MachineEquations;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.temperatureinterface.ITemperatureProvider;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.temperatureinterface.ITemperatureReceiver;

public class InterfacingComponent extends StrengthComponent implements ITemperatureProvider, ITemperatureReceiver {


	public InterfacingComponent(TemperatureType type, float maxTemperature) {
		super(type, maxTemperature);
	}

	@Override
	public float provideTemperature(int pipes) {
		float tempToProvide = MachineEquations.tempProvideEquation(
				super.getTemperatureStorage().getCurrentTemperature(),
				super.getOperationStrengthContext().getOperationStrength()
		);
		// subtract the provided temp from the storage
		super.getTemperatureStorage().setCurrentTemperature(
				this.getTemperatureStorage().getCurrentTemperature()-tempToProvide
		);

		return tempToProvide;
	}

	@Override
	public void receiveTemperature(float temperature) {
		super.getTemperatureStorage().setCurrentTemperature(temperature);
	}
}
