#version 330

in vec4 color;
in vec2 uv;

uniform sampler2D u_texture;

void main(){
    vec4 color = texture(u_texture, uv) * color;
    if(color.a <= 0)
        discard;

    gl_FragColor = color;
}
