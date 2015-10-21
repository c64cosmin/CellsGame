#version 400
#define PI2d16 0.616850275068084913677155

uniform float time;
uniform float radius;
uniform vec2 points[100];

in vec2 uv;

out vec4 fragColor;

float rnd(int i){
    return fract(cos(float(i)*564.5))+cos(time*0.1+20.0*float(i));
}
float map(vec2 p){
    float color = 0.0;
    for(int i=0;i<50;i++){
	vec2 v = (p - points[i])*10.0;
	if(length(v)<3.0){
  	    float c = 0.0;
	    if(length(v)<1.0)
                c = pow(1.0-PI2d16*(v.x*v.x+v.y*v.y),0.5);//1.0/(1.0+d*100.0);
	    if(c>color)color=c;
	}
    }
    return color;
//    color = clamp(0.0,1.0,color);
    return smoothstep(0.0,1.2,color);
    return 1.0-pow(1.0-color,2.0)*0.1;
}
vec3 norm(vec2 p){
    float r=0.001;
    float c=map(p);
    float x=map(p+vec2(r,0.0));
    float y=map(p+vec2(0.0,r));
    vec3 cc=vec3(p.x,  p.y,  c);
    vec3 xx=vec3(p.x+r,p.y,  x)-cc;
    vec3 yy=vec3(p.x,  p.y+r,y)-cc;
    return normalize(cross(xx,yy).xyz);
}
float light(vec3 norm){
    vec3 light=vec3(cos(time),sin(time),0.5);
    light=normalize(light);
    float lum=dot(norm,light);
    return lum*0.5+0.5;
}
vec3 tex(vec2 p){
    //return texture2D(iChannel0,norm(p).xy+p+vec2(0.5,0.5)).xyz;
    vec3 n=norm(p);
    vec2 pp;
    pp.x=atan(n.z,n.x);
    pp.y=atan(n.z,n.y);
    vec2 sp=norm(pp).xy*0.5+0.5;
    vec2 goodUV=3.0-pp;
    return vec3(0.0);//texture2D(iChannel0,goodUV*0.3).xyz;
}

void main(void)
{
    vec3 color;
    color=vec3(light(norm(uv)));
//    color=vec3(map(uv));
//    color=vec3(norm(uv));
    //color=tex(uv);
    fragColor = vec4( color, 1.0 );
}
