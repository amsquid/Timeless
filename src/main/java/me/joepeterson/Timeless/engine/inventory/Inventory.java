package me.joepeterson.Timeless.engine.inventory;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<ItemStack<? extends Item>> inventory;

    public Inventory() {
        this.inventory = new ArrayList<>();
    }

    public Inventory(ArrayList<ItemStack<? extends Item>> inventory) {
        this.inventory = inventory;
    }

    public ArrayList<ItemStack<? extends Item>> getInventory() {
        return inventory;
    }
    public void addItem(ItemStack<? extends Item> itemStack) {
        inventory.add(itemStack);
    }

    public boolean removeItem(ItemStack<? extends Item> itemStack) {
        return inventory.remove(itemStack);
    }
}
