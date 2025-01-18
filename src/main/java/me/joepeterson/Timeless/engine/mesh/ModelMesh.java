package me.joepeterson.Timeless.engine.mesh;

import me.joepeterson.Timeless.engine.texture.Texture;
import me.joepeterson.Timeless.engine.util.Utils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class ModelMesh extends BaseMesh {

	ArrayList<Float> verticesArray = new ArrayList<>();
	ArrayList<Float> texCoordsArray = new ArrayList<>();
	ArrayList<Integer> indicesArray = new ArrayList<>();

	Texture texture;

	String modelPath;

	public ModelMesh(String modelPath, Texture texture) throws Exception {
		super();

		String userDir = System.getProperty("user.dir");
		this.modelPath = userDir + "/" + modelPath;

		System.out.println(this.modelPath);

		loadModelFile(this.modelPath);

		updateBuffers(
				false,
				Utils.toFloatPrimitive(verticesArray),
				Utils.toFloatPrimitive(texCoordsArray),
				Utils.toIntPrimitive(indicesArray),
				texture
		);
	}

	private void loadModelFile(String path) {
		AIScene scene = Assimp.aiImportFile(path, Assimp.aiProcess_Triangulate);

		assert scene != null;
		PointerBuffer buffer = scene.mMeshes();

		assert buffer != null;
		for(int i = 0; i < buffer.limit(); i++) {
			AIMesh meshObject = AIMesh.create(buffer.get(i));

			processMesh(meshObject);
		}
	}

	private void processMesh(AIMesh meshObject) {
		AIVector3D.Buffer verticesBuf = meshObject.mVertices();
		for(int i = 0; i < verticesBuf.limit(); i++) {
			AIVector3D vector = verticesBuf.get(i);

			verticesArray.add(vector.x());
			verticesArray.add(vector.y());
			verticesArray.add(vector.z());

			indicesArray.add(i);
		}

		AIVector3D.Buffer texCoordsBuf = meshObject.mTextureCoords(0);
		for(int i = 0; i < texCoordsBuf.limit(); i++) {
			AIVector3D texCoord = texCoordsBuf.get(i);

			texCoordsArray.add(texCoord.x());
			texCoordsArray.add(texCoord.y());
		}
	}
}
