package com.theswirlingvoid.polarmachinery.block.block.thermalpipe;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ThermalPipeLocator {
	
	private final PipeNetwork network;
	private final BlockPos ultimateOriginPos;

	private final Block originalBlock;

	private final Block sourceBlockAfterUpdate;

	private final boolean pipeBroken;

	private final World world;

	// a network is defined as a chain of pipes containing TWO OR MORE.
	public ThermalPipeLocator(World world, BlockPos originPos, Block originalBlock) {
		this.network = new PipeNetwork(world);
		this.ultimateOriginPos = originPos;
		this.world = world;
		this.originalBlock = originalBlock;

		this.sourceBlockAfterUpdate = world.getBlockState(ultimateOriginPos).getBlock();
		this.pipeBroken = (sourceBlockAfterUpdate != originalBlock && ValidPipeBlocks.isValidPipeBlock(originalBlock));
	}

	public BlockPos getUltimateOriginPos()
	{
		return ultimateOriginPos;
	}

	/**
	 * Gets the positions of allowed pipe blocks immediately next to a coordinate in world.
	 * @param thisPos the position to search around
	 * @return the list of found blocks
	 */
	private Set<BlockPos> getSavedSurroundingPositions(BlockPos thisPos)
	{
		Set<BlockPos> finalPositions = new HashSet<>();

		accumulateImmediateBlock(finalPositions, thisPos.up());
		accumulateImmediateBlock(finalPositions, thisPos.down());
		accumulateImmediateBlock(finalPositions, thisPos.north());
		accumulateImmediateBlock(finalPositions, thisPos.east());
		accumulateImmediateBlock(finalPositions, thisPos.south());
		accumulateImmediateBlock(finalPositions, thisPos.west());

		return finalPositions;
	}

	private Set<BlockPos> getUnsavedSurroundingPositions(BlockPos thisPos)
	{
		Set<BlockPos> finalPositions = new HashSet<>();

		localBlockCheck(finalPositions, thisPos.up());
		localBlockCheck(finalPositions, thisPos.down());
		localBlockCheck(finalPositions, thisPos.north());
		localBlockCheck(finalPositions, thisPos.east());
		localBlockCheck(finalPositions, thisPos.south());
		localBlockCheck(finalPositions, thisPos.west());

		return finalPositions;
	}

	private void snakeSearchPositions(BlockPos thisPos)
	{

		Set<BlockPos> positions = getSavedSurroundingPositions(thisPos);

		for (BlockPos pos : positions)
		{
			snakeSearchPositions(pos);
		}
	}

	public List<PipeNetwork> findPipeNetworks()
	{

		List<PipeNetwork> finalNetworks = new ArrayList<>();

		Set<BlockPos> initialAffected = this.getUnsavedSurroundingPositions(ultimateOriginPos);


		if (pipeBroken && moreThan1Affected(initialAffected))
		{
			finalNetworks.addAll(findNetworksAfterBreak());
		} else
		{
			snakeSearchPositions(this.ultimateOriginPos);
			if (!pipeBroken)
				this.network.addPipeBlock(this.ultimateOriginPos);
			finalNetworks.add(this.network);
		}
		
		
		return finalNetworks;

	}

	public Set<BlockPos> getImmediatePipeBlocks()
	{
		// new locator to not affect the data of this instance
		ThermalPipeLocator localLocator = new ThermalPipeLocator(this.world, this.ultimateOriginPos, this.originalBlock);
		localLocator.getSavedSurroundingPositions(this.ultimateOriginPos); // this actually adds it in to the network blocks

		return localLocator.network.getPipeBlocks();
	}
	
	private List<PipeNetwork> findNetworksAfterBreak()
	{
		List<PipeNetwork> splitNetworks = new ArrayList<>();
		
		Set<BlockPos> surroundingPositions = getUnsavedSurroundingPositions(ultimateOriginPos);
		
		for (BlockPos subPos : surroundingPositions)
		{
			accumulateSubNetworkIfUnique(subPos, splitNetworks);
		}

		return splitNetworks;
	}

	private void accumulateSubNetworkIfUnique(BlockPos subPos, List<PipeNetwork> splitNetworks)
	{
		Block foundBlockType = world.getBlockState(subPos).getBlock();
		// if the adjacent blocks next to the broken pipe are pipe blocks that would've had their own networks affected
		if (ValidPipeBlocks.isValidPipeBlock(foundBlockType))
		{
			ThermalPipeLocator localLocator = new ThermalPipeLocator(world, subPos, foundBlockType);
			
			if (localLocator.isOriginPosInUniqueNetwork(splitNetworks))
			{
				splitNetworks.add(localLocator.recurseAfterSever());
			}
		}
	}

	// this will only on a pipe affected by an adjacent broken pipe!
	private PipeNetwork recurseAfterSever()
	{
		// we add a pipe position here because it would have nothing to do with the original update.
		network.addPipeBlock(this.ultimateOriginPos);
		snakeSearchPositions(this.ultimateOriginPos);
		return this.network;
	}

	private boolean isOriginPosInUniqueNetwork(List<PipeNetwork> splitNetworks)
	{
		for (PipeNetwork network : splitNetworks)
		{
			// this means the block has already been put into a network, so return false as it's not unique
			if (network.getPipeBlocks().contains(this.ultimateOriginPos))
				return false;
		}
		return true;
	}

	//! THIS IS FOR getSurroundingPositions()!
	private void accumulateImmediateBlock(Set<BlockPos> localCheckedPipePositions, BlockPos pos)
	{
		Block checkedBlock = world.getBlockState(pos).getBlock();

		// if it hasn't been checked AT ALL
		if (!this.network.getPipeBlocks().contains(pos) && !this.network.getConnectionEndings().contains(pos)) 
		{
			if (ValidPipeBlocks.isValidPipeBlock(checkedBlock))
			{
				localCheckedPipePositions.add(pos);
				this.network.addPipeBlock(pos);
			} 
			else if (ValidPipeBlocks.isValidConnectionEnd(checkedBlock))
			{
				// connection ends are NOT PIPES!
				// adding them would result in the network traversing through "machines,"
				// and we don't want that!
				// we add them to the connection ending list so we can track them, but
				// we don't further recurse on them.
				this.network.addConnectionEnding(pos);
			}
			// we add it here to get the immediate blocks next to a given pos, to see where to search next - it doesn't matter here if the position is a pipe end or not
		}
	}

	//! THIS IS FOR getInitialPositions()!
	private void localBlockCheck(Set<BlockPos> localCheckedPipePositions, BlockPos pos)
	{
		Block checkedBlock = world.getBlockState(pos).getBlock();

		if (ValidPipeBlocks.isValidPipeBlock(checkedBlock) || ValidPipeBlocks.isValidConnectionEnd(checkedBlock))
		{
			localCheckedPipePositions.add(pos);
		}
	}

	private boolean isUpdateWorthyOfRecurse()
	{
		Set<BlockPos> immPositions = getUnsavedSurroundingPositions(ultimateOriginPos);
		return (
			wasPipeEndModified(immPositions)
			|| moreThan1Affected(immPositions)
			);
	}

	private boolean wasPipeEndModified(Set<BlockPos> immPositions)
	{
		if (connectionEndDirectlyModified()) {
			return true;
			// if the block in question wasn't directly a connection end, move on with the checks
		}

		for (BlockPos immPos : immPositions)
		{
			if (ValidPipeBlocks.isValidConnectionEnd(world.getBlockState(immPos).getBlock()))
			{
				return true;
			}
		}
		return false;
	}

	private boolean moreThan1Affected(Set<BlockPos> immPositions)
	{
		return (immPositions.size() > 1);
	}

	private boolean connectionEndDirectlyModified()
	{
		return (
			// if the original block involved was a connection end (this will be true if a connection end was broken)
			ValidPipeBlocks.isValidConnectionEnd(originalBlock)
			// if a connection end was placed on a pipe network (basically, if the updated/new block is a connection end)
			|| ValidPipeBlocks.isValidConnectionEnd(sourceBlockAfterUpdate)
		);
	}

}
