package com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.temperatureinterface;

public interface ITemperatureProvider extends ITemperatureStorage, IOperationStrengthContext {

	public float provideTemperature();
	
}
