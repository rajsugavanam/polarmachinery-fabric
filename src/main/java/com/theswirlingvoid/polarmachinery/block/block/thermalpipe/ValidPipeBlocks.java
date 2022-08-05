package com.theswirlingvoid.polarmachinery.block.block.thermalpipe;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

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
			block == Blocks.IRON_BLOCK
			//||
		);
	}
}
