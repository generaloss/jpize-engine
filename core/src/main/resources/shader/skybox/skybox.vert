#version 330

in vec3 a_pos;

out vec3 pos;

uniform mat4 u_projection;
uniform mat4 u_view;

void main(){
    gl_Position = u_projection * u_view * vec4(a_pos, 1.0);
    pos = a_pos;
}