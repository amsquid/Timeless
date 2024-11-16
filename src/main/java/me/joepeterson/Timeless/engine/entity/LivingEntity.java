package me.joepeterson.Timeless.engine.entity;

public class LivingEntity extends Entity {

	public float health;
	public float maxHealth;
	public float damage;
	public float speed;

	public float getHealth() {
		return health;
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

	public LivingEntity(float speed) {
		super();

		this.speed = speed;
		this.health = 100;
		this.maxHealth = 100;
		this.damage = 1;
	}

}
