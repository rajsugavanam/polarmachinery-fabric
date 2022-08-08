package com.theswirlingvoid.polarmachinery.block.block.thermalpipe;


import java.util.List;

import com.theswirlingvoid.polarmachinery.Main;
import com.theswirlingvoid.polarmachinery.block.ModBlocks;

import com.theswirlingvoid.polarmachinery.block.block.thermalpipe.thermalpiperegion.ThermalPipeRegionManager;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class ThermalPipe extends Block {

	public static final String ID = "thermal_pipe";

	public ThermalPipe() {
		super(getSettings());
	}
	
	public static Settings getSettings()
	{
		Settings settings = FabricBlockSettings.of(Material.METAL)
			.requiresTool()
			.strength(1.0f, 6.0f)
			.sounds(BlockSoundGroup.METAL);
		return settings;
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
		return new BlockItem(ModBlocks.THERMAL_PIPE, new FabricItemSettings());
	}

	@Override
	public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {

		deleteLonelyPipe(world, pos, world.getBlockState(pos).getBlock());
		super.onBreak(world, pos, state, player);
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {

		super.onPlaced(world, pos, state, placer, itemStack);
	}

	@Override
	public void neighborUpdate(BlockState state, World world, BlockPos originalPos, Block sourceBlock, BlockPos sourcePos,
			boolean notify) {
				

		if (!world.isClient) 
		{


			ThermalPipeLocator locator = new ThermalPipeLocator(world, sourcePos, sourceBlock);
			List<PipeNetwork> foundNetworks = locator.findPipeNetworks();
			ThermalPipeRegionManager regionManager = new ThermalPipeRegionManager(world);

			// deletes every possible old network
			for (BlockPos possibleNetworkPos : locator.getImmediatePipeBlocks())
			{
				regionManager.deleteRegionsIncludingPipe(possibleNetworkPos);
			}

			for (PipeNetwork network : foundNetworks)
			{
				regionManager.saveNetwork(network);
			}

		}

		super.neighborUpdate(state, world, originalPos, sourceBlock, sourcePos, notify);
	}

	private void deleteLonelyPipe(World world, BlockPos pos, Block sourceBlock)
	{
		if (!world.isClient)
		{
			ThermalPipeLocator locator = new ThermalPipeLocator(world, pos, sourceBlock);
			// only a single pipe was involved
			if (locator.getImmediatePipeBlocks().size() == 0)
			{
				ThermalPipeRegionManager regionManager = new ThermalPipeRegionManager(world);
				regionManager.deleteRegionsIncludingPipe(pos);
			}
		}
	}

//	private void addLonelyPipe(World world, BlockPos pos, Block sourceBlock)
//	{
//		ThermalPipeLocator locator = new ThermalPipeLocator(world, pos, sourceBlock);
//		// only a single pipe was involved
//		if (locator.getImmediateValidBlocks().size() == 0)
//		{
//			ThermalPipeRegionManager regionManager = new ThermalPipeRegionManager(world);
//			PipeNetwork newNetwork = new PipeNetwork(world);
//			newNetwork.addPipeBlock(pos);
//			regionManager.saveNetwork(newNetwork);
//		}
//	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
}
