#version 330

layout (location = 0) in vec3 pos;
layout (location = 1) in vec2 texCoord;

out vec2 vTexCoord;

uniform mat4 projectionMatrix;

void main() {
	gl_Position = projectionMatrix * vec4(pos, 1.0);

	vTexCoord = texCoord;
}