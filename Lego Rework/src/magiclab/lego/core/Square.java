package magiclab.lego.core;

import magiclab.lego.util.Util;
import remixlab.dandelion.geom.Vec;

public class Square {

	private Vec position;
	private float width;
	private float height;

	public Vec getPosition() {
		return position;
	}

	public void setPosition(Vec position) {
		this.position = Util.newVecFromVec(position);
	}

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

	public Square(Vec position, float brickSize, float brickSize2) {
		super();
		this.position = position;
		this.width = brickSize;
		this.height = brickSize2;
	}

	public Square() {
		super();
	}

	public boolean container(Vec point) {
		if (point.x() > position.x() && point.x() < (position.x() + width)
				&& point.y() > position.y()
				&& point.y() < (position.y() + height)) {
			return true;

		}
		return false;
	}

	public boolean compare(Square square) {
		return square.getPosition().equals(position);
	}

}
