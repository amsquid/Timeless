package me.joepeterson.Timeless.engine.texture;

import de.matthiasmann.twl.utils.PNGDecoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Texture {

	private final int id;

	private final int width;
	private final int height;

	PNGDecoder decoder;

	public Texture(String texturePath) throws IOException {
		String userDir = System.getProperty("user.dir");
		String absPath = userDir + "/" + texturePath;

		File file = new File(absPath);
		FileInputStream is = new FileInputStream(file);

		decoder = new PNGDecoder(is);

		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
		decoder.decode(byteBuffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
		width = decoder.getWidth();
		height = decoder.getHeight();
		byteBuffer.flip();

		id = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, id);
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, byteBuffer);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

		glGenerateMipmap(GL_TEXTURE_2D);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getId() {
		return id;
	}

}
