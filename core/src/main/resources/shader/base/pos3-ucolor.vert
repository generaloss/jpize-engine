#version 330

in vec3 a_pos;

uniform mat4 u_combined;

void main(){
    gl_Position = u_combined * vec4(a_pos, 1);
}