#version 330

in vec3 pos;

uniform samplerCube u_cubeMap;

void main(){
    gl_FragColor = texture(u_cubeMap, pos);
}