#version 330

in vec3 a_pos;

uniform mat4 u_space;
uniform mat4 u_model;

void main(){
    gl_Position = u_space * u_model * vec4(a_pos, 1.0);
}