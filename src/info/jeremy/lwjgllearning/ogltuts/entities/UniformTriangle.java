package info.jeremy.lwjgllearning.ogltuts.entities;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import info.jeremy.lwjgllearning.graphics.Renderer;
import info.jeremy.lwjgllearning.graphics.Shader;
import info.jeremy.lwjgllearning.graphics.ShaderProgram;

public class UniformTriangle extends Renderable {
	
	private int VBO;
	private ShaderProgram shaderProgram;
	private int gScaleLocation;
	
	static float scale = 0.0f;
	
	public UniformTriangle(Renderer renderer) {
		
		super(renderer);

		Shader tri2VS = Shader.loadShader(GL_VERTEX_SHADER, "src/info/jeremy/lwjgllearning/shaders/tri2.vs");
		Shader tri2FS = Shader.loadShader(GL_FRAGMENT_SHADER, "src/info/jeremy/lwjgllearning/shaders/tri2.fs");
	
	 	shaderProgram = new ShaderProgram();
        
	 	shaderProgram.attachShader(tri2VS);
	 	shaderProgram.attachShader(tri2FS);
	 	
        shaderProgram.link();
        shaderProgram.use();
        
        
        gScaleLocation = shaderProgram.getUniformLocation("gScale"); 
		if(gScaleLocation == -1) {
        	System.err.println("No program id by the handle gScale");
        }
        
        if(gScaleLocation == -1) {
        	System.err.println("No program id by the handle gScale");
        }
        
        float[] vao = {
    			-1.0f, -1.0f, 0.0f, //left
    			1.0f, -1.0f, 0.0f, //right
    			0.0f, 1.0f, 0.0f //top
    		};
        
    		VBO = glGenBuffers();
    		glBindBuffer(GL_ARRAY_BUFFER, VBO);
    				
    		FloatBuffer ebo = BufferUtils.createFloatBuffer(vao.length);
    		
    		for(int i=0; i < vao.length; i++) {
    			ebo.put(vao[i]);
    		}
    		ebo.flip();

    		glBufferData(GL_ARRAY_BUFFER, ebo, GL_STATIC_DRAW);
    		
    		glEnableVertexAttribArray(0);
	}

	@Override
	public void render() {
		
        scale += 0.01f;
        glUniform1f(gScaleLocation, (float)Math.sin(scale));
		
		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glDrawArrays(GL_TRIANGLES, 0, 3);
				
	}

	@Override
	public void dispose() {
		glDisableVertexAttribArray(0);
	}

}
