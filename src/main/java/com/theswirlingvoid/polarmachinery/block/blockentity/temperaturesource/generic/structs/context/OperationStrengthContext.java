package com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.context;

public class OperationStrengthContext {
	
	private float operationsPerSecond = 1;
	private float operationStrength = 1;

	public float getOperationsPerSecond() {
		return operationsPerSecond;
	}
	public void setOperationsPerSecond(float operationsPerSecond) {
		this.operationsPerSecond = operationsPerSecond;
	}
	public float getOperationStrength() {
		return operationStrength;
	}
	public void setOperationStrength(float operationStrength) {
		this.operationStrength = operationStrength;
	}
}
