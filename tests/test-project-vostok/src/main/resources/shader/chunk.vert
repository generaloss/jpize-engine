#version 330

layout (location = 0) in vec3 a_position;
layout (location = 1) in vec4 a_color;
layout (location = 2) in vec2 a_uv;

out vec4 color;
out vec2 uv;

uniform mat4 u_projection;
uniform mat4 u_view;
uniform mat4 u_model;

void main(){
    gl_Position = u_projection * u_view * u_model * vec4(a_position, 1.0);

    color = a_color;
    uv = a_uv;
}
