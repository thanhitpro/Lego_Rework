package magiclab.lego.core;

import java.util.ArrayList;

import remixlab.dandelion.geom.Vec;

public class BoxCollider {
	/**
	 * Boxes
	 */
	private ArrayList<Box> boxes;
	/**
	 * Get Boxes
	 * @return
	 */
	public ArrayList<Box> getBoxes() {
		return boxes;
	}
	/**
	 * Set Boxes
	 * @param boxes
	 */
	public void setBoxes(ArrayList<Box> boxes) {
		this.boxes = boxes;
	}
	/**
	 * Add Box
	 * @param box
	 */
	public void addBox(Box box) {
		if (box == null)
			return;
		boxes.add(box);
	}
	/**
	 * Box Collider
	 */
	public BoxCollider() {
		boxes = new ArrayList<Box>();
	}
	/**
	 * Container
	 * @param point
	 * @return
	 */
	public boolean Container(Vec point) {
		if (point == null)
			return false;
		for (int i = 0; i < boxes.size(); i++) {
			Box box = boxes.get(i);
			if (box == null)
				return false;
			if (box.Container(point))
				return true;
		}

		return false;
	}
}
