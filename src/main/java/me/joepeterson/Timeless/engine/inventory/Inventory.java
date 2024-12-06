package me.joepeterson.Timeless.engine.inventory;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<ItemStack> inventory;

    public Inventory() {
        this.inventory = new ArrayList<>();
    }

    public Inventory(ArrayList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    public ArrayList<ItemStack> getItems() {
        return inventory;
    }

    public void addItem(ItemStack itemStack) {
        inventory.add(itemStack);
    }

    public void addItem(Item item, int amount) {
        ItemStack itemStack = new ItemStack(item, amount);

        inventory.add(itemStack);
    }

    public boolean removeItem(ItemStack itemStack) {
        return inventory.remove(itemStack);
    }
}
