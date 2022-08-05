package com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.temperatureinterface;

import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.context.TemperatureStorage;

public interface ITemperatureStorage extends ITemperaturePolarized {

	public TemperatureStorage getTemperatureStorage();

}
