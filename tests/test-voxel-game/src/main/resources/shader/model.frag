#version 330

in vec4 color;
in vec2 uv;

uniform sampler2D u_texture;

void main(){
    gl_FragColor = texture2D(u_texture, uv) * color * vec4(vec3(7.0 / 15.0), 1);
}
