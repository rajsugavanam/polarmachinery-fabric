package com.theswirlingvoid.polarmachinery.item.functionalcrystals;

import com.theswirlingvoid.polarmachinery.Main;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

public abstract class FunctionalCrystal extends Item {

	public FunctionalCrystal(Settings settings) {
		super(getSettings());
	}

	public static FabricItemSettings getSettings()
	{
		return new FabricItemSettings()
		.maxCount(1)
		.group(Main.IGROUP);
	}
}
