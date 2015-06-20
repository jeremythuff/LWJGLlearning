package info.jeremy.voxelengine;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import info.jeremy.voxelengine.io.Window;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.opengl.GLContext;

import static java.awt.Font.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glGetError;

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
    
    Font font;

	private int fontHeight;

    public Engine()
    {
     
    	window = new Window("Voxel Engine");
    	
        glfwMakeContextCurrent(window.id);
        GLContext.createFromCurrent();

        glfwSwapInterval(1);
    }

    public void init() {}

    public void update(float delta) {}

    public void render(float delta) {}

    public void dispose() {}

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
        
        font = new Font(MONOSPACED, PLAIN, 16);
        
        int imageWidth = 0;
        int imageHeight = 0;

        for (int i = 32; i < 256; i++) {
            if (i == 127) {
                continue;
            }
            char c = (char) i;
            BufferedImage ch = createCharImage(font, c, true);

            imageWidth += ch.getWidth();
            imageHeight = Math.max(imageHeight, ch.getHeight());
        }

        fontHeight = imageHeight;

        // Initialise the Game
        init();

        running = true;

        // Loop continuously and render and update
        while (running && glfwWindowShouldClose(window.id) != GL_TRUE)
        {
            // Get the time
            now = (float) glfwGetTime();
            delta = now - last;
            last = now;
            

            // Update and render
            update(delta);
            render(delta);

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
    	
    	System.out.println("Mouse x: "+xpos+" Mouse y:"+ypos);
    	
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
}
