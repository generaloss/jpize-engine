#version 330

in vec2 uv;
in vec4 color;

uniform sampler2D u_texture;

void main(){
    gl_FragColor = texture2D(u_texture, uv) * color;
}