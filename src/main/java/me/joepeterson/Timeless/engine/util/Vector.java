package me.joepeterson.Timeless.engine.util;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class Vector {

	public static Vector3f addVectors(Vector3f a, Vector3f b) {
		return new Vector3f(a.x + b.x, a.y + b.y, a.z + b.z);
	}

	public static Vector3f addVectors(Vector3f a, Vector3i b) {
		return new Vector3f(a.x + b.x, a.y + b.y, a.z + b.z);
	}

	public static Vector3f addVectors(Vector3i a, Vector3f b) {
		return new Vector3f(a.x + b.x, a.y + b.y, a.z + b.z);
	}

	public static Vector3f addVectors(Vector3i a, Vector3i b) {
		return new Vector3f(a.x + b.x, a.y + b.y, a.z + b.z);
	}

	public static Vector3i toVector3i(Vector3f a) {
		return new Vector3i((int) a.x, (int) a.y, (int) a.z);
	}

	public static Vector3i toVector3iCeil(Vector3f a) {
		return new Vector3i((int) Math.ceil(a.x), (int) Math.ceil(a.y), (int) Math.ceil(a.z));
	}

	public static Vector3i toVector3iFloor(Vector3f a) {
		return new Vector3i((int) Math.floor(a.x), (int) Math.floor(a.y), (int) Math.floor(a.z));
	}

	public static Vector3i toVector3iRound(Vector3f a) {
		return new Vector3i((int) Math.round(a.x), (int) Math.round(a.y), (int) Math.round(a.z));
	}

	public static Vector3f toVector3f(Vector3i a) {
		return new Vector3f(a.x, a.y, a.z);
	}

	public static Vector3f multiplyVector(Vector3f a, float b) {
		return new Vector3f(a.x * b, a.y * b, a.z * b);
	}

	public static Vector3i multiplyVector(Vector3i a, int b) {
		return new Vector3i(a.x * b, a.y * b, a.z * b);
	}

	public static Vector2f multiplyVector(Vector2f a, float b) {
		return new Vector2f(a.x * b, a.y * b);
	}

	public static Vector2i multiplyVector(Vector2i a, int b) {
		return new Vector2i(a.x * b, a.y * b);
	}

	public static float distance(Vector3f a, Vector3f b) {
		return (float) Math.sqrt(
				Math.pow(a.x - b.x, 2) +
				Math.pow(a.x - b.x, 2) +
				Math.pow(a.x - b.x, 2)
		);
	}
}
