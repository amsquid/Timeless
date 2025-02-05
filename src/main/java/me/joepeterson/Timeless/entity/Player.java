package me.joepeterson.Timeless.entity;

import me.joepeterson.Timeless.engine.BoundingBox;
import me.joepeterson.Timeless.engine.entity.LivingEntity;
import me.joepeterson.Timeless.engine.inventory.Item;
import me.joepeterson.Timeless.engine.inventory.Material;
import me.joepeterson.Timeless.engine.util.Vector;
import me.joepeterson.Timeless.engine.world.World;
import org.joml.Vector3f;

import java.util.ArrayList;

public class Player extends LivingEntity {

	private float scale = 1.0f;

	public boolean sprinting = false;

	public boolean inventoryOpen = false;

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

	public void moveAndCollide(Vector3f velocity, World world) {
		if(inventoryOpen) return; // Don't move if inventory is open

		// Future Velocities
		Vector3f futureForwardVelocity = new Vector3f(0.0f, 0.0f, velocity.z);
		Vector3f futureUpVelocity = new Vector3f(0.0f, velocity.y, 0.0f);
		Vector3f futureRightVelocity = new Vector3f(velocity.x, 0.0f, 0.0f);

		// Doing collision checks for each of the future velocities
		boolean futureForwardCollision = checkBlockCollisions(futureForwardVelocity, world.getBlocks());
		boolean futureUpCollision = checkBlockCollisions(futureUpVelocity, world.getBlocks());
		boolean futureRightCollision = checkBlockCollisions(futureRightVelocity, world.getBlocks());

		// Stopping the player in the direction they're colliding with
		if(futureForwardCollision) velocity = new Vector3f(velocity.x, velocity.y, 0.0f);
		if(futureUpCollision) velocity = new Vector3f(velocity.x, 0.0f, velocity.z);
		if(futureRightCollision) velocity = new Vector3f(0.0f, velocity.y, velocity.z);;

		// Updating velocities based on collisions
		setVelocity(velocity.x, velocity.y, velocity.z);
	}

	public void giveItem(Material material, int amount) {
		Item item = new Item(material, amount);

		getInventory().add(item);
	}

	public void giveItem(Item item) {
		getInventory().add(item);
	}
}
