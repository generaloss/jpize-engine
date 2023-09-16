#version 330

layout (location = 0) in int a_packed_1; // used 32 / 32 bit
layout (location = 1) in int a_packed_2; // used 32 / 32 bit
layout (location = 2) in int a_packed_3; // used 12 / 32 bit

out vec4 fragCoord;
out vec2 uv;
out vec4 color;
out vec3 light;

uniform mat4 u_projection;
uniform mat4 u_view;
uniform mat4 u_model;

// ~~~~~~~~~~ PACKED VOXEL ~~~~~~~~~~ //

const int WIDTH_BITS      =  31; // [0, 16]  =>   5 bit = 0b11111     = [0, 31]
const int HEIGHT_BITS     = 511; // [0, 255] =>   9 bit = 0b111111111 = [0, 511]

const int UV_TILES_X_BITS =  63; // [0, 32]  =>   6 bit = 0b111111    = [0, 63]
const int UV_TILES_Y_BITS =  63; // [0, 32]  =>   6 bit = 0b111111    = [0, 63]
const int UV_TILES_X      =  32; // atlas width  = 32 tiles
const int UV_TILES_Y      =  32; // atlas height = 32 tiles

const int COLOR_8_BITS    = 255; // [0, 255] =>   8 bit = 0b11111111  = [0, 255]
const int COLOR_MAX_VALUE = 255; // [0, 255]->[0, 1]

const int LIGHT_BITS      =  15; // [0, 15]  =>   4 bit = 0b1111      = [0, 15]
const int LIGHT_MAX_VALUE =  15; // [0, 15]->[0, 1]

void main(){
    // Position
    int x = (a_packed_1      ) & WIDTH_BITS;
    int y = (a_packed_1 >> 5 ) & HEIGHT_BITS;
    int z = (a_packed_1 >> 14) & WIDTH_BITS;
    vec4 position = vec4(x, y, z, 1);
    fragCoord = u_model * position;
    gl_Position = u_projection * u_view * fragCoord;

    // UV
    float u = float((a_packed_1 >> 19) & UV_TILES_X_BITS) / UV_TILES_X;
    float v = float((a_packed_1 >> 25) & UV_TILES_Y_BITS) / UV_TILES_Y;
    uv = vec2(u, v);

    // Color
    float r = float((a_packed_2      ) & COLOR_8_BITS) / COLOR_MAX_VALUE;
    float g = float((a_packed_2 >> 8 ) & COLOR_8_BITS) / COLOR_MAX_VALUE;
    float b = float((a_packed_2 >> 16) & COLOR_8_BITS) / COLOR_MAX_VALUE;
    float a = float((a_packed_2 >> 24) & COLOR_8_BITS) / COLOR_MAX_VALUE;
    color = vec4(r, g, b, a);

    // AmbientOcclusion, SkyLight, BlockLight
    float ao = float((a_packed_3     ) & LIGHT_BITS) / LIGHT_MAX_VALUE;
    float sl = float((a_packed_3 >> 4) & LIGHT_BITS) / LIGHT_MAX_VALUE;
    float bl = float((a_packed_3 >> 8) & LIGHT_BITS) / LIGHT_MAX_VALUE;
    light = vec3(ao, sl, bl);
}