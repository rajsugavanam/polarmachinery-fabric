package com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.machine;

public class MachineEquations {

	public static float tempProvideEquation(float temp, float strength)
	{
		// provide a percentage of temperature
		return temp*(strength/100f);
	}
}
