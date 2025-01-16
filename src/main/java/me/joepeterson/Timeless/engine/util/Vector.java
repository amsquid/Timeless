package me.joepeterson.Timeless.engine.util;

import me.joepeterson.Timeless.engine.BoundingBox;
import me.joepeterson.Timeless.engine.block.Block;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;

import javax.swing.*;

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
				Math.pow(a.y - b.y, 2) +
				Math.pow(a.z - b.z, 2)
		);
	}

	public static Vector3f getNormalBlockFace(Vector3f a, Block block) {
		if(block == null) return null;

		BoundingBox box = block.boundingBox;
		Vector3f min = box.getMin();
		Vector3f max = box.getMax();

		float tolerance = 1e-2F;

		if (Math.abs(a.x - max.x) < tolerance) {
			System.out.println("+X");
			return new Vector3f(1, 0, 0); // Normal vector for +X face
		} else if (Math.abs(a.x - min.x) < tolerance) {
			System.out.println("-X");
			return new Vector3f(-1.001f, 0, 0); // Normal vector for -X face
		} else if (Math.abs(a.y - max.y) < tolerance) {
			System.out.println("+Y");
			return new Vector3f(0, 1, 0); // Normal vector for +Y face
		} else if (Math.abs(a.y - min.y) < tolerance) {
			System.out.println("-Y");
			return new Vector3f(0, -1.001f, 0); // Normal vector for -Y face
		} else if (Math.abs(a.z - max.z) < tolerance) {
			System.out.println("+Z");
			return new Vector3f(0, 0, 1); // Normal vector for +Z face
		} else if (Math.abs(a.z - min.z) < tolerance) {
			System.out.println("-Z");
			return new Vector3f(0, 0, -1.001f); // Normal vector for -Z face
		}

		return null;
	}
}
