package magiclab.lego.core;

import magiclab.lego.util.Util;
import remixlab.dandelion.geom.Vec;

public class Box {
	/**
	 * Position
	 */
	private Vec position;
	/**
	 * Center Position
	 */
	private Vec centerPosition;
	/**
	 * Width
	 */
	private float width;
	/**
	 * Height
	 */
	private float height;
	/**
	 * Depth
	 */
	private float depth;
	/**
	 * Size
	 */
	private Vec size;
	/**
	 * Get Position
	 * @return
	 */
	public Vec getPosition() {
		return position;
	}
	/**
	 * Set Position
	 * @param position
	 */
	public void setPosition(Vec position) {
		this.position = Util.newVecFromVec(position);
	}
	/**
	 * Get Center Position
	 * @return
	 */
	public Vec getCenterPosition() {
		return centerPosition;
	}
	/**
	 * Set Center Position
	 * @param centerPosition
	 */
	public void setCenterPosition(Vec centerPosition) {
		this.centerPosition = Util.newVecFromVec(centerPosition);
	}
	/**
	 * Get Width
	 * @return
	 */
	public float getWidth() {
		return width;
	}
	/**
	 * Set Width
	 * @param width
	 */
	public void setWidth(float width) {
		this.width = width;
	}
	/**
	 * Get Height
	 * @return
	 */
	public float getHeight() {
		return height;
	}
	/**
	 * Set Height
	 * @param height
	 */
	public void setHeight(float height) {
		this.height = height;
	}
	/**
	 * Get Depth
	 * @return
	 */
	public float getDepth() {
		return depth;
	}
	/**
	 * Set Depth
	 * @param depth
	 */
	public void setDepth(float depth) {
		this.depth = depth;
	}
	/**
	 * Get Size
	 * @return
	 */
	public Vec getSize() {
		return size;
	}
	/**
	 * Set Size
	 * @param size
	 */
	public void setSize(Vec size) {
		this.size = Util.newVecFromVec(size);
	}
	/**
	 * Container
	 * @param point
	 * @return
	 */
	public boolean Container(Vec point) {
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
