#version 330

in vec2 uv;
flat in vec4 color;

uniform sampler2D u_texture;

void main(){
    gl_FragColor = color * texture2D(u_texture, uv);
}
