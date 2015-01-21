package magiclab.lego.core;

import java.awt.Point;
import java.util.ArrayList;

import magiclab.lego.brick.Brick;
import magiclab.lego.util.Util;
import remixlab.dandelion.geom.Vec;

public class Rect {
	private float x;
	private float y;
	private float width;
	private float height;

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
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
	
	

	public Rect(float x2, float y2, float width2, float height2) {
		super();
		this.x = x2;
		this.y = y2;
		this.width = width2;
		this.height = height2;
	}

	public boolean Contains(Point iPoint) {
		if (width >= 0) {
			if (height >= 0) {
				if (iPoint.x > x && iPoint.x < (x + width) && iPoint.y > y && iPoint.y < (y+height)) {
					return true;
				} else {
					return false;
				}
			}
			if (height < 0) {
				if (iPoint.x > x && iPoint.x < (x + width) && iPoint.y < y && iPoint.y > (y+height)) {
					return true;
				} else {
					return false;
				}
			}
		}

		if (width < 0) {
			if (height >= 0) {
				if (iPoint.x < x && iPoint.x > (x + width) && iPoint.y > y && iPoint.y < (y+height)) {
					return true;
				} else {
					return false;
				}
			}
			if (height < 0) {
				if (iPoint.x < x && iPoint.x > (x + width) && iPoint.y < y && iPoint.y > (y+height)) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	public boolean Contains(Brick brick) {
		ArrayList<Vec> points = brick.generateEightPoint();
		
		for (int i = 0; i < points.size(); i++) {
			Point iPoint = Util.GetScreenspaceCoords(points.get(i));
			if (Contains(iPoint)) {
				return true;
			}
		}
		
		return false;
	}

}
