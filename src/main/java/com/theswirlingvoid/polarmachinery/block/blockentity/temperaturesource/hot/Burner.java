package com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.hot;

import com.theswirlingvoid.polarmachinery.Main;
import com.theswirlingvoid.polarmachinery.block.ModBlocks;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.enums.TemperatureType;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.machine.GeneratingMachine;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.BlockView;

import java.util.List;

public class Burner extends GeneratingMachine {

	private static final float MAX_TEMP = 100f;
	private static final TemperatureType TEMPERATURE_TYPE = TemperatureType.HOT;

	private static final String ID = "burner";

	public Burner() {
		super(getSettings(), TEMPERATURE_TYPE, MAX_TEMP);
	}

	public static Settings getSettings()
	{
		return FabricBlockSettings.of(Material.METAL)
				.requiresTool()
				.strength(1.0f, 6.0f)
				.sounds(BlockSoundGroup.METAL);
	}

	public static FabricItemSettings getItemSettings()
	{
		FabricItemSettings settings = new FabricItemSettings()
				.group(Main.IGROUP);

		return settings;
	}

	@Override
	public void appendTooltip(ItemStack stack, BlockView world, List<Text> tooltip, TooltipContext options) {
		tooltip.add(Text.translatable("tooltip." + ID + ".desc").formatted(Formatting.GRAY).formatted(Formatting.ITALIC));
		super.appendTooltip(stack, world, tooltip, options);
	}

	public static Identifier getIdentifier()
	{
		return new Identifier(Main.MOD_ID, ID);
	}

	public static BlockItem getBlockItem()
	{
		return new BlockItem(ModBlocks.BURNER, new FabricItemSettings());
	}
}
