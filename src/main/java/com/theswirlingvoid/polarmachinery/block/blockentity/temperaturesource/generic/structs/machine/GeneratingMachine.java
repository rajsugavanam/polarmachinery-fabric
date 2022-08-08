package com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.machine;

import com.theswirlingvoid.polarmachinery.block.ModBlocks;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.enums.TemperatureType;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.blocksandbentities.generic.PipeConnectionEndBlockEntity;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.component.GeneratingComponent;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.component.PolarStorageComponent;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class GeneratingMachine extends PipeConnectionEndBlockEntity implements IPipeMachine {

	private GeneratingComponent generatingComponent;

	public GeneratingMachine(TemperatureType tempType, float maxTemperature, BlockPos pos, BlockState state) {
		super(ModBlocks.BURNER_BLOCKENTITY, pos, state);
		generatingComponent = new GeneratingComponent(tempType, maxTemperature);
	}

	public GeneratingComponent getGeneratingComponent() {
		return generatingComponent;
	}

	@Override
	public PolarStorageComponent getStorageComponent() {
		return generatingComponent;
	}
}
