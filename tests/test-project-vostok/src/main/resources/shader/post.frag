#version 330

in vec2 uv;

uniform sampler2D u_frame;
uniform sampler2D u_batch;

void main(){
    vec4 color = texture2D(u_frame, uv); // World sampling
    vec4 batch = texture2D(u_batch, uv); // Cursor sampling

    if(batch.a > 0.5)
        color.rgb = 1 - color.rgb;

    const float gamma = 0.7;
    const float exposure = 2;
    vec3 hdrColor = color.rgb;
    // exposure tone mapping
    vec3 mapped = vec3(1.0) - exp(-hdrColor * exposure);
    // gamma correction
    mapped = pow(mapped, vec3(1.0 / gamma));
    color.rgb = mapped;

    gl_FragColor = color;
}