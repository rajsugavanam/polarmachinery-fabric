package com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.blocksandbentities.generic;

import com.theswirlingvoid.polarmachinery.block.block.thermalpipe.thermalpiperegion.ThermalPipeRegionManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public abstract class PipeConnectionEndBlockEntity extends BlockEntity {

	public PipeConnectionEndBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public List<File> getNetworksAtPosition()
	{
		return null;
	}

}
