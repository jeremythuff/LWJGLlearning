#version 330 core

layout (location = 0) in vec3 Position;

out vec4 vColor;

void main()
{
   gl_Position = vec4(0.25 * Position.x, 0.25 * Position.y, Position.z, 1.0);
}