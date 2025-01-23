package me.joepeterson.Timeless.engine.inventory;

import me.joepeterson.Timeless.engine.block.Block;

public class PlaceableItemStack extends ItemStack {
	Block blockToPlace;

	public PlaceableItemStack(Item item, Block block) {
		super(item);

		this.blockToPlace = block;
	}

	public PlaceableItemStack(Item item, Block block, int amount) {
		super(item, amount);

		this.blockToPlace = block;
	}
}
