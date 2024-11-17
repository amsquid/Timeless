package me.joepeterson.Timeless.engine.inventory;

import me.joepeterson.Timeless.engine.texture.Texture;

public class BlockItem extends Item {
    private double durability;

    public BlockItem(ItemType itemType, int itemStackSize, String name, Texture texture, boolean isBreakable) {
        super(itemType, itemStackSize, name, texture, isBreakable);
    }

    public double getDurability() {
        return durability;
    }


}
