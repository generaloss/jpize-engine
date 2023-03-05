#version 330

in vec2 uv;
flat in vec4 color;

out vec4 fragColor;

uniform sampler2D u_texture;

void main(){
    fragColor = color * texture2D(u_texture, uv);
}
