#version 330

in vec2 uv;
flat in vec4 color;

uniform sampler2D u_texture;
uniform vec2 u_center;
uniform vec2 u_size;
uniform float u_cornerRadius;
uniform float u_borderSize;
uniform vec4 u_borderColor;

float roundedBoxSDF(vec2 centerPosition, vec2 size, vec4 radius){
    radius.xy = (centerPosition.x > 0.0) ? radius.xy : radius.zw;
    radius.x  = (centerPosition.y > 0.0) ? radius.x  : radius.y;

    vec2 q = abs(centerPosition) - size + radius.x;
    return min(max(q.x, q.y), 0.0) + length(max(q, 0.0)) - radius.x;
}


void main() {
    vec4 result = color * texture2D(u_texture, uv);


    float softness = 0.5;

    vec2 halfSize = (u_size / 2.0); // Rectangle extents (half of the size)
    vec4 radius = vec4(1.0) * u_cornerRadius;

    float distance = roundedBoxSDF(gl_FragCoord.xy - u_center, halfSize, radius);
    float smoothedAlpha = 1.0 - smoothstep(0.0, softness, distance); // smooth the result (free antialiasing).

    float borderAlpha   = 1.0 - smoothstep(u_borderSize - softness, u_borderSize, abs(distance));

    // vec4 res_shadow_color = mix(u_colorBg, vec4(u_colorShadow.rgb, shadowAlpha), shadowAlpha);

    // Blend (background+shadow) with rect
    //   Note:
    //     - Used 'min(u_colorRect.a, smoothedAlpha)' instead of 'smoothedAlpha'
    //       to enable rectangle color transparency
    result.a = min(result.a, smoothedAlpha);

    // Blend (background+shadow+rect) with border
    //   Note:
    //     - Used 'min(borderAlpha, smoothedAlpha)' instead of 'borderAlpha'
    //       to make border 'internal'
    //     - Used 'min(u_colorBorder.a, alpha)' instead of 'alpha' to enable
    //       border color transparency
    result =
    mix(
        result, // res_shadow_with_rect_color
        u_borderColor,
        min(u_borderColor.a, min(borderAlpha, smoothedAlpha))
    );

    gl_FragColor = result;
}