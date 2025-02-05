package me.joepeterson.Timeless.engine.inventory;

import me.joepeterson.Timeless.engine.texture.Texture;

public class Tool extends Material {
	private double durability;
	private final double durabilityMax;

	public Tool(int itemStackSize, String name, Texture texture, double durabilityMax) {
		super(itemStackSize, name, texture);
		this.durabilityMax = durabilityMax;
	}

	public double getDurability() {
		return durability;
	}

	public double getDurabilityMax() {
		return durabilityMax;
	}

	public void setDurability(double durability) {
		this.durability = durability;
	}
}
