#version 330 core

in vec2 vTexCoord;

out vec4 fragColor;

uniform sampler2D tex;

void main() {
	vec4 texColor = texture(tex, vTexCoord);

	fragColor = texColor;
}
