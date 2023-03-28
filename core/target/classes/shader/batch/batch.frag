#version 330

in vec2 uv;
flat in vec4 color;

out vec4 FragColor;

uniform sampler2D u_texture;

void main(){
    FragColor = color * texture2D(u_texture, uv);
}
