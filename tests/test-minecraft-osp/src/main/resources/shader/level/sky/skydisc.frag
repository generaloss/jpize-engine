#version 330

in vec4 position;

uniform vec4 u_skyColor;

void main(){
    vec4 color = u_skyColor;

    // Fog
    float hypot = sqrt(position.x * position.x + position.y * position.y + position.z * position.z);
    float angle = asin(position.y / hypot) / 3.1415926535897932384246 * 2;

    float fogMinAngle = 0;
    float fogMaxAngle = 0.2;
    float fogFactor = pow(1 - (fogMaxAngle - angle) / (fogMaxAngle - fogMinAngle), 0.5);

    color.a = fogFactor;

    // Result
    gl_FragColor = color;
}