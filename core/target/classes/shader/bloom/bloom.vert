#version 330

in vec3 a_pos;
in vec2 a_uv;
out vec2 uv;

void main(){
    gl_Position = vec4(a_pos, 1.0);
    uv = a_uv;
}