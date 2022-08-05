package com.theswirlingvoid.polarmachinery.item.functionalcrystals;

import java.util.List;

import com.theswirlingvoid.polarmachinery.Main;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class TransformationCrystal extends FunctionalCrystal {

	public static final String ID = "transformation_crystal";

	public TransformationCrystal(Settings settings) {
		super(getSettings());
	}

	public static FabricItemSettings getSettings()
	{
		return new FabricItemSettings();
	}

	public static Identifier getIdentifier()
	{
		return new Identifier(Main.MOD_ID, ID);
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip,
			net.minecraft.client.item.TooltipContext context) {

		tooltip.add(Text.translatable("tooltip." + ID + ".desc").formatted(Formatting.GRAY).formatted(Formatting.ITALIC));

		super.appendTooltip(stack, world, tooltip, context);
	}
	
}
