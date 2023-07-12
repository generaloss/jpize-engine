#version 330

layout (location = 0) in vec4 a_position;
layout (location = 1) in vec4 a_color;

out vec4 color;

uniform mat4 u_projection;
uniform mat4 u_view;

void main(){
    gl_Position = u_projection * u_view * a_position;

    color = a_color;
}
