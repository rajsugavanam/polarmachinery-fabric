package com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.machine;

import com.theswirlingvoid.polarmachinery.block.ModBlocks;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.enums.TemperatureType;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.blocksandbentities.generic.PipeConnectionEndBlock;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.blocksandbentities.generic.PipeConnectionEndBlockEntity;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.component.GeneratingComponent;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.component.InterfacingComponent;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.component.PolarStorageComponent;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.component.ReceivingComponent;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class ReceivingMachine extends PipeConnectionEndBlockEntity implements IPipeMachine {

	private ReceivingComponent receivingComponent;

	public ReceivingMachine(TemperatureType tempType, float maxTemperature, BlockPos pos, BlockState state) {
		super(ModBlocks.BURNER_BLOCKENTITY, pos, state);
		receivingComponent = new ReceivingComponent(tempType, maxTemperature);
	}

	public ReceivingComponent getReceivingComponent() {
		return receivingComponent;
	}

	@Override
	public PolarStorageComponent getStorageComponent() {
		return receivingComponent;
	}
}
