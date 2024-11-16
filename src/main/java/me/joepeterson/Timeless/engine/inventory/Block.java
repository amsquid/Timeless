package me.joepeterson.Timeless.engine.inventory;

import me.joepeterson.Timeless.engine.texture.Texture;

public class Block extends Item {
    private double durability;

    public Block(ItemType itemType, int itemStackSize, String name, Texture texture, double hardness, boolean isBreakable) {
        super(itemType, itemStackSize, name, texture, hardness, isBreakable);
    }

    public double getDurability() {
        return durability;
    }


}
