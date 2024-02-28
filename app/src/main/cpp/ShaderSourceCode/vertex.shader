#version 330 es
layout(location = 0) in vec3 pos;
layout(location = 1) in vec4 color;

out vec4 fragColor;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;
uniform mat4 camera;

void main() {
    gl_Position = vec4(pos, 1.0);
    fragColor = color;
}