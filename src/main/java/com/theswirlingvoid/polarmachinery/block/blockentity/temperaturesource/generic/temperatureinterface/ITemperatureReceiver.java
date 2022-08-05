package com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.temperatureinterface;

public interface ITemperatureReceiver extends ITemperatureStorage, IOperationStrengthContext {
	
	public void receiveTemperature(float temperature);

}
