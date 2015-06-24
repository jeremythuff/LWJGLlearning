package info.jeremy.lwjgllearning.ogltuts.entities;

import info.jeremy.lwjgllearning.graphics.Renderer;

public abstract class Renderable {
	
	protected Renderer renderer;
	
	public Renderable(Renderer renderer) {
		this.renderer = renderer;
	};
	
	protected abstract void render();
	
	protected abstract void dispose();

}
