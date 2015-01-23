package magiclab.lego.plane;

import java.util.ArrayList;

import magiclab.lego.core.DrawableObject;
import magiclab.lego.core.Square;
import magiclab.lego.util.Util;
import processing.core.PShape;
import remixlab.dandelion.geom.Vec;

public class PlaneLego implements DrawableObject {

	private int width;
	private int height;
	private ArrayList<PShape> pShapes;
	private ArrayList<Square> pSquarePosition;
	private ArrayList<Vec> pCornerPoint;

	public PlaneLego() {
		super();
		pShapes = new ArrayList<PShape>();
		pSquarePosition = new ArrayList<Square>();
		pCornerPoint = new ArrayList<Vec>();
	}

	public PlaneLego(int width, int height, ArrayList<PShape> pShapes) {
		super();
		this.width = width;
		this.height = height;
		this.pShapes = pShapes;
	}

	public ArrayList<Vec> getpCornerPoint() {
		return pCornerPoint;
	}

	public void setpCornerPoint(ArrayList<Vec> pCornerPoint) {
		this.pCornerPoint = pCornerPoint;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public ArrayList<PShape> getpShapes() {
		return pShapes;
	}

	public void setpShapes(ArrayList<PShape> pShapes) {
		this.pShapes = pShapes;
	}

	public ArrayList<Square> getpSquarePosition() {
		return pSquarePosition;
	}

	public void setpSquarePosition(ArrayList<Square> pSquarePosition) {
		this.pSquarePosition = pSquarePosition;
	}

	@Override
	public void setup() {
		pShapes = new ArrayList<PShape>();
		pSquarePosition = new ArrayList<Square>();
	}

	public void addShape(PShape pShape) {
		if (pShape == null)
			return;
		pShapes.add(pShape);
	}

	public void addSquarePosition(Square square) {
		if (square == null)
			return;
		pSquarePosition.add(square);
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked() {
		// TODO Auto-generated method stub

	}

	public void reset() {
		pShapes.clear();
		pSquarePosition.clear();
	}

	public void addShape(ArrayList<PShape> expandShape) {
		pShapes.addAll(expandShape);
	}

	public void addSquarePosition(ArrayList<Square> expandShapePosition) {
		pSquarePosition.addAll(expandShapePosition);
	}

	public void generateFourPoint() {
		pCornerPoint = new ArrayList<Vec>();

		for (int i = 0; i < pSquarePosition.size(); i++) {
			Vec point0 = new Vec(pSquarePosition.get(i).getPosition().x(),
					pSquarePosition.get(i).getPosition().y(), 0);
			Vec point1 = new Vec(pSquarePosition.get(i).getPosition().x()
					+ Util.BRICK_SIZE, pSquarePosition.get(i).getPosition().y());
			Vec point2 = new Vec(pSquarePosition.get(i).getPosition().x(),
					pSquarePosition.get(i).getPosition().y() + Util.BRICK_SIZE);
			Vec point3 = new Vec(pSquarePosition.get(i).getPosition().x()
					+ Util.BRICK_SIZE, pSquarePosition.get(i).getPosition().y()
					+ Util.BRICK_SIZE);

			pCornerPoint.add(point0);

			pCornerPoint.add(point1);

			pCornerPoint.add(point2);

			pCornerPoint.add(point3);
		}
	}

}
