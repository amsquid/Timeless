package me.joepeterson.Timeless.engine.inventory;

public class ItemStack<item extends Item> {
    private final item item;
    private int amount;

    public ItemStack(item item) {
        this.item = item;
        amount = 1;
    }

    public ItemStack(item item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public boolean addAmount(int amount) {
        if (this.amount + amount > item.getItemStackSize()) return false;
        this.amount += amount;
        return true;
    }
}
