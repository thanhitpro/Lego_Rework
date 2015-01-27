package magiclab.lego.core;

import java.util.ArrayList;

import remixlab.dandelion.geom.Vec;

public class BoundingPointOfBox {
	/**
	 * Points
	 */
	private ArrayList<Vec> points;
	/**
	 * Get Points
	 * @return
	 */
	public ArrayList<Vec> getPoints() {
		return points;
	}
	/**
	 * Set Points
	 * @param points
	 */
	public void setPoints(ArrayList<Vec> points) {
		this.points = points;
	}
	/**
	 * Bounding Point Of Box
	 */
	public BoundingPointOfBox() {
		points = new ArrayList<Vec>();
	}
	/**
	 * Add Point
	 * @param point
	 */
	public void addPoint(Vec point) {
		if (point == null)
			return;
		points.add(point);
	}
}
