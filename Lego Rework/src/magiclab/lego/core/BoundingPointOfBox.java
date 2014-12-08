package magiclab.lego.core;

import java.util.ArrayList;

import remixlab.dandelion.geom.Vec;

public class BoundingPointOfBox {
	private ArrayList<Vec> points;

	public ArrayList<Vec> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<Vec> points) {
		this.points = points;
	}

	public BoundingPointOfBox() {
		points = new ArrayList<Vec>();
	}

	public void addPoint(Vec point) {
		if (point == null)
			return;
		points.add(point);
	}
}
