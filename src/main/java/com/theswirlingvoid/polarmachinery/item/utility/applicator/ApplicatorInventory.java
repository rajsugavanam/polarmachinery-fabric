package com.theswirlingvoid.polarmachinery.item.utility.applicator;

import com.theswirlingvoid.polarmachinery.screens.ModScreenHandlers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;

public class ApplicatorInventory implements Inventory {

	private ItemStack boundItem;
	public DefaultedList<ItemStack> storedTools;

	public ApplicatorInventory(ItemStack stack)
	{
		int size = ModScreenHandlers.SWITCHER_INVENTORY_SIZE;
		storedTools = DefaultedList.ofSize(size, ItemStack.EMPTY);
		this.boundItem = stack;
		
		NbtCompound nbt = stack.getSubNbt("storedTools");
		// read nbt to variable if it has data
		if (nbt != null)
		{
			Inventories.readNbt(nbt, storedTools);
			// if desired size can accommodate more than previous, transfer items over
			if (storedTools.size() < size)
			{
				DefaultedList<ItemStack> transferInventory = DefaultedList.ofSize(size, ItemStack.EMPTY);
				for (int toolIndex = 0; toolIndex < storedTools.size(); toolIndex++)
				{
					transferInventory.set(toolIndex, storedTools.get(toolIndex));
				}
				storedTools = transferInventory;
			}
		}
	}


	public ItemStack getBoundItem() {
		return boundItem;
	}


	public void setBoundItem(ItemStack boundItem) {
		this.boundItem = boundItem;
	}


	public void writeToNBT()
	{
		// save data from storedTools to nbt
		NbtCompound nbt = boundItem.getOrCreateSubNbt("storedTools");
		nbt = Inventories.writeNbt(nbt, storedTools);
		boundItem.setSubNbt("storedTools", nbt);
	}

	public void readFromNBT()
	{
		// save data from nbt to storedTools
		NbtCompound nbt = boundItem.getSubNbt("storedTools");
		Inventories.readNbt(nbt, storedTools);
	}


	@Override
	public void clear() {
		storedTools.clear();
	}

	@Override
	public boolean canPlayerUse(PlayerEntity player) {
		return true;
	}

	@Override
	public ItemStack getStack(int slot) {
		return storedTools.get(slot);
	}

	@Override
	public boolean isEmpty() {
		return storedTools.isEmpty();
	}

	@Override
	public void markDirty() 
	{
		writeToNBT();
	}

	@Override
	public ItemStack removeStack(int slot) {
		return storedTools.remove(slot);
	}

	@Override
	public ItemStack removeStack(int slot, int amount) {

		ItemStack istack = storedTools.get(slot);
		ItemStack original = istack.copy();

		istack.setCount(istack.getCount()-1);

		return original;
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
		storedTools.set(slot, stack);
	}

	@Override
	public int size() {
		return storedTools.size();
	}
	
}
