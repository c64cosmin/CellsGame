#version 400
#define PI2d16 0.616850275068084913677155
#define PI 3.1415926536

uniform sampler2D tex0;
uniform float time;
uniform vec2 points[100];

in vec2 uv;

out vec4 fragColor;

float rnd(int i){
    return fract(cos(float(i)*564.5))+cos(time*0.1+20.0*float(i));
}
float dist(vec2 p){
    float color = 0.0;
    for(int i=0;i<50;i++){
	float v = 1.0/(1.0+distance(p, points[i])*1.0);
	float c = 1.0-((1.0-v)*10.0);
	if(c>color)color=c;
    }
    return 1.0-color;
}
float map(vec2 p){
return dist(p);
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
float angle(vec2 p){
    vec3 v = norm(p);
    vec3 n = normalize(vec3(v.x,v.y,0.0));
    return atan(n.y,n.x);
}
float light(vec3 norm){
    vec3 light=vec3(cos(time),sin(time),0.5);
    light=normalize(light);
    float lum=dot(norm,light);
    return lum*0.5+0.5;
}
vec4 tex(vec2 p){
    vec4 result = vec4(0.0,0.0,0.0,1.0);
    float color = 0.0;
    for(int i=0;i<100;i++){
        float dist = distance(p, points[i]);
	if(dist*10.0<1.0){
            vec2 u = (p - points[i])*10.0;
            vec2 d = vec2(cos(p.x*200.0-time*10.0)+sin(p.y*200.0+time*10.0),
	                  cos(p.x*210.0+time*12.0)+sin(p.y*190.0-time*11.0))*0.01;
	    float v = 1.0/(1.0+dist);
            float c = 1.0-((1.0-v)*10.0);
	    if(c>color){
	        color=c;
	        result = texture2D(tex0,u*0.5+0.5+d);
            }
	}
    }
    return result;
}
void main(void)
{
    vec3 color;
//    color=vec3(light(norm(uv)));
//    color=vec3(map(uv));
//    color=vec3(norm(uv));
    fragColor = tex(uv);
}
