package com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.hot.blockentity;

import com.theswirlingvoid.polarmachinery.Main;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.enums.TemperatureType;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.machine.GeneratingMachine;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class BurnerBlockEntity extends GeneratingMachine {

	public static final String ID = "burner_block_entity";
	private static final float MAX_TEMP = 100f;
	private static final TemperatureType TEMPERATURE_TYPE = TemperatureType.HOT;

	public BurnerBlockEntity(BlockPos pos, BlockState state) {
		super(TEMPERATURE_TYPE, MAX_TEMP, pos, state);
	}

	public static Identifier getIdentifier()
	{
		return new Identifier(Main.MOD_ID, ID);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.getGeneratingComponent().getTemperatureStorage().setCurrentTemperature(
				nbt.getFloat("temperature")
		);
		super.readNbt(nbt);
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		nbt.putFloat(
			"temperature",
			super.getGeneratingComponent().getTemperatureStorage().getCurrentTemperature()
		);
		super.writeNbt(nbt);
	}
}
