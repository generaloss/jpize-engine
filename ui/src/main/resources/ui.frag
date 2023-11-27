#version 330

in vec2 uv;
flat in vec4 color;

uniform sampler2D u_texture;
uniform vec2 u_pos;
uniform vec2 u_size;
uniform float u_cornerRadius;

float roundedBoxSDF(vec2 center, vec2 size){
    return length(max(abs(center) - size + u_cornerRadius, 0.0)) - u_cornerRadius;
}

void main() {
    vec4 result = color * texture2D(u_texture, uv);

    float distance = roundedBoxSDF(gl_FragCoord.xy - u_pos - (u_size / 2.0f), u_size / 2.0f);
    float edgeSoftness = 0.5;
    result.a = 1.0f - smoothstep(0.0f, edgeSoftness * 2.0f, distance);

    gl_FragColor = result;
}