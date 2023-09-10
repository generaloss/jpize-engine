#version 330

in vec4 fragCoord;
in vec2 uv;

// Clouds
uniform sampler2D u_clouds;
uniform vec2 u_position;
uniform float u_skyBrightness;
// Fog
uniform int u_renderDistanceBlocks;
uniform bool u_fogEnabled;
uniform vec4 u_fogColor;
uniform float u_fogStart;

void main(){
    vec4 fragColor = texture(u_clouds, uv + u_position);
    if(fragColor.a < 0.5)
        discard;

    fragColor.rgb *= u_skyBrightness;
    fragColor.a = 0.85;

    // Fog
    if(u_fogEnabled){
        float fogMin = u_renderDistanceBlocks * u_fogStart;
        float fogMax = u_renderDistanceBlocks;
        float dist = sqrt(fragCoord.x * fragCoord.x + fragCoord.z * fragCoord.z);
        float fogFactor = 1 - (fogMax - dist) / (fogMax - fogMin);
        fragColor.a = 1 - clamp(pow(fogFactor, 1.3), 0, 1);
    }

    gl_FragColor = fragColor;
}