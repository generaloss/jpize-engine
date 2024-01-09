#version 330

in vec2 a_pos;
in vec4 a_color;

out vec4 color;

uniform mat4 u_combined;

void main(){
    gl_Position = u_combined * vec4(a_pos, 0, 1);
    color = a_color;
}