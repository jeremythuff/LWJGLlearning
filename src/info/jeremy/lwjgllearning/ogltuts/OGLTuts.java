package info.jeremy.lwjgllearning.ogltuts;

import java.awt.Font;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import static org.lwjgl.stb.STBEasyFont.*;
import info.jeremy.lwjgllearning.Engine;
import info.jeremy.lwjgllearning.io.Window;
import info.jeremy.lwjgllearning.ogltuts.entities.Dot;
import info.jeremy.lwjgllearning.ogltuts.entities.Rectangle;
import info.jeremy.lwjgllearning.ogltuts.entities.RotationTriangle;
import info.jeremy.lwjgllearning.ogltuts.entities.ScalingTriangle;
import info.jeremy.lwjgllearning.ogltuts.entities.ShadedTriangle;
import info.jeremy.lwjgllearning.ogltuts.entities.TranslationTriangle;
import info.jeremy.lwjgllearning.ogltuts.entities.Triangle;
import info.jeremy.lwjgllearning.ogltuts.entities.UniformTriangle;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class OGLTuts extends Engine
{
	
	Font font;
	
	//Dot dot;
	//Triangle trianlge;
	//Rectangle rec;
	//ShadedTriangle shadedTriangle;
	//UniformTriangle uniformTriangle;
	//TranslationTriangle translationTriangle;
	//RotationTriangle rotationTraingle;
	ScalingTriangle scalingTriangle;

    public void init() {
       glfwSetWindowTitle(OGLTuts.getWindowID(), "OGL TUTS");
       //dot = new Dot();
       //trianlge = new Triangle();
       //rec = new Rectangle();
       //shadedTriangle = new ShadedTriangle();
       //uniformTriangle = new UniformTriangle();
       //translationTriangle = new TranslationTriangle();
       //rotationTraingle = new RotationTriangle();
       scalingTriangle = new ScalingTriangle();

    }

    public void render(float delta) {
        glClear(GL_COLOR_BUFFER_BIT);
        
        //dot.render();
        //trianlge.render();
        //rec.render();
        //shadedTriangle.render();
        //uniformTriangle.render();
        //translationTriangle.render();
        //rotationTraingle.render();
        scalingTriangle.render();
        
        
    }

    public void dispose() {
       //dot.dispose();
       //trianlge.dispose();
       //rec.dispose();
    	//shadedTriangle.dispose();
    	//uniformTriangle.dispose();
    	//translationTriangle.dispose();
    	//rotationTraingle.dispose();
    	scalingTriangle.render();
    }

    public static void main(String[] args) {
        new OGLTuts().start();
    }
    
}
