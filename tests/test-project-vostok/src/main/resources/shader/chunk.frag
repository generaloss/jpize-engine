#version 330

in vec4 color;
centroid in vec2 uv;

uniform sampler2D u_atlas;

void main(){
    vec4 color = texture2D(u_atlas, uv) * color;
    gl_FragColor = color;
}
