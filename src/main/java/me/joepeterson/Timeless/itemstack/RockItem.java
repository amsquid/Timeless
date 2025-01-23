package me.joepeterson.Timeless.itemstack;

import me.joepeterson.Timeless.block.RockBlock;
import me.joepeterson.Timeless.engine.inventory.Item;
import me.joepeterson.Timeless.engine.inventory.PlaceableItemStack;
import me.joepeterson.Timeless.item.Rock;
import org.joml.Vector3i;

import java.io.IOException;

public class RockItem extends PlaceableItemStack {

	private static final RockBlock block = new RockBlock(new Vector3i());
	public static Item item;

	static {
		try {
			item = new Rock();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public RockItem(Item item) {
		super(item, block);
	}

	public RockItem(Item item, int amount) {
		super(item, block, amount);
	}
}
