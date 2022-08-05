package com.theswirlingvoid.polarmachinery.item.items;

import com.theswirlingvoid.polarmachinery.item.functionalcrystals.TransformationCrystal;
import com.theswirlingvoid.polarmachinery.item.utility.applicator.Applicator;

import net.minecraft.item.Item.Settings;
import net.minecraft.util.registry.Registry;

public class ModItems {
	// applicator
	public static final Applicator APPLICATOR = new Applicator();

	// crystals
	public static final TransformationCrystal FORMATION_CRYSTAL = new TransformationCrystal(new Settings());

	public static void registerItems()
	{
		Registry.register(Registry.ITEM, Applicator.getIdentifier(), APPLICATOR);
		Registry.register(Registry.ITEM, TransformationCrystal.getIdentifier(), FORMATION_CRYSTAL);
	}
}
