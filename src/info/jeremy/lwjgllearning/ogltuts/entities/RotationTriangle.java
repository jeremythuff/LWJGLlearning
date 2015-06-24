package info.jeremy.lwjgllearning.ogltuts.entities;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import info.jeremy.lwjgllearning.graphics.Renderer;
import info.jeremy.lwjgllearning.graphics.Shader;
import info.jeremy.lwjgllearning.graphics.ShaderProgram;

public class RotationTriangle extends Renderable {
	
	
	
	private int VBO;
	private ShaderProgram shaderProgram;
	private int gWorldLocation;
	
	static float scale = 0.0f;

		
	public RotationTriangle(Renderer renderer) {
		
		super(renderer);
		
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		float[] vao = {
			-0.5f, -0.5f, 0.0f, //left
			0.5f, -0.5f, 0.0f, //right
			0.0f, 0.5f, 0.0f //top
		};
		
		FloatBuffer ebo = BufferUtils.createFloatBuffer(vao.length);
		
		for(int i=0; i < vao.length; i++) {
			ebo.put(vao[i]);
		}
		ebo.flip();
		
		VBO = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glBufferData(GL_ARRAY_BUFFER, ebo, GL_STATIC_DRAW);
		glEnableVertexAttribArray(0);
		
		Shader tri3VS = Shader.loadShader(GL_VERTEX_SHADER, "src/info/jeremy/lwjgllearning/shaders/tri3.vs");
		Shader tri3FS = Shader.loadShader(GL_FRAGMENT_SHADER, "src/info/jeremy/lwjgllearning/shaders/tri3.fs");
	
	 	shaderProgram = new ShaderProgram();
        
	 	shaderProgram.attachShader(tri3VS);
	 	shaderProgram.attachShader(tri3FS);
	 	
        shaderProgram.link();
        shaderProgram.use();
        
	}

	@Override
	public void render() {
		
		glClear(GL_COLOR_BUFFER_BIT);
		
		gWorldLocation = shaderProgram.getUniformLocation("gWorld"); 
		if(gWorldLocation == -1) {
        	System.err.println("No program id by the handle gWorld");
        }
		
        scale += 0.01f;
        
        float sinScale = (float)Math.sin(scale);
        float cosScale = (float)Math.cos(scale);
        
        float[] vao = {
        	cosScale, -sinScale, 0.0f, 0.0f, 
			sinScale, cosScale, 0.0f, 0.0f, 
			0.0f, 0.0f, 1.0f, 0.0f, 
			0.0f, 0.0f, 0.0f, 1.0f 
		};
   
		glBindBuffer(GL_ARRAY_BUFFER, VBO);
				
		FloatBuffer ebo = BufferUtils.createFloatBuffer(vao.length);
		
		for(int i=0; i < vao.length; i++) {
			ebo.put(vao[i]);
		}
		ebo.flip();
		
        
        glUniformMatrix4fv(gWorldLocation, true, ebo);
		
        glEnableVertexAttribArray(0);
		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glDrawArrays(GL_TRIANGLES, 0, 3);
		glDisableVertexAttribArray(0);
		
		
	}

	@Override
	public void dispose() {
	
	}

}
