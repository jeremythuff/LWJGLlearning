package info.jeremy.lwjgllearning;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import info.jeremy.lwjgllearning.graphics.Renderer;
import info.jeremy.lwjgllearning.io.Font;
import info.jeremy.lwjgllearning.io.Window;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.opengl.GLContext;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Engine
{
    private static boolean running;

    // The callbacks
    private GLFWErrorCallback       errorCallback;
    private GLFWKeyCallback         keyCallback;
    private GLFWCursorPosCallback   cursorPosCallback;
    private GLFWMouseButtonCallback mouseButtonCallback;
    private GLFWScrollCallback      scrollCallback;
    
    private static Window window;
    
    public static double timer = 0;
	public static int frames = 0;
	public static int fps;
	
	public static double lastX = 0, lastY = 0;
	
	public Font font;
	public int fontHeight;

	public static Renderer renderer; 
	
    public Engine()
    {
     
    	window = new Window("Engine");
    	
        glfwMakeContextCurrent(window.id);
        GLContext.createFromCurrent();
       
        glfwSwapInterval(1);
    }

    public void init(Renderer renderer) {}

    protected void update(float delta) {}

    protected void render(float delta) {}

    protected void dispose() {}

    public void start()
    {
        float now, last, delta;
        
        last = 0;

        // Create the callbacks
        errorCallback = Callbacks.errorCallbackPrint(System.err);
        keyCallback = GLFWKeyCallback(this::glfwKeyCallback);
        cursorPosCallback = GLFWCursorPosCallback(this::glfwCursorPosCallback);
        mouseButtonCallback = GLFWMouseButtonCallback(this::glfwMouseButtonCallback);
        scrollCallback = GLFWScrollCallback(this::glfwScrollCallback);

        // Set the callbacks
        glfwSetErrorCallback(errorCallback);
        glfwSetKeyCallback(window.id, keyCallback);
        glfwSetCursorPosCallback(window.id, cursorPosCallback);
        glfwSetMouseButtonCallback(window.id, mouseButtonCallback);
        glfwSetScrollCallback(window.id, scrollCallback);
        
        font = new Font(16, false);
        
        int imageWidth = 0;
        int imageHeight = 0;



        fontHeight = imageHeight;

        // Initialise the Game
        renderer = new Renderer();
        renderer.init(true);
        init(renderer);

        running = true;

        // Loop continuously and render and update
 		
        while (running && glfwWindowShouldClose(window.id) != GL_TRUE)
        {
        	
        	renderer.clear();
        	
            // Get the time
            now = (float) glfwGetTime();
            delta = now - last;
            last = now;
            
            
            update(delta);
            render(delta);
            frames ++;
            
            if (glfwGetTime() - timer > 1) {
				timer += 1;
		        fps = frames;
				frames = 0;
			}
            
           
            	
            	
  
        	
            
	            
            



            
            int error = glGetError();
    		if (error != GL_NO_ERROR)
    			System.out.println(error);
    		
            // Poll the events and swap the buffers
            glfwPollEvents();
            glfwSwapBuffers(window.id);
            
        }

        // Dispose the game
        dispose();

        // Release the callbacks
        keyCallback.release();
        cursorPosCallback.release();
        mouseButtonCallback.release();
        scrollCallback.release();
        errorCallback.release();

        // Destroy the window
        glfwDestroyWindow(window.id);
        glfwTerminate();

        System.exit(0);
    }

    /**
     * Creates a char image from specified AWT font and char.
     *
     * @param font The AWT font
     * @param c The char
     * @param antiAlias Wheter the char should be antialiased or not
     * @return Char image
     */
    private BufferedImage createCharImage(java.awt.Font font, char c, boolean antiAlias) {
        /* Creating temporary image to extract character size */
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        if (antiAlias) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics();
        g.dispose();

        /* Get char charWidth and charHeight */
        int charWidth = metrics.charWidth(c);
        int charHeight = metrics.getHeight();

        /* Check if charWidth is 0 */
        if (charWidth == 0) {
            return null;
        }

        /* Create image for holding the char */
        image = new BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
        g = image.createGraphics();
        if (antiAlias) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        g.setFont(font);
        g.setPaint(Color.WHITE);
        g.drawString(String.valueOf(c), 0, metrics.getAscent());
        g.dispose();
        return image;
    }

	public void end() {
        running = false;
    }

    // Callback functions which can be overriden
    public void glfwKeyCallback(long window, int key, int scancode, int action, int mods) {
        // End on escape
        if (key == GLFW_KEY_ESCAPE && action != GLFW_RELEASE)
            end();
    }

    public void glfwCursorPosCallback(long window, double xpos, double ypos) {
       	
    	lastX = xpos;
    	lastY = ypos;
    	
    }

    public void glfwMouseButtonCallback(long window, int button, int action, int mods) {
    	
    }

    public void glfwScrollCallback(long window, double xoffset, double yoffset) {
    }

    // Static helpful polled input methods
    public static boolean isKeyPressed(int key) {
        return glfwGetKey(window.id, key) != GLFW_RELEASE;
    }

    public static boolean isMouseButtonPressed(int button) {
        return glfwGetMouseButton(window.id, button) != GLFW_RELEASE;
    }

    public static long getWindowID() {
        return window.id;
    }
    
    public long getTime() {
        return System.nanoTime() / 1000000;
    }

    public static void main(String[] args) {
        new Engine().start();
    }
    
}
