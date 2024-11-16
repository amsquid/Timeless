package me.joepeterson.Timeless.entities;

import me.joepeterson.Timeless.engine.texture.Texture;
import me.joepeterson.Timeless.engine.entity.MeshEntity;
import me.joepeterson.Timeless.engine.mesh.CubeMesh;

public class CubeEntity extends MeshEntity {

	public CubeEntity(Texture texture) {
		super(new CubeMesh(texture));

		setRotation(0.0f, 0.0f, 0.0f);
	}

}
