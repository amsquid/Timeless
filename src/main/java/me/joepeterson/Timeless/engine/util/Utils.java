package me.joepeterson.Timeless.engine.util;

import java.util.ArrayList;

public class Utils {

	public static float[] appendFloatArrays(float[] a, float[] b) {
		int totalSize = a.length + b.length;
		float[] ret = new float[totalSize];

		System.arraycopy(a, 0, ret, 0, a.length);
		System.arraycopy(b, 0, ret, a.length, b.length);

		return ret;
	}

	public static float[] toFloatPrimitive(ArrayList<Float> arrayList) {
		float[] ret = new float[arrayList.size()];

		for(int i = 0; i < arrayList.size(); i++) {
			ret[i] = arrayList.get(i);
		}

		return ret;
	}

	public static int[] toIntPrimitive(ArrayList<Integer> arrayList) {
		int[] ret = new int[arrayList.size()];

		for(int i = 0; i < arrayList.size(); i++) {
			ret[i] = arrayList.get(i);
		}

		return ret;
	}

}
