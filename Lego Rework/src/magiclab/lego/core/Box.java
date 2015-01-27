package magiclab.lego.core;

import magiclab.lego.util.Util;
import remixlab.dandelion.geom.Vec;

public class Box {
	private Vec position;
	private Vec centerPosition;
	private float width;
	private float height;
	private float depth;
	private Vec size;

	public Vec getPosition() {
		return position;
	}

	public void setPosition(Vec position) {
		this.position = Util.newVecFromVec(position);
	}

	public Vec getCenterPosition() {
		return centerPosition;
	}

	public void setCenterPosition(Vec centerPosition) {
		this.centerPosition = Util.newVecFromVec(centerPosition);
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

	public float getDepth() {
		return depth;
	}

	public void setDepth(float depth) {
		this.depth = depth;
	}

	public Vec getSize() {
		return size;
	}

	public void setSize(Vec size) {
		this.size = Util.newVecFromVec(size);
	}

	public boolean checkContainer(Vec point) {
		if (point == null)
			return false;
		if (point.x() >= position.x() && point.x() <= (position.x() + width)
				&& point.y() >= position.y()
				&& point.y() <= (position.y() + height)
				&& point.z() >= position.z()
				&& point.z() <= (position.z() + depth)) {
			return true;
		}
		return false;
	}
}
