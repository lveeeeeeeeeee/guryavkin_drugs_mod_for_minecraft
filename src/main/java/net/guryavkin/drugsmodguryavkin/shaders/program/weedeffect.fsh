#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;
in vec2 oneTexel;

uniform vec2 InSize;

uniform float Time;
uniform vec2 Frequency;
uniform vec2 WobbleAmount;

float perspective = 0.1;

const int samples = 30;
const float minBlur = 0.15;
const float maxBlur = 0.0;
const float speed = 0.5;

out vec4 fragColor;

void main() {
    vec2 p = texCoord;

    vec4 result = vec4(0);

    float timeQ = mix(minBlur, maxBlur, (cos(degrees(Time*speed)*5.0/360.0)+1.0)/2.0);

    for (int i=0; i<=samples; i++)
    {
        float q = float(i)/float(samples);
        result += texture(DiffuseSampler, p + (vec2(0.5)-p)*q*timeQ) / float(samples);
    }
    result.xyz = result.xyz * (cos(degrees(Time*speed)*5.0/360.0)+1.0) / 2.0;
    fragColor = result;
}
