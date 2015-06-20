package info.jeremy.voxelengine;

import info.jeremy.voxelengine.io.Keyboard;
import info.jeremy.voxelengine.io.Window;
import info.jeremy.voxelengine.math.Vector3f;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL13.*;

public class Main {
    
	
	
	static IntBuffer cubeTextureIDs = BufferUtils.createIntBuffer(6);
	static float xRotation = 0f;
	static float yRotation = 0f;
	
	private GLFWErrorCallback errorCallback;
	
	private GLFWKeyCallback keyCallback;
	
	Window window;
	
	public boolean running;
	
	public float angle = 0.0f;
	
	private void init() {
		errorCallback = Callbacks.errorCallbackPrint(System.err);
		glfwSetErrorCallback(errorCallback);
		
		int glfwResult = glfwInit();
		
		if(glfwResult == GL_FALSE)
			throw new IllegalStateException("GLFW failed to initialize");
		
		window = new Window("Voxel Engine");
		
		glfwSetKeyCallback(window.id, new Keyboard());
		
		glfwMakeContextCurrent(window.id);
		glfwShowWindow(window.id);
		GLContext.createFromCurrent();
		
 		glEnable(GL_DEPTH_TEST);
		glActiveTexture(GL_TEXTURE1);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		System.out.println("OpenGL: " + glGetString(GL_VERSION));
		
		glfwMakeContextCurrent(window.id);
		glfwShowWindow(window.id);
		
		GLContext.createFromCurrent();
		
	}
	
	private void loop() {
		while(running) {
			
			update();
			render();
			
			if (glfwWindowShouldClose(window.id) == GL_TRUE)
				running = false;

		}
	}
	
