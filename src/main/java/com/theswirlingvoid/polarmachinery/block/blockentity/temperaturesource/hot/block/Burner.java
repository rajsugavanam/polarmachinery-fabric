package com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.hot.block;

import com.theswirlingvoid.polarmachinery.Main;
import com.theswirlingvoid.polarmachinery.block.ModBlocks;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.blocksandbentities.generic.PipeConnectionEndBlockWithEntity;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.hot.blockentity.BurnerBlockEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.List;

public class Burner extends PipeConnectionEndBlockWithEntity {

	private static final String ID = "burner";

	public Burner() {
		super(getSettings());
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new BurnerBlockEntity(pos, state);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	public static Settings getSettings()
	{
		return FabricBlockSettings.of(Material.METAL)
				.requiresTool()
				.strength(2.0f, 6.0f)
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
