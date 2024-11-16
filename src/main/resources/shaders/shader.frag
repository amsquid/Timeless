#version 330 core

in vec2 vTexCoord;
in vec3 position;

out vec4 fragColor;

uniform sampler2D tex;

float lightEnd = 30.0f;

vec4 darkColor = vec4(1.0, 1.0, 1.0, 1.0);

void main() {
	vec4 texColor = texture(tex, vTexCoord);

	float factor = position.z / lightEnd;

	if(position.z <= lightEnd) {
		fragColor = mix(texColor, darkColor, factor);
	} else {
		fragColor = texColor;
	}
}