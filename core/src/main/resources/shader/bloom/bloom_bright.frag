#version 330

in vec2 uv;
uniform sampler2D u_frame;

uniform float u_brightness;

void main(){
    vec4 color = texture(u_frame, uv);
    float brightness = dot(color.rgb, vec3(0.2126, 0.7152, 0.0722));

    if(brightness > u_brightness)
        gl_FragColor = color;
    else
        gl_FragColor = vec4(0,0,0,1);
}