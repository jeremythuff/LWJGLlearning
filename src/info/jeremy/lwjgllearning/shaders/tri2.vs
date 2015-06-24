#version 330 core

layout (location = 0) in vec3 Position;

out vec4 vColor;
uniform float gScale;

void main()
{
   gl_Position = vec4(gScale * Position.x, gScale * Position.y, Position.z, 1.0);
}