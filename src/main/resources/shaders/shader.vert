#version 330 core

layout (location = 0) in vec3 pos;
layout (location = 1) in vec2 texCoord;

out vec2 vTexCoord;
out vec3 position;

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix;

mat4 mvp;

void main() {
	mvp = projectionMatrix * modelViewMatrix;
	position = vec3(modelViewMatrix * vec4(pos, 1.0));

	gl_Position = mvp * vec4(pos, 1.0f) + vec4(0.0, 0.0, 0.0, 0.0);

	vTexCoord = texCoord;
}