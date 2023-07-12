#version 330

in vec4 color;
in vec2 uv;
in vec4 fragCoord;

uniform sampler2D u_atlas;
// Fog
uniform int u_renderDistanceBlocks;
uniform vec4 u_fogColor;
// Options.brightness
uniform float u_brightness;


void main(){
    float brightness = u_brightness * 0.1;
    // Sampling
    vec4 fragColor = (color * (1 - brightness) + brightness) * texture(u_atlas, uv);
    if(fragColor.a <= 0)
        discard;
    // Fog
    float fogMin = u_renderDistanceBlocks / 4 * 3;
    float fogMax = u_renderDistanceBlocks;
    float dist = sqrt(fragCoord.x * fragCoord.x + fragCoord.z * fragCoord.z);
    float fogFactor = 1 - (fogMax - dist) / (fogMax - fogMin);
    fragColor = mix(fragColor, u_fogColor, max(min(fogFactor, 1.0), 0.0));
    // Result
    gl_FragColor = fragColor;
}
