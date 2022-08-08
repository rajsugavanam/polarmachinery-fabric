package com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.machine;

import com.theswirlingvoid.polarmachinery.block.ModBlocks;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.enums.TemperatureType;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.blocksandbentities.generic.PipeConnectionEndBlock;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.blocksandbentities.generic.PipeConnectionEndBlockEntity;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.component.GeneratingComponent;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.component.InterfacingComponent;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.component.PolarStorageComponent;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class InterfacingMachine extends PipeConnectionEndBlockEntity implements IPipeMachine {

	private InterfacingComponent interfacingComponent;

	public InterfacingMachine(TemperatureType tempType, float maxTemperature, BlockPos pos, BlockState state) {
		super(ModBlocks.BURNER_BLOCKENTITY, pos, state);
		interfacingComponent = new InterfacingComponent(tempType, maxTemperature);
	}

	public InterfacingComponent getInterfacingComponent() {
		return interfacingComponent;
	}

	@Override
	public PolarStorageComponent getStorageComponent() {
		return interfacingComponent;
	}
}
