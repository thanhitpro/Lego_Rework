package magiclab.lego.core;

import magiclab.lego.util.Util;
import remixlab.dandelion.geom.Vec;

public class Square {
	/**
	 * Position
	 */
	private Vec position;
	/**
	 * Width
	 */
	private float width;
	/**
	 * Height
	 */
	private float height;
	/**
	 * Index Square
	 */
	private String indexSquare;
	/**
	 * Get Index Square
	 * @return
	 */
	public String getIndexSquare() {
		return indexSquare;
	}
	/**
	 * Set Index Square
	 * @param indexSquare
	 */
	public void setIndexSquare(String indexSquare) {
		this.indexSquare = indexSquare;
	}
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
	 * Square
	 * @param position
	 * @param brickSize
	 * @param brickSize2
	 */
	public Square(Vec position, float brickSize, float brickSize2) {
		super();
		this.position = position;
		this.width = brickSize;
		this.height = brickSize2;
		this.indexSquare = "";
	}
	/**
	 * Square
	 */
	public Square() {
		super();
		this.indexSquare = "";
	}
	/**
	 * Container
	 * @param point
	 * @return
	 */
	public boolean container(Vec point) {
		if (point.x() > position.x() && point.x() < (position.x() + width)
				&& point.y() > position.y()
				&& point.y() < (position.y() + height)) {
			return true;

		}
		return false;
	}
	/**
	 * Compare
	 * @param square
	 * @return
	 */
	public boolean compare(Square square) {
		return square.getPosition().equals(position);
	}

}
