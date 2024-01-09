#version 330

in vec2 a_pos;
in vec2 a_uv;
in vec4 a_color;

out vec2 uv;
out vec4 color;

uniform mat4 u_combined;

void main(){
    gl_Position = u_combined * vec4(a_pos, 0, 1);
    uv = a_uv;
    color = a_color;
}