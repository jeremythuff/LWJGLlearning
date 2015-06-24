package info.jeremy.lwjgllearning.ogltuts.entities;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import info.jeremy.lwjgllearning.graphics.Renderer;
import info.jeremy.lwjgllearning.math.Vector3f;


public class Triangle extends Renderable {
		
	private int VBO;

	
	public Triangle(Renderer renderer) {
		
		super(renderer);
		
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
