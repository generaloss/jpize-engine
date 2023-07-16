#version 330

layout (location = 0) in vec4 a_position;

out vec4 position;

uniform mat4 u_projection;
uniform mat4 u_view;

void main(){
    gl_Position = u_projection * u_view * a_position;
    position = a_position;
}
