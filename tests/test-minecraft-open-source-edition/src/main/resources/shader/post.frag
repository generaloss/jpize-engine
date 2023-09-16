#version 330

in vec2 uv;

uniform sampler2D u_frame;
uniform sampler2D u_batch;

uniform vec4 u_color;

void main(){
    vec4 color = texture(u_frame, uv); // Level sampling
    vec4 batch = texture(u_batch, uv); // Cursor sampling

    if(batch.a > 0.99)
        color.rgb = 1 - color.rgb;

    const float gamma = 0.7;
    const float exposure = 1.8;
    vec3 hdrColor = color.rgb;

    // Exposure tone mapping
    vec3 mapped = vec3(1.0) - exp(-hdrColor * exposure);

    // Gamma correction
    mapped = pow(mapped, vec3(1.0 / gamma));
    color.rgb = mapped;

    // Result
    gl_FragColor = color * u_color;
}