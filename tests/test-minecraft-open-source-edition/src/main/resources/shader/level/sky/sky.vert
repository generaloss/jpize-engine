#version 330

layout (location = 0) in vec4 a_position;

out vec4 fragCoord;

uniform mat4 u_projection;
uniform mat4 u_view;

void main(){
    fragCoord = a_position;
    gl_Position = u_projection * u_view * a_position;
}
