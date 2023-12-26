#version 330

in vec2 uv;
flat in vec4 color;
// Rect
uniform sampler2D u_texture;
uniform vec2 u_center;
uniform vec2 u_size;
// Corners
uniform float u_cornerRadius;
uniform float u_cornerSoftness;
// Borders
uniform float u_borderSize;
uniform vec4 u_borderColor;
uniform float u_borderSoftness;

float roundedBoxSDF(vec2 centerPosition, vec2 size, vec4 radius){
    radius.xy = (centerPosition.x > 0.0) ? radius.xy : radius.zw;
    radius.x  = (centerPosition.y > 0.0) ? radius.x  : radius.y;

    vec2 q = abs(centerPosition) - size + radius.x;
    return min(max(q.x, q.y), 0.0) + length(max(q, 0.0)) - radius.x;
}

void main() {
    vec4 result = color * texture2D(u_texture, uv);

    vec2 halfSize = (u_size / 2.0); // rectangle extents (half of the size)
    vec4 radius = vec4(1.0) * u_cornerRadius;

    float distance = roundedBoxSDF(gl_FragCoord.xy - u_center, halfSize, radius);
    float smoothedAlpha = 1.0 - smoothstep(0.0, u_cornerSoftness, distance); // smooth the result (free antialiasing).

    float borderAlpha = 1.0 - smoothstep(u_borderSize - u_borderSoftness, u_borderSize, abs(distance));

    result.a = min(result.a, smoothedAlpha);

    result = mix(
        result,
        u_borderColor,
        min(u_borderColor.a, min(borderAlpha, smoothedAlpha))
    );

    gl_FragColor = result;
}