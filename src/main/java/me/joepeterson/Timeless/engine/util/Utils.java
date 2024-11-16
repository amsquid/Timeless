package me.joepeterson.Timeless.engine.util;

public class Utils {

	public static float[] appendFloatArrays(float[] a, float[] b) {
		int totalSize = a.length + b.length;
		float[] ret = new float[totalSize];

		System.arraycopy(a, 0, ret, 0, a.length);
		System.arraycopy(b, 0, ret, a.length, b.length);

		return ret;
	}

}
