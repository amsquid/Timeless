package me.joepeterson.Timeless.engine.entity;

import me.joepeterson.Timeless.engine.BoundingBox;
import me.joepeterson.Timeless.engine.block.Block;
import me.joepeterson.Timeless.engine.util.Vector;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.Map;

public class Entity {
	private final Vector3f position;
	private final Vector3f velocity;
	private final Vector3f rotation;

	private Vector3f min = new Vector3f();
	private Vector3f max = new Vector3f();

	private BoundingBox entityBox;

	public Entity() {
		position = new Vector3f(0.0f, 0.0f, 0.0f);
		velocity = new Vector3f(0.0f, 0.0f, 0.0f);
		rotation = new Vector3f(0.0f, 0.0f, 0.0f);
	}

	public BoundingBox getBoundingBox() {
		return entityBox;
	}

	public BoundingBox getBoundingBoxWithOffset(Vector3f offset) {
		Vector3f min = Vector.addVectors(entityBox.getMin(), offset);
		Vector3f max = Vector.addVectors(entityBox.getMax(), offset);

		return new BoundingBox(min, max);
	}

	public void setBoundingBox(BoundingBox boundingBox) {
		this.entityBox = boundingBox;
	}

	public void setMin(Vector3f min) {
		this.min = min;
	}

	public void setMax(Vector3f max) {
		this.max = max;
	}

	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getMin() {
		return min;
	}

	public Vector3f getMax() {
		return max;
	}

	public void setPosition(float x, float y, float z) {
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
	}

	public Vector3f getVelocity() {
		return velocity;
	}

	public void setVelocity(float x, float y, float z) {
		this.velocity.x = x;
		this.velocity.y = y;
		this.velocity.z = z;
	}

	public void accelerate(float x, float y, float z) {
		this.velocity.x += x;
		this.velocity.y += y;
		this.velocity.z += z;
	}

	public Vector3f getVelocityForward(float x, float y, float z) {
		double dir = Math.toRadians(getRotation().y);

		Vector3f forward = new Vector3f(
				(float) Math.cos(dir) * z,
				0.0f,
				(float) Math.sin(dir) * z
		);

		Vector3f right = new Vector3f(
				(float) Math.cos(dir - (Math.PI / 2)) * x,
				0.0f,
				(float) Math.sin(dir - (Math.PI / 2)) * x
		);

		return new Vector3f(
				forward.x + right.x,
				y,
				forward.z + right.z
		);
	}

	public Vector3f getVelocityForward(float x, float z) {
		return getVelocityForward(x, getVelocity().y, z);
	}

	public void move() {
		this.position.x += this.velocity.x;
		this.position.y += this.velocity.y;
		this.position.z += this.velocity.z;

		Vector3f min = Vector.addVectors(getBoundingBox().getMin(), this.velocity);
		Vector3f max = Vector.addVectors(getBoundingBox().getMax(), this.velocity);

		setBoundingBox(new BoundingBox(min, max));
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(float x, float y, float z) {
		this.rotation.x = x;
		this.rotation.y = y;
		this.rotation.z = z;
	}

	public void rotate(float x, float y, float z) {
		this.rotation.x += x;
		this.rotation.y += y;
		this.rotation.z += z;
	}

	public boolean checkBlockCollisions(Vector3f offset, Map<Vector3i, Block> blocks) {
		Vector3f position = Vector.addVectors(this.position, offset);
		
		for (int x = -2 + (int) Math.ceil(position.x); x <= 2 + Math.ceil(position.x); x++) {
			for (int y = -2 + (int) Math.ceil(position.y); y <= 2 + Math.ceil(position.y); y++) {
				for (int z = -2 + (int) Math.ceil(position.z); z <= 2 + Math.ceil(position.z); z++) {
					Vector3i positionToCheck = new Vector3i(x, y, z);

					if (!blocks.containsKey(positionToCheck)) continue;

					boolean colliding = checkBlockCollision(offset, blocks.get(positionToCheck));

					if (colliding) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public boolean checkAllBlockCollisions(Vector3f offset, Map<Vector3i, Block> blocks) {
		Vector3f position = Vector.addVectors(this.position, offset);

		for(Block block : blocks.values()) {
			boolean colliding = checkBlockCollision(offset, block);

			if(colliding) return true;
		}

		return false;
	}

	public boolean checkBlockCollision(Vector3f offset, Block block) {
		Vector3f position = Vector.addVectors(this.position, offset);

		BoundingBox box = block.boundingBox;

		return getBoundingBox().collidesWith(offset, box);
	}

	public boolean checkBlockCollisions(Map<Vector3i, Block> blocks) {
		return checkBlockCollisions(new Vector3f(), blocks);
	}
}
