package com.theswirlingvoid.polarmachinery.item.utility.applicator;

import java.util.List;

import com.theswirlingvoid.polarmachinery.Main;
import com.theswirlingvoid.polarmachinery.generic.color.AlphaRGB;
import com.theswirlingvoid.polarmachinery.item.utility.applicator.screen.inventory.ApplicatorScreenHandler;
import com.theswirlingvoid.polarmachinery.item.utility.applicator.screen.select.ApplicatorSelectScreen;
import com.theswirlingvoid.polarmachinery.item.utility.applicator.screen.select.options.ColorOptions;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class Applicator extends Item {

	private ApplicatorInventory inventory;
	private ItemStack selectedTool;

	public static final String ID = "applicator";

	public Applicator() 
	{
		super(getSettings());
	}

	public static FabricItemSettings getSettings()
	{
		return new FabricItemSettings()
		.maxCount(1)
		.group(Main.IGROUP);
	}

	public static Identifier getIdentifier()
	{
		return new Identifier(Main.MOD_ID, ID);
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.translatable("tooltip." + ID + ".desc").formatted(Formatting.GRAY).formatted(Formatting.ITALIC));
		super.appendTooltip(stack, world, tooltip, context);
	}

	public void writeToNBT()
	{
		inventory.writeToNBT();
	}

	public void readFromNBT()
	{
		inventory.readFromNBT();
	}



	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);

		inventory = new ApplicatorInventory(stack);

		// writes default inventory nbt with 8 blank slots
		if (!stack.hasNbt())
		{
			writeToNBT();
		}

		if (user.isSneaking() == false)
		{
			readFromNBT();

			// only reder on client
			if (world.isClient == true)
			{
				MinecraftClient client = MinecraftClient.getInstance();

				ColorOptions colorOptions = new ColorOptions(
					new AlphaRGB(30f, 30f, 30f, 0.9f), 
					new AlphaRGB(80f, 80f, 80f, 0.9f)
				);

				ApplicatorSelectScreen selectScreen = new ApplicatorSelectScreen(colorOptions, 2f, inventory);

				client.setScreen(selectScreen);
			}

		} else {
			if (!world.isClient)
			{
				SimpleNamedScreenHandlerFactory factory = ApplicatorScreenHandler.createScreenHandlerFactory(stack);

				user.openHandledScreen(factory);
			}
		}
		return TypedActionResult.success(stack);
	}



	public ApplicatorInventory getStoredTools() {
		return inventory;
	}

	public void setStoredTools(ApplicatorInventory inventory) {
		this.inventory = inventory;
	}

	public ItemStack getSelectedTool() {
		return selectedTool;
	}

	public void setSelectedTool(ItemStack selectedTool) {
		this.selectedTool = selectedTool;
	}

}
