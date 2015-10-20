#version 400

uniform vec4 resolution;
in vec2 xy;

out vec2 uv;

void main(void){
    uv=vec2(xy);
    gl_Position=vec4(xy.x * (resolution.y/resolution.x), xy.y, 0.0, 1.0);
}
