package me.joepeterson.Timeless.engine.inventory;

import me.joepeterson.Timeless.engine.texture.Texture;

public class Item {
	private final ItemType itemType;
	private final int itemStackSize;
	private final String name;
	private final Texture texture;
	private final boolean isBreakable;

	public Item(ItemType itemType, int itemStackSize, String name, Texture texture, boolean isBreakable) {
		this.itemType = itemType;
		this.itemStackSize = itemStackSize;
		this.name = name;
		this.texture = texture;
		this.isBreakable = isBreakable;
	}

	public int getItemStackSize() {
		return itemStackSize;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public String getName() {
		return name;
	}

	public Texture getTexture() {
		return texture;
	}

	public boolean isBreakable() {
		return isBreakable;
	}
}
