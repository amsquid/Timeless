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

	public float intersect(Vector3f rayOrigin, Vector3f rayDirection, float intersectionMin, float intersectionMax) {
		float rayPoint = intersectionMin;

		Vector3f intersectionPoint = new Vector3f();

		// Check intersection with x-axis
		if (rayDirection.x != 0) {
			intersectionPoint.x = (min.x - rayOrigin.x) / rayDirection.x;
			if (intersectionPoint.x > intersectionMax || intersectionPoint.x < intersectionMin) {
				return -1.0f;
			}
			if (intersectionPoint.x > rayPoint) {
				rayPoint = intersectionPoint.x;
			}
		}

		// Check intersection with y-axis
		if (rayDirection.y != 0) {
			intersectionPoint.y = (min.y - rayOrigin.y) / rayDirection.y;
			if (intersectionPoint.y > intersectionMax || intersectionPoint.y < intersectionMin) {
				return -1.0f;
			}
			if (intersectionPoint.y > rayPoint) {
				rayPoint = intersectionPoint.y;
			}
		}

		// Check intersection with z-axis
		if (rayDirection.z != 0) {
			intersectionPoint.z = (min.z - rayOrigin.z) / rayDirection.z;
			if (intersectionPoint.z > intersectionMax || intersectionPoint.z < intersectionMin) {
				return -1.0f;
			}
			if (intersectionPoint.z > rayPoint) {
				rayPoint = intersectionPoint.z;
			}
		}

		// Check if the intersection point is within the AABB
		Vector3f point = new Vector3f(rayOrigin.x + rayDirection.x * rayPoint, rayOrigin.y + rayDirection.y * rayPoint, rayOrigin.z + rayDirection.z * rayPoint);
		if (point.x >= min.x && point.x <= max.x && point.y >= min.y && point.y <= max.y && point.z >= min.z && point.z <= max.z) {
			return rayPoint;
		}

		return -1.0f;
	}

	public Vector3f getNormal(Vector3f rayDirection) {
		// Calculate the normal vector based on the ray direction
		Vector3f normal = new Vector3f();

		if (Math.abs(rayDirection.x) > Math.abs(rayDirection.y) && Math.abs(rayDirection.x) > Math.abs(rayDirection.z)) {
			normal.x = 1.0f;
			normal.y = 0.0f;
			normal.z = 0.0f;
		} else if (Math.abs(rayDirection.y) > Math.abs(rayDirection.x) && Math.abs(rayDirection.y) > Math.abs(rayDirection.z)) {
			normal.x = 0.0f;
			normal.y = 1.0f;
			normal.z = 0.0f;
		} else {
			normal.x = 0.0f;
			normal.y = 0.0f;
			normal.z = 1.0f;
		}

		return normal;
	}
}
