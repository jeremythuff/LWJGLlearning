package info.jeremy.voxelengine.ogl;

import info.jeremy.voxelengine.Engine;
import info.jeremy.voxelengine.ogl.entities.Dot;
import info.jeremy.voxelengine.ogl.entities.Rectangle;
import info.jeremy.voxelengine.ogl.entities.Triangle;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class OGLTuts extends Engine
{
	Dot dot;
	Triangle trianlge;
	Rectangle rec;

    public void init() {
       glfwSetWindowTitle(OGLTuts.getWindowID(), "OGL TUTS");
       dot = new Dot();
       trianlge = new Triangle();
       rec = new Rectangle();
    }

    public void render(float delta) {
        glClear(GL_COLOR_BUFFER_BIT);
        
        dot.render();
        trianlge.render();
        rec.render();
    }

    public void dispose() {
       dot.dispose();
       trianlge.dispose();
       rec.dispose();
    }

    public static void main(String[] args) {
        new OGLTuts().start();
    }
    
}
