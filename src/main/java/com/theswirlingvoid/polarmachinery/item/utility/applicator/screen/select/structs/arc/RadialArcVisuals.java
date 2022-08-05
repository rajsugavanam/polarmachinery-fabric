package com.theswirlingvoid.polarmachinery.item.utility.applicator.screen.select.structs.arc;

import com.theswirlingvoid.polarmachinery.item.utility.applicator.screen.select.animation.RadialAnimationOptions;
import com.theswirlingvoid.polarmachinery.item.utility.applicator.screen.select.options.ColorOptions;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

public class RadialArcVisuals {
	private ItemStack displayedItem;
	private ColorOptions colorOptions;
	private RadialAnimationOptions animationOptions;
	private MatrixStack matrixStack;
	
	public RadialArcVisuals(ItemStack displayedItem, ColorOptions colorOptions,
			RadialAnimationOptions animationOptions, MatrixStack matrixStack) {
		this.displayedItem = displayedItem;
		this.colorOptions = colorOptions;
		this.animationOptions = animationOptions;
		this.matrixStack = matrixStack;
	}
	
	public ItemStack getDisplayedItem() {
		return displayedItem;
	}
	public void setDisplayedItem(ItemStack displayedItem) {
		this.displayedItem = displayedItem;
	}
	public ColorOptions getColorOptions() {
		return colorOptions;
	}
	public void setColorOptions(ColorOptions colorOptions) {
		this.colorOptions = colorOptions;
	}
	public RadialAnimationOptions getAnimationOptions() {
		return animationOptions;
	}
	public void setAnimationOptions(RadialAnimationOptions animationOptions) {
		this.animationOptions = animationOptions;
	}
	public MatrixStack getMatrixStack() {
		return matrixStack;
	}
	public void setMatrixStack(MatrixStack matrixStack) {
		this.matrixStack = matrixStack;
	}
}
