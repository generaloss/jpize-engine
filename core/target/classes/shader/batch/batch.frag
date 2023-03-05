#version 400

in vec2 uv;
flat in vec4 color;
flat in int texIndex;

out vec4 fragColor;

uniform sampler2D u_textures[TEX_SLOTS];

void main(){
    fragColor = color * texture2D(u_textures[texIndex], uv);
}