package com.theswirlingvoid.polarmachinery.item.utility.applicator;

import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;

public class ApplicatorHelper {
	
	public static Formatting getRarityColor(Rarity rarity)
	{
		switch (rarity)
		{
			case UNCOMMON:
				return Formatting.YELLOW;
			
			case RARE:
				return Formatting.AQUA;
			
			case EPIC:
				return Formatting.LIGHT_PURPLE;

			default:
				return Formatting.WHITE;
		}
	}
}
