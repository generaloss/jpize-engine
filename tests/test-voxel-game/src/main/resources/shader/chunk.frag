#version 330

in vec4 color;
in vec2 uv;
in vec4 fragCoord;

uniform sampler2D u_atlas;
// Fog
uniform vec4 u_fogColor;
uniform int u_renderDistanceBlocks;
// Options.brightness
uniform float u_brightness;


void main(){
    float brightness = u_brightness * 0.1;
    // Sampling
    vec4 fragColor = texture(u_atlas, uv) * (color * (1 - brightness) + brightness);
    if(fragColor.a <= 0)
        discard;
    // Fog
    float fogMin = u_renderDistanceBlocks / 2;
    float fogMax = u_renderDistanceBlocks;
    float fogFactor = 1 - (fogMax - distance(vec2(0), fragCoord.xz)) / (fogMax - fogMin);
    fragColor = mix(fragColor, u_fogColor, max(min(fogFactor, 1.0), 0.0));
    // Result
    gl_FragColor = fragColor;
}
