#version 330

in vec2 uv;

uniform sampler2D u_frame;
uniform int u_axis;
uniform float u_radius;

void main(){
    vec2 p = uv;
    vec2 res = textureSize(u_frame, 0);

    float x,y, rr = u_radius * u_radius, d, w, w0;
    vec4 col = vec4(0.0);
    w0 = 0.5135 / pow(u_radius, 0.96);

    if(u_axis == 0)
        for(d = 1.0 / res.x, x = -u_radius, p.x += x * d; x <= u_radius;  x++, p.x += d){
            w = w0 * exp((-x * x) / (2.0 * rr));
            col += texture2D(u_frame, p) * w;
        }
    else
        for(d = 1.0 / res.y, y = -u_radius, p.y += y * d;  y <= u_radius;  y++, p.y += d){
            w = w0 * exp((-y * y) / (2.0 * rr));
            col += texture2D(u_frame, p) * w;
        }

    gl_FragColor=col;
}