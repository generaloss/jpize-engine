#version 330

in vec2 a_pos;
in vec2 a_uv;
in vec4 a_color;

out vec2 uv;
flat out vec4 color;

uniform mat4 u_projection;
uniform mat4 u_view;

void main(){
    vec4 pos = u_view * vec4(a_pos, 0, 1);
    gl_Position = u_projection * vec4(round(pos.xy), pos.zw);

    uv = a_uv;
    color = a_color;
}