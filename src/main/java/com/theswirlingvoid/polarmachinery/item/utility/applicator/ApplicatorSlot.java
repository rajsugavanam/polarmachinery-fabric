package com.theswirlingvoid.polarmachinery.item.utility.applicator;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class ApplicatorSlot extends Slot {

	private ItemStack boundItem;

	public ApplicatorSlot(Inventory inventory, ItemStack boundItem, int index, int x, int y) {
		super(inventory, index, x, y);
		this.boundItem = boundItem;
	}

	@Override
	public boolean canInsert(ItemStack itemStack) {
		Item itemType = itemStack.getItem();
		// if the item type is allowed, and if the item inserted isn't the bound stack
		boolean canInsert = ApplicatorValidItems.isValidItem(itemType) && (itemStack!=boundItem);
		return canInsert;
	}
	
}
