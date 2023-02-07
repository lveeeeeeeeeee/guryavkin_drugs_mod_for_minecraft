#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;
in vec2 oneTexel;

uniform vec2 InSize;

uniform float Time;
uniform vec2 Frequency;
uniform vec2 WobbleAmount;

out vec4 fragColor;

vec3 hsv(in float h, in float s, in float v)
{
    return mix(vec3(1.0), clamp((abs(fract(h + vec3(3, 2, 1) / 3.0) * 6.0 - 3.0) - 1.0), 0.0 , 1.0), s) * v;
}

vec3 formula(in vec2 p, in vec2 c)
{
    const float n = 2.0;
    const int iters = 2;

    float time = Time*0.5;
    vec3 col = vec3(0);
    float t = 8.0;
    float dpp = dot(p, p);
    float lp = sqrt(dpp);

    for (int i = 0; i < iters; i++) {
        // The transformation
        p = abs(mod(p/dpp + c, n) - n/2.7);

        dpp = dot(p, p);
        lp = sqrt(dpp);

        // Add to colour using hsv
        col += hsv(1.0 - max(p.x, p.y) + t*2.0 + time, 2.0-lp+t, 1.5*sin(Time*0.5));

    }

    return (-cos(col/4.0)*0.5 + 0.5)*(t);
}

void main() {
    vec4 rgb = texture(DiffuseSampler, texCoord);

    //calc the trip pixel
    vec2 p = -1.0 + 2.0 * texCoord.xy;
    p *= 2.0;
    const vec2 e = vec2(0.06545465634, -0.05346356485);
    vec2 c = Time*e;
    float d = 1.0;
    vec3 col = vec3(0.0);
    const float blursamples = 4.0;
    float sbs = sqrt(blursamples);
    float mbluramount = 1.0/oneTexel.x/length(e)/blursamples*2.0;
    float aabluramount = 1.0/oneTexel.x/sbs*4.0;
    for (float b = 0.0; b < blursamples; b++) {
        col += formula(
        p + vec2(mod(b, sbs)*aabluramount, b/sbs*aabluramount),
        c + e*mbluramount*b);
    }
    col /= blursamples;

    col = col * abs(sin(Time*20.0/360.0));

    rgb.xyz = rgb.xyz - (rgb.xyz * 0.2 * sin(Time*20.0/360.0));

    col = (col * 0.05) + rgb.xyz;

    fragColor = vec4(col, 1.0);
}
