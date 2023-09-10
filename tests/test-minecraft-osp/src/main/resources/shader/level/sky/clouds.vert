#version 330

layout (location = 0) in vec4 a_position;
layout (location = 1) in vec2 a_uv;

out vec4 fragCoord;
out vec2 uv;

uniform mat4 u_projection;
uniform mat4 u_view;

void main(){
    fragCoord = a_position;
    gl_Position = u_projection * u_view * fragCoord;
    uv = a_uv;
}
