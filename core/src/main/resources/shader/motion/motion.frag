#version 330

in vec2 uv;
uniform sampler2D u_frame;
uniform sampler2D u_backFrame;

void main(){
    gl_FragColor = mix( texture2D(u_frame, uv), texture2D(u_backFrame, uv), 0.6 );
}