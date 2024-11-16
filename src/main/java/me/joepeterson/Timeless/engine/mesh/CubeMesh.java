package me.joepeterson.Timeless.engine.mesh;

import me.joepeterson.Timeless.engine.block.BlockFace;
import me.joepeterson.Timeless.engine.texture.Texture;

import java.util.ArrayList;
import java.util.List;

public class CubeMesh extends BaseMesh {

	static float[] vertices = new float[] {
			// V0
			0.0f, 1.0f, 1.0f,
			// V1
			0.0f, 0.0f, 1.0f,
			// V2
			1.0f, 0.0f, 1.0f,
			// V3
			1.0f, 1.0f, 1.0f,
			// V4
			0.0f, 1.0f, 0.0f,
			// V5
			1.0f, 1.0f, 0.0f,
			// V6
			0.0f, 0.0f, 0.0f,
			// V7
			1.0f, 0.0f, 0.0f,

			// For tex coords in top face
			// V8: V4 repeated
			0.0f, 1.0f, 0.0f,
			// V9: V5 repeated
			1.0f, 1.0f, 0.0f,
			// V10: V0 repeated
			0.0f, 1.0f, 1.0f,
			// V11: V3 repeated
			1.0f, 1.0f, 1.0f,

			// For tex coords in right face
			// V12: V3 repeated
			1.0f, 1.0f, 1.0f,
			// V13: V2 repeated
			1.0f, 0.0f, 1.0f,

			// For tex coords in left face
			// V14: V0 repeated
			0.0f, 1.0f, 1.0f,
			// V15: V1 repeated
			0.0f, 0.0f, 1.0f,

			// For tex coords in bottom face
			// V16: V6 repeated
			0.0f, 0.0f, 0.0f,
			// V17: V7 repeated
			1.0f, 0.0f, 0.0f,
			// V18: V1 repeated
			0.0f, 0.0f, 1.0f,
			// V19: V2 repeated
			1.0f, 0.0f, 1.0f,
	};

	static float[] texCoords = new float[]{
			0.0f, 0.0f,
			0.0f, 0.5f,
			0.5f, 0.5f,
			0.5f, 0.0f,

			0.0f, 0.0f,
			0.5f, 0.0f,
			0.0f, 0.5f,
			0.5f, 0.5f,

			0.0f, 0.5f,
			0.5f, 0.5f,
			0.0f, 1.0f,
			0.5f, 1.0f,

			0.0f, 0.0f,
			0.0f, 0.5f,

			0.5f, 0.0f,
			0.5f, 0.5f,

			0.5f, 0.0f,
			1.0f, 0.0f,
			0.5f, 0.5f,
			1.0f, 0.5f
	};

	static int[] startIndices = new int[] {
			// Front Face
			0, 1, 3, 3, 1, 2,
			// Top Face
			8, 10, 11, 9, 8, 11,
			// Right face
			12, 13, 7, 5, 12, 7,
			// Left face
			6, 15, 14, 6, 14, 4,
			// Bottom face
			19, 18, 16, 19, 16, 17,
			// Back face
			7, 6, 4, 7, 4, 5,
	};

	int[] indices = startIndices;

	public boolean visible = true;

	ArrayList<BlockFace> facesVisible = new ArrayList<>();

	public CubeMesh(Texture texture) {
		super(
				vertices,
				texCoords,
				startIndices,
				texture
		);

		resetIndicesNoUpdate();
	}

	public void resetCubeIndices() {
		indices = startIndices;

		updateIndices();

	}

	public void deleteCubeIndices(BlockFace face) {
		facesVisible.remove(face);
	}

	public void updateIndices() {
		List<Integer> newIndicesArray = new ArrayList<>();
		int[] newIndices = new int[0];

		for(BlockFace face : facesVisible) {
			int start = switch (face) {
				case BlockFace.FRONT -> 0;
				case BlockFace.TOP -> 6;
				case BlockFace.RIGHT -> 12;
				case BlockFace.LEFT -> 18;
				case BlockFace.BOTTOM -> 24;
				case BlockFace.BACK -> 30;
			};

			int end = start + 6;

			for(int i = 0; i < startIndices.length; i++) {
				if(i >= start && i < end) newIndicesArray.add(startIndices[i]);
			}

			newIndices = new int[newIndicesArray.size()];

			for(int i = 0; i < newIndicesArray.size(); i++) {
				Integer index = newIndicesArray.get(i);

				newIndices[i] = index;
			}
		}

		indices = newIndices;

		updateBuffers(true, vertices, texCoords, indices, texture);
	}

	public void resetIndices() {
		resetIndicesNoUpdate();

		updateBuffers(true, vertices, texCoords, indices, texture);
	}

	public void resetIndicesNoUpdate() {
		indices = startIndices;

		facesVisible.clear();
		facesVisible.add(BlockFace.FRONT);
		facesVisible.add(BlockFace.TOP);
		facesVisible.add(BlockFace.RIGHT);
		facesVisible.add(BlockFace.LEFT);
		facesVisible.add(BlockFace.BOTTOM);
		facesVisible.add(BlockFace.BACK);
	}
}
