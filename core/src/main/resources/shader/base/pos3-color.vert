#version 330

in vec3 a_pos;
in vec4 a_color;

out vec4 color;

uniform mat4 u_projection;
uniform mat4 u_view;

void main(){
    gl_Position = u_projection * u_view * vec4(a_pos, 1);
    color = a_color;
}