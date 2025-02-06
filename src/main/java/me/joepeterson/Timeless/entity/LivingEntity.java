package me.joepeterson.Timeless.entity;

import me.joepeterson.Timeless.engine.entity.Entity;
import me.joepeterson.Timeless.inventory.Item;
import me.joepeterson.Timeless.inventory.Material;

import java.util.ArrayList;

public class LivingEntity extends Entity {

	private float health;
	private float maxHealth;
	private float damage;
	private float speed;

	private final int maxInventorySize;
	ArrayList<Item> inventory;

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		if(health > this.maxHealth) {
			this.health = health;
		} else {
			this.health = maxHealth;
		}
	}

	public float getMaxHealth() {
		return maxHealth;
	}

	public float getDamage() {
		return damage;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getSpeed() {
		return speed;
	}

	public ArrayList<Item> getInventory() {
		return this.inventory;
	}

	public LivingEntity(float speed, int maxInventorySize) {
		super();

		this.speed = speed;
		this.health = 100;
		this.maxHealth = 100;
		this.damage = 1;
		this.inventory = new ArrayList<>();
		this.maxInventorySize = maxInventorySize;
	}

	public boolean giveItem(Material material, int amount) {
		Item item = new Item(material, amount);
		return giveItem(item);
	}

	public boolean giveItem(Item item) {
		if(inventory.size() >= maxInventorySize) return false;

		for(int i = 0; i < inventory.size(); i++) {
			Item item1 = inventory.get(i);

			if(item1.getMaterial() == item.getMaterial()) {
				item1.addAmount(item.getAmount());
				return true;
			}

			if(item1.getMaterial() == Material.AIR) {
				inventory.set(i, item);
				return true;
			}
		}

		inventory.add(item);
		return true;
	}

}
