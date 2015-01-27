package magiclab.lego.core;

public interface DrawableObject {
	/**
	 * Setup
	 */
	public abstract void setup();
	/**
	 * Draw
	 */
	public abstract void draw();
	/**
	 * Key Pressed 
	 */
	public abstract void keyPressed();
	/**
	 * Mouse Clicked
	 */
	public abstract void mouseClicked();
}
