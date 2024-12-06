package me.joepeterson.Timeless.engine.entity;

import me.joepeterson.Timeless.engine.inventory.Inventory;

public class LivingEntity extends Entity {

	private float health;
	private float maxHealth;
	private float damage;
	private float speed;

	private Inventory inventory;

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

	public Inventory getInventory() {
		return this.inventory;
	}

	public LivingEntity(float speed) {
		super();

		this.speed = speed;
		this.health = 100;
		this.maxHealth = 100;
		this.damage = 1;
		this.inventory = new Inventory();
	}

}
