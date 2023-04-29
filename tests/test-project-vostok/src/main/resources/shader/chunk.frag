#version 330

in vec4 color;
in vec2 uv;
in vec4 fragCoord;

uniform sampler2D u_atlas;

void main(){
    vec4 fragColor = texture(u_atlas, uv) * color;
    if(fragColor.a <= 0)
        discard;

    gl_FragColor = fragColor;
}
