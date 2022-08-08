package com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.hot;

import com.theswirlingvoid.polarmachinery.Main;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.enums.TemperatureType;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.machine.GeneratingMachine;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class BurnerBlockEntity extends GeneratingMachine {

	public static final String ID = "burner_block_entity";
	private static final float MAX_TEMP = 100f;
	private static final TemperatureType TEMPERATURE_TYPE = TemperatureType.HOT;

	public BurnerBlockEntity(BlockPos pos, BlockState state) {
		super(TemperatureType.HOT, MAX_TEMP, pos, state);
	}

	public static Identifier getIdentifier()
	{
		return new Identifier(Main.MOD_ID, ID);
	}
}
