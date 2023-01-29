#version 150

#ifdef GL_ES
precision mediump float;
#endif

uniform float Time;
uniform sampler2D DiffuseSampler;

in vec2 texCoord;
in vec2 oneTexel;

out vec4 fragColor;

vec3 pal( in float t, in vec3 a, in vec3 b, in vec3 c, in vec3 d )
{
    return a + b*cos( 6.28318*(c*t+d) );
}

void main() {
    vec4 origColor = texture(DiffuseSampler, texCoord);

    float catx = texCoord.x;

    float caty = texCoord.y;

    float hyp = sqrt(pow(catx, 2.0) + pow(caty, 2.0));

    float rotAmp = degrees(Time/hyp);

    mat2 rotate = mat2(sin(rotAmp), cos(rotAmp), -cos(rotAmp), sin(rotAmp));

    vec2 p = texCoord.xy / oneTexel.xy * rotate;

    p.x *= oneTexel.x/oneTexel.y;

    vec3 col = pal( p.x+p.y, vec3(abs(0.5*sin(Time)),0.3,0.3),vec3(0.4,abs(0.5*sin(Time-0.7587)),0.2),vec3(abs(0.5*sin(2.0*Time-0.7587)),0.3,abs(0.8*sin(0.1*Time-0.7587))),vec3(0.0,abs(0.5*cos(1.5*Time-0.7587)),0.1) );

    col *= 0.2*abs(sin(degrees(Time/(360.0*4.0))));

    fragColor.r = mix(col.r, origColor.r, 0.1);
    fragColor.g = mix(col.g, origColor.g, 0.1);
    fragColor.b = mix(col.b, origColor.b, 0.1);
    fragColor.a = 1.0;
}