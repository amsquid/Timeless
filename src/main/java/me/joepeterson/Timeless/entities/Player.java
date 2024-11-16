package me.joepeterson.Timeless.entities;

import me.joepeterson.Timeless.engine.BoundingBox;
import me.joepeterson.Timeless.engine.entity.LivingEntity;
import me.joepeterson.Timeless.engine.inventory.Item;
import me.joepeterson.Timeless.engine.util.Vector;
import org.joml.Vector3f;

import java.util.ArrayList;

public class Player extends LivingEntity {

	private float scale = 1.0f;

	public boolean sprinting = false;

	private ArrayList<Item> inventory = new ArrayList<Item>();

	public ArrayList<Item> getInventory() {
		return inventory;
	}

	public void addItem(Item item) {
		inventory.add(item);
	}

	public boolean removeItem(Item item) {
		return inventory.remove(item);
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public float getScale() {
		return scale;
	}

	public Player(float speed) {
		super(speed);

		Vector3f min = Vector.multiplyVector(new Vector3f(-.25f, .0f, -.25f), scale);
		Vector3f max = Vector.multiplyVector(new Vector3f(.25f, 1.25f, .25f), scale);

		setMin(min);
		setMax(max);

		BoundingBox boundingBox = new BoundingBox(getMin(), getMax());

		this.setBoundingBox(boundingBox);
	}
}
