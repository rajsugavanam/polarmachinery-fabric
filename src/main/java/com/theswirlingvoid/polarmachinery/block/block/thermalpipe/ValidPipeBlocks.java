package com.theswirlingvoid.polarmachinery.block.block.thermalpipe;

import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.blocksandbentities.generic.PipeConnectionEndBlock;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.blocksandbentities.generic.PipeConnectionEndBlockWithEntity;
import net.minecraft.block.Block;

public class ValidPipeBlocks {
	
	public static boolean isValidPipeBlock(Block block)
	{
		return (
			block instanceof ThermalPipe
			//||
		);
	}

	public static boolean isValidConnectionEnd(Block block)
	{
		return (
			block instanceof PipeConnectionEndBlock
			|| block instanceof PipeConnectionEndBlockWithEntity
			//||
		);
	}
}
