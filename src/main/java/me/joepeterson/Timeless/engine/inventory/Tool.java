package me.joepeterson.Timeless.engine.inventory;

import me.joepeterson.Timeless.engine.texture.Texture;

public class Tool extends Item {
    private double durability;
    private final double durabilityMax;

    public Tool(ItemType itemType, int itemStackSize, String name, Texture texture, boolean isBreakable, double durabilityMax) {
        super(itemType, itemStackSize, name, texture, isBreakable);
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
