package me.joepeterson.Timeless.engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public class Shader {

	private final int id;

	private int vertexId;
	private int fragmentId;

	private final Map<String, Integer> uniforms;

	public Shader() {
		id = glCreateProgram();

		uniforms = new HashMap<>();
	}

	public void createVertexShader(String source) throws Exception {
		vertexId = createShader(source, GL_VERTEX_SHADER);
	}

	public void createFragmentShader(String source) throws Exception {
		fragmentId = createShader(source, GL_FRAGMENT_SHADER);
	}

	protected int createShader(String source, int type) throws Exception {
		int id = glCreateShader(type);

		if(id == 0) {
			throw new Exception("Error creating shader. Type: " + type);
		}

		glShaderSource(id, source);
		glCompileShader(id);

		if(glGetShaderi(id, GL_COMPILE_STATUS) == 0) {
			throw new Exception("Error compiling shader code: " + glGetShaderInfoLog(id));
		}

		glAttachShader(this.id, id);

		return id;
	}

	public void link() throws Exception {
		glLinkProgram(this.id);

		if(glGetProgrami(this.id, GL_LINK_STATUS) == 0) {
			throw new Exception("Error linking shader code: " + glGetProgramInfoLog(this.id));
		}

		if(vertexId != 0) {
			glDetachShader(this.id, vertexId);
		}

		if(fragmentId != 0) {
			glDetachShader(this.id, fragmentId);
		}

		glValidateProgram(this.id);

		if(glGetProgrami(this.id, GL_VALIDATE_STATUS) == 0) {
			System.err.println("Warning validating shader code: " + glGetProgramInfoLog(this.id));
		}
	}

	public void bind() {
		glUseProgram(this.id);
	}

	public void unbind() {
		glUseProgram(0);
	}

	public void createUniform(String uniformName) throws Exception {
		int uniformLocation = glGetUniformLocation(this.id, uniformName);

		if(uniformLocation < 0) {
			throw new Exception("Could not find uniform " + uniformName);
		}

		uniforms.put(uniformName, uniformLocation);
	}

	public void setUniform(String uniformName, Matrix4f value) {
		try(MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer fb = stack.mallocFloat(16);
			value.get(fb);
			glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
		}
	}

	public void setUniform(String uniformName, int value) {
		glUniform1i(uniforms.get(uniformName), value);
	}

	public void setUniform(String uniformName, Vector3f value) {
		glUniform3f(uniforms.get(uniformName), value.x, value.y, value.z);
	}

	public void cleanup() {
		unbind();

		if(this.id != 0) {
			glDeleteProgram(this.id);
		}
	}
}