	private void update() {
		glfwPollEvents();
		
		if(Keyboard.isKeyDown(GLFW_KEY_UP)) {
			angle += 0.1;
		}
		
		if(Keyboard.isKeyDown(GLFW_KEY_DOWN)) {
			angle -= 0.1;
		}
		
		if(Keyboard.isKeyDown(GLFW_KEY_ESCAPE)) {
			running = false;
		}
	}
	
	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	      
		//drawVertexBufferObjectIndexed();
		//drawQuad();
		drawCube(angle);
		
		
		glfwSwapBuffers(window.id);
	}
	
	
	static void drawCube(Float angle) {
		// Clear The Screen And The Depth Buffer
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
				// R,G,B,A Set The Color To Blue One Time Only
				GL11.glColor3f(0.5f, 0.5f, 1.0f);
				
				// draw quad
				GL11.glPushMatrix();
				GL11.glRotatef(xRotation, 1, 0, 0);
				GL11.glRotatef(yRotation, 0, 1, 0);
				
				// top
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, cubeTextureIDs.get(0));
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0, 0); GL11.glVertex3f(-1, -1, 1);
				GL11.glTexCoord2f(0, 1); GL11.glVertex3f(1, -1, 1);
				GL11.glTexCoord2f(1, 1); GL11.glVertex3f(1, 1, 1);
				GL11.glTexCoord2f(1, 0); GL11.glVertex3f(-1, 1, 1);
				GL11.glEnd();
				//left
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, cubeTextureIDs.get(1));
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0, 0); GL11.glVertex3f(-1, -1, -1);
				GL11.glTexCoord2f(0, 1); GL11.glVertex3f(-1, -1, 1);
				GL11.glTexCoord2f(1, 1); GL11.glVertex3f(-1, 1, 1);
				GL11.glTexCoord2f(1, 0); GL11.glVertex3f(-1, 1, -1);
				GL11.glEnd();
				//right
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, cubeTextureIDs.get(2));
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0, 0); GL11.glVertex3f(1, -1, -1);
				GL11.glTexCoord2f(0, 1); GL11.glVertex3f(1, 1, -1);
				GL11.glTexCoord2f(1, 1); GL11.glVertex3f(1, 1, 1);
				GL11.glTexCoord2f(1, 0); GL11.glVertex3f(1, -1, 1);
				GL11.glEnd();
				//front
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, cubeTextureIDs.get(3));
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0, 0); GL11.glVertex3f(-1, -1, -1);
				GL11.glTexCoord2f(0, 1); GL11.glVertex3f(1, -1, -1);
				GL11.glTexCoord2f(1, 1); GL11.glVertex3f(1, -1, 1);
				GL11.glTexCoord2f(1, 0); GL11.glVertex3f(-1, -1, 1);
				GL11.glEnd();
				//back
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, cubeTextureIDs.get(4));
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0, 0); GL11.glVertex3f(1, 1, -1);
				GL11.glTexCoord2f(0, 1); GL11.glVertex3f(-1, 1, -1);
				GL11.glTexCoord2f(1, 1); GL11.glVertex3f(-1, 1, 1);
				GL11.glTexCoord2f(1, 0); GL11.glVertex3f(1, 1, 1);
				GL11.glEnd();
				//bottom
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, cubeTextureIDs.get(5));
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0, 0); GL11.glVertex3f(1, -1, -1);
				GL11.glTexCoord2f(0, 1); GL11.glVertex3f(-1, -1, -1);
				GL11.glTexCoord2f(1, 1); GL11.glVertex3f(-1, 1, -1);
				GL11.glTexCoord2f(1, 0); GL11.glVertex3f(1, 1, -1);
				GL11.glEnd();
				
				GL11.glPopMatrix();
			
		
	}
	
	static void drawQuad() {
		
		float[] vertices = {
		        // Left bottom triangle
		        -0.5f, 0.5f, 0f,
		        -0.5f, -0.5f, 0f,
		        0.5f, -0.5f, 0f,
		        // Right top triangle
		        0.5f, -0.5f, 0f,
		        0.5f, 0.5f, 0f,
		        -0.5f, 0.5f, 0f
		};
		
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
		verticesBuffer.put(vertices);
		verticesBuffer.flip();
		
		int vertexCount = 6;
		
		// Create a new Vertex Array Object in memory and select it (bind)
		// A VAO can have up to 16 attributes (VBO's) assigned to it by default
		int vaoId = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoId);
		 
		// Create a new Vertex Buffer Object in memory and select it (bind)
		// A VBO is a collection of Vectors which in this case resemble the location of each vertex.
		int vboId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
		// Put the VBO in the attributes list at index 0
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		// Deselect (bind to 0) the VBO
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		 
		// Deselect (bind to 0) the VAO
		GL30.glBindVertexArray(0);
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		 
		// Bind to the VAO that has all the information about the quad vertices
		GL30.glBindVertexArray(vaoId);
		GL20.glEnableVertexAttribArray(0);
		 
		// Draw the vertices
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);
		 
		// Put everything back to default (deselect)
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		
		// Disable the VBO index from the VAO attributes list
		GL20.glDisableVertexAttribArray(0);
		 
		// Delete the VBO
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(vboId);
		 
		// Delete the VAO
		GL30.glBindVertexArray(0);
		GL30.glDeleteVertexArrays(vaoId);
	}
	
	private void drawVertexBufferObjectIndexed() {
		
		IntBuffer ib = BufferUtils.createIntBuffer(1);

	      glGenBuffers(ib);
	      int vcHandle = ib.get(0);

	      glEnableClientState(GL_VERTEX_ARRAY);
	      glEnableClientState(GL_COLOR_ARRAY);

	      glBindBuffer(GL_ARRAY_BUFFER, vcHandle);
	      glBufferData(GL_ARRAY_BUFFER, (9 + 9) << 2, GL_STATIC_DRAW);

	      {
	         ByteBuffer dataBuffer = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, (9 + 9) << 2, null);

	         // create geometry buffer (both vertex and color)
	         FloatBuffer vcBuffer = dataBuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();

	         vcBuffer.put(-0.5f).put(-0.5f).put(0.0f); // v
	         vcBuffer.put(1).put(0).put(0); // c

	         vcBuffer.put(+0.5f).put(-0.5f).put(0.0f); // v
	         vcBuffer.put(0).put(1).put(0); // c

	         vcBuffer.put(+0.0f).put(+0.5f).put(0.0f); // v
	         vcBuffer.put(0).put(0).put(1); // c
	         
	         vcBuffer.flip();         

	         glUnmapBuffer(GL_ARRAY_BUFFER);
	      }

	      glVertexPointer(3, GL_FLOAT, /* stride */(3 * 2) << 2, /* offset */0L << 2); // float at index 0
	      glColorPointer(3, GL_FLOAT, /* stride */(3 * 2) << 2, /* offset */(3 * 1) << 2); // float at index 3

	      glDrawArrays(GL_TRIANGLES, 0, 3 /* elements */);

	      glBindBuffer(GL_ARRAY_BUFFER, 0);

	      glDisableClientState(GL_COLOR_ARRAY);
	      glDisableClientState(GL_VERTEX_ARRAY);


	      // cleanup VBO handles
	      ib.put(0, vcHandle);
	      glDeleteBuffers(ib);
	      
	      
	   }

	

	private void run() {
		System.out.println("Hello LWJGL " + Sys.getVersion() + "!");
		
		running = true;
		
		try {
            init();
            loop();
            // Release window and window callbacks
            glfwDestroyWindow(window.id);
            
            
        } finally {
            // Terminate GLFW and release the GLFWerrorfun
            glfwTerminate();
            errorCallback.release();
        }
		
	}
	
	
	public static void main(String[] args) {
		new Main().run();
	}
	
}
