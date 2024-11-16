package me.joepeterson.Timeless.engine.texture;

import java.io.IOException;

public class TextureAtlas extends Texture {

	public int textureSize = 1;

	public TextureAtlas(String texturePath, int textureSize) throws IOException {
		super(texturePath);

		this.textureSize = textureSize;
	}

	public float[] getTexCoords(int x, int y) {
		int width = getWidth();
		int height = getHeight();

		float colSize = (float) textureSize / width;
		float rowSize = (float) textureSize / height;

		float[] texCoords = new float[8];

		float originX = x * colSize;
		float originY = y * rowSize;

		// V0
		texCoords[0] = originX + colSize;
		texCoords[1] = originY;
		// V1
		texCoords[2] = originX + colSize;
		texCoords[3] = originY + rowSize;
		// V2
		texCoords[4] = originX;
		texCoords[5] = originY + rowSize;
		// V3
		texCoords[6] = originX;
		texCoords[7] = originY;

		System.out.println(originX);

		return texCoords;
	}

}
