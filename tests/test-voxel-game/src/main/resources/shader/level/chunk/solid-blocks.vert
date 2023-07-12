#version 330

layout (location = 0) in int a_packed1;
layout (location = 1) in int a_packed2;

out vec4 color;
out vec2 uv;
out vec4 fragCoord;

uniform mat4 u_projection;
uniform mat4 u_view;
uniform mat4 u_model;

const int atlasTilesX = 8;
const int atlasTilesY = 8;

void main(){
    // Unpack
    int x = (a_packed1      ) &  31; // 5 bit
    int y = (a_packed1 >> 5 ) & 511; // 9 bit
    int z = (a_packed1 >> 14) &  31; // 5 bit
    vec4 position = vec4(x, y, z, 1);

    int u = (a_packed1 >> 19) & 15; // 4 bit
    int v = (a_packed1 >> 23) & 15; // 4 bit

    int light = a_packed2; // 8 bit
    float brightness = float(light) / 255;

    // Position
    fragCoord = u_model * position;
    gl_Position = u_projection * u_view * fragCoord;

    // Color
    color = vec4(vec3(brightness), 1);

    // UV
    uv = vec2(float(u) / atlasTilesX, float(v) / atlasTilesY);
}