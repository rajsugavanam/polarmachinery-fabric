package com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.context;

public class TemperatureStorage {
	private float currentTemperature;
	private float maxTemperature;

	
	public TemperatureStorage(float currentTemperature, float maxTemperature) {
		this.currentTemperature = currentTemperature;
		this.maxTemperature = maxTemperature;
	}


	public float getCurrentTemperature() {
		return currentTemperature;
	}
	public void setCurrentTemperature(float currentTemperature) {
		this.currentTemperature = currentTemperature;
	}
	public float getMaxTemperature() {
		return maxTemperature;
	}
	public void setMaxTemperature(float maxTemperature) {
		this.maxTemperature = maxTemperature;
	}
	
}
