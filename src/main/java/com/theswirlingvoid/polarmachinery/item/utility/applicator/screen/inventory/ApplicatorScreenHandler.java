package com.theswirlingvoid.polarmachinery.item.utility.applicator.screen.inventory;

import com.theswirlingvoid.polarmachinery.Main;
import com.theswirlingvoid.polarmachinery.item.utility.applicator.ApplicatorInventory;
import com.theswirlingvoid.polarmachinery.item.utility.applicator.ApplicatorSlot;
import com.theswirlingvoid.polarmachinery.screens.ModScreenHandlers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

//* THE SERVER-BASED SIDE */
public class ApplicatorScreenHandler extends ScreenHandler {
	private ApplicatorInventory inventory;

	// called by register
	public ApplicatorScreenHandler(int syncId, PlayerInventory playerInventory, ItemStack stack) {
		this(syncId, playerInventory, new ApplicatorInventory(stack));
	}

	// for opening
	public ApplicatorScreenHandler(int syncId, PlayerInventory playerInventory, PlayerEntity ent, ItemStack stack) {
		this(syncId, playerInventory, new ApplicatorInventory(stack));
	}

	public ApplicatorScreenHandler(int syncId, PlayerInventory playerInventory, ApplicatorInventory switcherInventory) {
		super(ModScreenHandlers.TOOL_INV_SCREEN_HANDLER_TYPE, syncId);

		this.inventory = switcherInventory;
		inventory.onOpen(playerInventory.player);

		int idxX;
        int idxY;	

		//the inventory; default 0 to 8
		for (idxX = 0; idxX < inventory.size()/2; ++idxX) {
			for (idxY = 0; idxY < 2; idxY++) {
				// set the applicator slots to be custom-made so only certain items can go in
				this.addSlot(new ApplicatorSlot(inventory, this.inventory.getBoundItem(), (2*idxX)+idxY, 44+idxX*18, 17+idxY*18));
			}
		}
		
		//the player inventory; starts at index 9 to end
		for (idxX = 0; idxX < 3; ++idxX) {
			for (idxY = 0; idxY < 9; ++idxY) {
				this.addSlot(new Slot(playerInventory, idxY + idxX * 9 + 9, 8 + idxY * 18, 84 + idxX * 18));
			}
		}
		//the player Hotbar; index 0 to 8
		for (idxX = 0; idxX < 9; ++idxX) {
			this.addSlot(new Slot(playerInventory, idxX, 8 + idxX * 18, 142));
		}
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return true;
	}

	@Override
	public ItemStack transferSlot(PlayerEntity player, int invSlot) {
		// inv slot can be any visible slot on the screen, from the opened inventory to the hotbar to the player inv
		ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);

        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();

			// if a stack in the OPENED inventory was clicked
            if (invSlot < this.inventory.size()) {
				// this.slots.size = total slots, inventory.size = opened inventory slots
				// returns whether the item transfer succeeded; ! makes it whether it failed
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }
 
            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
 
        return newStack;
	}

	public static SimpleNamedScreenHandlerFactory createScreenHandlerFactory(ItemStack boundItem)
	{
		return new SimpleNamedScreenHandlerFactory(
			(syncId, playerInventory, ent) -> new ApplicatorScreenHandler(syncId, playerInventory, ent, boundItem),
			Text.of("Applicator Items")
		);
	}

	public static Identifier getIdentifier()
	{
		return new Identifier(Main.MOD_ID, "tool_inv_screen");
	}
	
}
