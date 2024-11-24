package me.joepeterson.Timeless.engine.inventory;

public class ItemStack {
    private int amount;
    protected Item item;

    public ItemStack(Item item) {
        this.item = item;
        amount = 1;
    }

    public ItemStack(Item Item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public boolean addAmount(int amount) {
        if ((this.amount + amount) > item.getItemStackSize()) return false;
        this.amount += amount;
        return true;
    }
}
