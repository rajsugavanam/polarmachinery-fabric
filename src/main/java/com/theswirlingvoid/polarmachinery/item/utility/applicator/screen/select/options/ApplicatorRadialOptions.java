package com.theswirlingvoid.polarmachinery.item.utility.applicator.screen.select.options;

public class ApplicatorRadialOptions {
	private float innerRadiusRatio;
	private float outerRadiusRatio;
	private float offsetRadiusRatio;
	private float degResolution;
	private float angleShift = 270;
	private int arcs;


	public ApplicatorRadialOptions(float innerRadiusRatio, float outerRadiusRatio, float offsetRadiusRatio, float degResolution,
			int arcs) {
		this.innerRadiusRatio = innerRadiusRatio;
		this.outerRadiusRatio = outerRadiusRatio;
		this.offsetRadiusRatio = offsetRadiusRatio;
		this.degResolution = degResolution;
		this.arcs = arcs;
	}


	public float getAngleShift() {
		return angleShift;
	}

	public void setAngleShift(float angleShift) {
		this.angleShift = angleShift;
	}
	
	public float getInnerRadiusRatio() {
		return innerRadiusRatio;
	}

	public void setInnerRadiusRatio(float innerRadiusRatio) {
		this.innerRadiusRatio = innerRadiusRatio;
	}

	public float getOuterRadiusRatio() {
		return outerRadiusRatio;
	}

	public void setOuterRadiusRatio(float outerRadiusRatio) {
		this.outerRadiusRatio = outerRadiusRatio;
	}

	public float getOffsetRadiusRatio() {
		return offsetRadiusRatio;
	}

	public void setOffsetRadiusRatio(float offsetRadiusRatio) {
		this.offsetRadiusRatio = offsetRadiusRatio;
	}

	public float getDegResolution() {
		return degResolution;
	}

	public void setDegResolution(float degResolution) {
		this.degResolution = degResolution;
	}

	public int getArcs() {
		return arcs;
	}

	public void setArcs(int arcs) {
		this.arcs = arcs;
	}
	
}
