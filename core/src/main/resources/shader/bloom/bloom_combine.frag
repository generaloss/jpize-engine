#version 330

in vec2 uv;
uniform sampler2D u_frame1;
uniform sampler2D u_frame2;

uniform float u_bloom;
uniform float u_exposure;
uniform float u_gamma;

void main(){
    vec4 color = texture(u_frame1, uv);
    color.rgb += texture(u_frame2, uv).rgb * u_bloom;

    // Tone Mapping
    color.rgb = vec3(1) - exp( -color.rgb * u_exposure);

    // Gamma Correction
    color.rgb = pow(color.rgb, vec3(1.0 / u_gamma));

    gl_FragColor = color;
}