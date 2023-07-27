#version 330

layout (location = 0) in int a_packed_1;
layout (location = 1) in int a_packed_2;

out vec4 fragCoord;
out vec2 uv;
out vec4 color;

uniform mat4 u_projection;
uniform mat4 u_view;
uniform mat4 u_model;

const float tileWidth = 1.0 / 16;
const float tileHeight = 1.0 / 16;

void main(){
    // Unpack
    int x = (a_packed_1      ) &  31; // 5 bit - [0, 31]
    int y = (a_packed_1 >> 5 ) & 511; // 9 bit - [0, 511]
    int z = (a_packed_1 >> 14) &  31; // 5 bit - [0, 31]
    vec4 position = vec4(x, y, z, 1);

    float u = float((a_packed_1 >> 19) & 15) * tileWidth;  // 4 bit - [0, 15]
    float v = float((a_packed_1 >> 23) & 15) * tileHeight; // 4 bit - [0, 15]

    int framesNum = (a_packed_1 >> 27); // 4 bit - [0, 15] - (без & 15 т.к. это и так последние 4 бита)

    float r = float((a_packed_2      ) & 255) / 255; // 8 bit - [0, 255]
    float g = float((a_packed_2 >> 8 ) & 255) / 255; // 8 bit - [0, 255]
    float b = float((a_packed_2 >> 16) & 255) / 255; // 8 bit - [0, 255]
    float a = float((a_packed_2 >> 24) & 255) / 255; // 8 bit - [0, 255]

    // Result
    fragCoord = u_model * position;
    gl_Position = u_projection * u_view * fragCoord;

    uv = vec2(u, v);

    color = vec4(r, g, b, a);
}