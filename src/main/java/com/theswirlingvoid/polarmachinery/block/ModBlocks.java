package com.theswirlingvoid.polarmachinery.block;

import com.theswirlingvoid.polarmachinery.block.block.thermalpipe.ThermalPipe;

import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.hot.block.Burner;
import com.theswirlingvoid.polarmachinery.block.blockentity.temperaturesource.hot.blockentity.BurnerBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
	// applicator
	public static final Block THERMAL_PIPE = new ThermalPipe();
	public static final BlockItem THERMAL_PIPE_BLOCKITEM = ThermalPipe.getBlockItem();


//	GENERATION, RECEIVING, INTERFACING MACHINES
	public static final Block BURNER = new Burner();
	public static final BlockItem BURNER_BLOCKITEM = Burner.getBlockItem();
	public static final BlockEntityType<BurnerBlockEntity> BURNER_BLOCKENTITY = FabricBlockEntityTypeBuilder.create(BurnerBlockEntity::new, BURNER).build();

	public static void registerBlocksAndBlockItems()
	{
		Registry.register(Registry.BLOCK, ThermalPipe.getIdentifier(), THERMAL_PIPE);
		Registry.register(Registry.ITEM, ThermalPipe.getIdentifier(), THERMAL_PIPE_BLOCKITEM);

		Registry.register(Registry.BLOCK, Burner.getIdentifier(), BURNER);
		Registry.register(Registry.ITEM, Burner.getIdentifier(), BURNER_BLOCKITEM);
	}

	public static void registerBlockEntities()
	{
		Registry.register(Registry.BLOCK_ENTITY_TYPE, BurnerBlockEntity.getIdentifier(), BURNER_BLOCKENTITY);
	}
}
