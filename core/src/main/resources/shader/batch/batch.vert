#version 400

in vec2 a_pos;
in vec2 a_uv;
in vec4 a_color;
in float a_tex;

out vec2 uv;
flat out vec4 color;
flat out int texIndex;

uniform mat4 u_projection;
uniform mat4 u_view;

void main(){
    vec4 pos = u_view * vec4(a_pos, 0, 1);
    gl_Position = u_projection * POS_FUNC;

    uv = a_uv;
    color = a_color;
    texIndex = int(a_tex);
}