package com.theswirlingvoid.polarmachinery.item.utility.applicator.screen.select.animation;

public class RadialAnimationOptions {
	private float totalSecs = 0;
	private float secsToRenderNextArc = 0.02f;
	private float secsFadeIn = 0.02f;
	private float snapshotDelta = 0;
	

	public RadialAnimationOptions(float secsToRenderNextArc, float secsFadeIn) {
		this.secsToRenderNextArc = secsToRenderNextArc;
		this.secsFadeIn = secsFadeIn;
	}
	

	public float getSnapshotDelta() {
		return snapshotDelta;
	}

	public void setSnapshotDelta(float snapshotDelta) {
		this.snapshotDelta = snapshotDelta;
	}


	public float getTotalSecs() {
		return totalSecs;
	}

	public void setTotalSecs(float totalSecs) {
		this.totalSecs = totalSecs;
	}

	public float getSecsToRenderNextArc() {
		return secsToRenderNextArc;
	}

	public void setSecsToRenderNextArc(float secsToRenderNextArc) {
		this.secsToRenderNextArc = secsToRenderNextArc;
	}

	public float getSecsFadeIn() {
		return secsFadeIn;
	}

	public void setSecsFadeIn(float secsFadeIn) {
		this.secsFadeIn = secsFadeIn;
	}
}
