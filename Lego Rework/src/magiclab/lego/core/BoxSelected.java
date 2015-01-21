package magiclab.lego.core;

import magiclab.lego.util.Util;
import remixlab.dandelion.geom.Vec;

public class BoxSelected {
	private Vec startClick;
	private float width;
	private float height;
	private boolean iStart;

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public boolean isiStart() {
		return iStart;
	}

	public void setiStart(boolean iStart) {
		this.iStart = iStart;
	}

	public Vec getStartClick() {
		return startClick;
	}

	public void setStartClick(Vec startClick) {
		this.startClick = Util.newVecFromVec(startClick);
	}

	public BoxSelected() {
		startClick = new Vec();
		iStart = false;
	}

	public void print() {
		System.out.println("Start Click: " + startClick);
		System.out.println("Box: " + startClick.x() + " " + startClick.y()
				+ " " + width + " " + height);
	}

	public void reset() {
		width = 0;
		height = 0;
	}

}
