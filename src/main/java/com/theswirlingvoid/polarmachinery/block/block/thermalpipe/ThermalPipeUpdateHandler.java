package com.theswirlingvoid.polarmachinery.block.block.thermalpipe;

import com.theswirlingvoid.polarmachinery.block.block.thermalpipe.thermalpiperegion.ThermalPipeRegionManager;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ThermalPipeUpdateHandler {

	private World world;
	private BlockPos sourcePos;
	private Block sourceBlock;

	public ThermalPipeUpdateHandler(World world, BlockPos sourcePos, Block sourceBlock) {
		this.world = world;
		this.sourcePos = sourcePos;
		this.sourceBlock = sourceBlock;
	}

	public void neighborUpdate() {




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

//		super.neighborUpdate(state, world, originalPos, sourceBlock, sourcePos, notify);
	}

	public void deleteLonelyPipe()
	{
		if (!world.isClient)
		{
			ThermalPipeLocator locator = new ThermalPipeLocator(world, sourcePos, sourceBlock);
			// only a single pipe was involved
			if (locator.getImmediatePipeBlocks().size() == 0)
			{
				ThermalPipeRegionManager regionManager = new ThermalPipeRegionManager(world);
				regionManager.deleteRegionsIncludingPipe(sourcePos);
			}
		}
	}

	public void addLonelyPipe()
	{
		if (!world.isClient)
		{
			ThermalPipeLocator locator = new ThermalPipeLocator(world, sourcePos, sourceBlock);
			// only a single pipe was involved
			if (locator.getImmediatePipeBlocks().size() == 0)
			{
				ThermalPipeRegionManager regionManager = new ThermalPipeRegionManager(world);
				PipeNetwork newNetwork = new PipeNetwork(world);
				newNetwork.addPipeBlock(sourcePos);
				regionManager.saveNetwork(newNetwork);
			}
		}
	}
}
