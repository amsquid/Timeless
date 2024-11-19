package me.joepeterson.Timeless.engine;

import me.joepeterson.Timeless.engine.util.Vector;
import org.joml.Vector3f;

public class BoundingBox {

	private Vector3f min;
	private Vector3f max;

	public BoundingBox(Vector3f min, Vector3f max) {
		this.min = min;
		this.max = max;
	}

	public Vector3f getMin() {
		return min;
	}

	public Vector3f getMax() {
		return max;
	}

	public boolean collidesWith(Vector3f offset, BoundingBox box) {
		Vector3f aMin = Vector.addVectors(this.min, offset);
		Vector3f aMax = Vector.addVectors(this.max, offset);

		Vector3f bMin = box.getMin();
		Vector3f bMax = box.getMax();

		boolean test1X = aMax.x >= bMin.x;
		boolean test2X = aMin.x <= bMax.x;

		boolean test1Y = aMax.y >= bMin.y;
		boolean test2Y = aMin.y <= bMax.y;

		boolean test1Z = aMax.z >= bMin.z;
		boolean test2Z = aMin.z <= bMax.z;

		// An intersection has happened if all tests are true
		return
				test1X && test2X &&
				test1Y && test2Y &&
				test1Z && test2Z;
	}
}
