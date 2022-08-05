package com.theswirlingvoid.polarmachinery.item.utility.applicator.screen.select.structs.arc;

public class RadialArcAngularData {
	private float degStart;
	private float degEnd;
	private float degResolution;

	
	public RadialArcAngularData(float degResolution, float degStart, float degEnd) {
		this.degResolution = degResolution;
		this.degStart = degStart;
		this.degEnd = degEnd;
	}
	
	public float getDegResolution() {
		return degResolution;
	}

	public void setDegResolution(float degResolution) {
		this.degResolution = degResolution;
	}

	public float getDegStart() {
		return degStart;
	}
	public void setDegStart(float degStart) {
		this.degStart = degStart;
	}
	public float getDegEnd() {
		return degEnd;
	}
	public void setDegEnd(float degEnd) {
		this.degEnd = degEnd;
	}
}
