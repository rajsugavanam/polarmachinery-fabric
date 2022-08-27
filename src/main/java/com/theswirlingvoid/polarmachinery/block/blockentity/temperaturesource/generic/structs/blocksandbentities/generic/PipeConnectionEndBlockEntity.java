package com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.generic.structs.blocksandbentities.generic;

import com.theswirlingvoid.polarmachinery.block.block.thermalpipe.thermalpiperegion.ThermalPipeRegionManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.SerializationUtils;
import org.json.JSONStringer;

import java.io.File;
import java.util.*;

public abstract class PipeConnectionEndBlockEntity extends BlockEntity {

	private HashSet<UUID> attachedNetworks = new HashSet<>();

	public PipeConnectionEndBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public void readNbt(NbtCompound nbt) {

		// add UUIDS to variable from NBT
		byte[] stringNetworks = nbt.getByteArray("attachedNetworks");
		attachedNetworks =  SerializationUtils.deserialize(stringNetworks);

		super.readNbt(nbt);
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {

		byte[] serializedNetworks = SerializationUtils.serialize(attachedNetworks);
		nbt.putByteArray("attachedNetworks", serializedNetworks);
		super.writeNbt(nbt);
	}

	public List<File> getNetworksAtPosition()
	{
		return null;
	}

}
