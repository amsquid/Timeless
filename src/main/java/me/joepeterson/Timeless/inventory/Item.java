package me.joepeterson.Timeless.inventory;

public class Item {
    private int amount;
    protected Material material;

    public Item(Material material) {
        this.material = material;
        this.amount = 1;
    }

    public Item(Material material, int amount) {
        this.material = material;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public Material getMaterial() {
        return this.material;
    }

    public boolean addAmount(int amount) {
        if ((this.amount + amount) > material.stackSize) return false;
        this.amount += amount;
        return true;
    }
}
