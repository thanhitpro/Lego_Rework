package magiclab.lego.core;

import java.util.ArrayList;

import remixlab.dandelion.geom.Vec;

public class BoxCollider {
	private ArrayList<Box> boxes;

	public ArrayList<Box> getBoxes() {
		return boxes;
	}

	public void setBoxes(ArrayList<Box> boxes) {
		this.boxes = boxes;
	}

	public void addBox(Box box) {
		if (box == null)
			return;
		boxes.add(box);
	}

	public BoxCollider() {
		boxes = new ArrayList<Box>();
	}

	public boolean Container(Vec point) {
		if (point == null)
			return false;
		for (int i = 0; i < boxes.size(); i++) {
			Box box = boxes.get(i);
			if (box == null)
				return false;
			if (box.checkContainer(point))
				return true;
		}

		return false;
	}
}
