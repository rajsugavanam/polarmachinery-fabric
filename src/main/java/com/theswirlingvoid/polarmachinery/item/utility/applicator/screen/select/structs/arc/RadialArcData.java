package com.theswirlingvoid.polarmachinery.item.utility.applicator.screen.select.structs.arc;

public class RadialArcData {
	private float innerRadius;
	private float outerRadius;
	private float pushoutRadius;
	private int index;
	private boolean selected;

	public RadialArcData(float innerRadius, float outerRadius, float pushoutRadius, int index, boolean selected) {
		this.innerRadius = innerRadius;
		this.outerRadius = outerRadius;
		this.pushoutRadius = pushoutRadius;
		this.index = index;
		this.selected = selected;
	}

	public float getInnerRadius() {
		return innerRadius;
	}

	public float getOffsetInnerRadius() {
		return innerRadius+pushoutRadius;
	}

	public void setInnerRadius(float innerRadius) {
		this.innerRadius = innerRadius;
	}

	public float getOuterRadius() {
		return outerRadius;
	}

	public float getOffsetOuterRadius() {
		return outerRadius+pushoutRadius;
	}

	public void setOuterRadius(float outerRadius) {
		this.outerRadius = outerRadius;
	}
	public float getPushoutRadius() {
		return pushoutRadius;
	}
	public void setPushoutRadius(float pushoutRadius) {
		this.pushoutRadius = pushoutRadius;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
