package info.jeremy.lwjgllearning.ogltuts.entities;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import info.jeremy.lwjgllearning.graphics.Renderer;
import info.jeremy.lwjgllearning.math.Vector3f;


public class Dot extends Renderable {
		
	private int VBO;

	
	public Dot(Renderer renderer) {
		
		super(renderer);
		
		Vector3f vertices = new Vector3f();
		vertices.add(new Vector3f(0.0f, 0.0f, 0.0f));
		
		
		VBO = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, VBO);
				
		FloatBuffer ebo = BufferUtils.createFloatBuffer(3);
		
		ebo.put(vertices.x).put(vertices.y).put(vertices.z);
		ebo.flip();

		glBufferData(GL_ARRAY_BUFFER, ebo, GL_STATIC_DRAW);
		
		glEnableVertexAttribArray(0);
		
	}

	@Override
	public void render() {
		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glDrawArrays(GL_POINTS, 0, 1);
	}

	@Override
	public void dispose() {
		glDisableVertexAttribArray(0);
	}

}
