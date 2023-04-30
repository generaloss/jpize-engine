#version 330

in vec4 color;
in vec2 uv;
in vec4 fragCoord;

uniform sampler2D u_atlas;
uniform float u_brightness;

void main(){
    float brightness = u_brightness * 0.1;

    vec4 fragColor = texture(u_atlas, uv) * (color * (1 - brightness) + brightness);
    if(fragColor.a <= 0)
        discard;

    gl_FragColor = fragColor;
}
