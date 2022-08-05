package com.theswirlingvoid.polarmachinery.item.utility.applicator;

import com.theswirlingvoid.polarmachinery.item.functionalcrystals.FunctionalCrystal;

import net.minecraft.item.Item;

public class ApplicatorValidItems {

	public static boolean isValidItem(Item item)
	{
		return (
			item instanceof FunctionalCrystal
		);
	}
}
