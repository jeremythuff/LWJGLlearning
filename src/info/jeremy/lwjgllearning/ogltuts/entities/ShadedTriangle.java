package info.jeremy.lwjgllearning.ogltuts.entities;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import info.jeremy.lwjgllearning.graphics.Shader;
import info.jeremy.lwjgllearning.graphics.ShaderProgram;

public class ShadedTriangle extends Renderable {
	
	private int VBO;
	private ShaderProgram shaderProgram;
	
	public ShadedTriangle() {

		Shader triVS = Shader.loadShader(GL_VERTEX_SHADER, "src/info/jeremy/lwjgllearning/shaders/tri.vs");
		Shader triFS = Shader.loadShader(GL_FRAGMENT_SHADER, "src/info/jeremy/lwjgllearning/shaders/tri.fs");
	
	 	shaderProgram = new ShaderProgram();
        
	 	shaderProgram.attachShader(triVS);
	 	shaderProgram.attachShader(triFS);
	 	
        shaderProgram.link();
        shaderProgram.use();
        
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
		
		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glDrawArrays(GL_TRIANGLES, 0, 3);
				
	}

	@Override
	public void dispose() {
		glDisableVertexAttribArray(0);
	}
}
