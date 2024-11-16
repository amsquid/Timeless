package me.joepeterson.Timeless.engine.block;

import me.joepeterson.Timeless.engine.BoundingBox;
import me.joepeterson.Timeless.engine.mesh.CubeMesh;
import me.joepeterson.Timeless.engine.texture.Texture;
import me.joepeterson.Timeless.engine.util.Vector;
import org.joml.*;

public class Block {

	public Vector3i position;

	public int hardness = 0;

	public CubeMesh mesh;

	public final BoundingBox boundingBox;

	public Block(Vector3i position, Texture texture) {
		mesh = new CubeMesh(texture);

		this.position = position;

		boundingBox = new BoundingBox(Vector.toVector3f(this.position), Vector.addVectors(this.position, new Vector3f(1, 1, 1)));
	}

	public void update() {

	}

	public void render() {
		mesh.render();
	}

	public Matrix4f getMatrix(Matrix4f viewMatrix) {
		Matrix4f worldMatrix = new Matrix4f();

		worldMatrix.identity().translate(new Vector3f(
				position.x,
				position.y,
				position.z
		));

		Matrix4f viewCurr = new Matrix4f(viewMatrix);

		return viewCurr.mul(worldMatrix);
	}

	public void cleanup() {
		mesh.cleanup();
	}

}
