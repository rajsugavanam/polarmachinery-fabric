package com.theswirlingvoid.polarmachinery.item.utility.applicator.screen.select.options;

import com.theswirlingvoid.polarmachinery.generic.color.AlphaRGB;

public class ColorOptions {
	private AlphaRGB unhighlightedColor;
	private AlphaRGB highlightedColor;
	
	public ColorOptions(AlphaRGB unhighlightedColor, AlphaRGB highlightedColor) {
		this.unhighlightedColor = unhighlightedColor;
		this.highlightedColor = highlightedColor;
	}

	public AlphaRGB getAppropriateColorCopy(boolean selected)
	{
		AlphaRGB color;
		if (selected)
		{
			highlightedColor = getHighlightedColor();
			color = new AlphaRGB(highlightedColor.getRed(), highlightedColor.getBlue(), highlightedColor.getGreen(), highlightedColor.getAlpha());
		}
		else
		{
			unhighlightedColor = getUnhighlightedColor();
			color = new AlphaRGB(unhighlightedColor.getRed(), unhighlightedColor.getBlue(), unhighlightedColor.getGreen(), unhighlightedColor.getAlpha());
		}
		return color;
	}

	public AlphaRGB linearMix(float percentageColor2)
	{
		// bound the color to prevent value errors
		percentageColor2 = Math.max(Math.min(1,percentageColor2),0);

		float percentageColor1 = 1-percentageColor2;

		float newRed = unhighlightedColor.getRed()*percentageColor1+highlightedColor.getRed()*percentageColor2;
		float newBlue = unhighlightedColor.getBlue()*percentageColor1+highlightedColor.getBlue()*percentageColor2;
		float newGreen = unhighlightedColor.getGreen()*percentageColor1+highlightedColor.getGreen()*percentageColor2;
		float newAlpha = unhighlightedColor.getAlpha()*percentageColor1+highlightedColor.getAlpha()*percentageColor2;

		return new AlphaRGB(newRed, newBlue, newGreen, newAlpha);
	}

	public AlphaRGB getUnhighlightedColor() {
		return unhighlightedColor;
	}
	public void setUnhighlightedColor(AlphaRGB unhighlightedColor) {
		this.unhighlightedColor = unhighlightedColor;
	}
	public AlphaRGB getHighlightedColor() {
		return highlightedColor;
	}
	public void setHighlightedColor(AlphaRGB highlightedColor) {
		this.highlightedColor = highlightedColor;
	}
}
