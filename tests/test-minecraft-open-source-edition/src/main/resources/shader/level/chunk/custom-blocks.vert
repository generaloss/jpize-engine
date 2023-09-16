#version 330

layout (location = 0) in vec4 a_position;
layout (location = 1) in vec2 a_uv;
layout (location = 2) in vec4 a_color;
layout (location = 3) in vec3 a_light;

out vec4 fragCoord;
out vec2 uv;
out vec4 color;
out vec3 light;

uniform mat4 u_projection;
uniform mat4 u_view;
uniform mat4 u_model;

void main(){
    fragCoord = u_model * a_position;
    gl_Position = u_projection * u_view * fragCoord;

    uv = a_uv;
    color = a_color;
    light = a_light;
}
