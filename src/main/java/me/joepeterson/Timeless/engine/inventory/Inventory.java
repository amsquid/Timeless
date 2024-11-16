package me.joepeterson.Timeless.engine.inventory;

import java.util.ArrayList;

public class Inventory {

    private ArrayList<Item> inventory = new java.util.ArrayList<Item>();

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public boolean removeItem(Item item) {
        return inventory.remove(item);
    }
}
