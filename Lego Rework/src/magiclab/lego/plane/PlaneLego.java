package magiclab.lego.plane;

import java.util.ArrayList;

import magiclab.lego.core.DrawableObject;
import magiclab.lego.core.Square;
import magiclab.lego.util.Util;
import processing.core.PShape;
import remixlab.dandelion.geom.Vec;

public class PlaneLego implements DrawableObject {
	/**
	 * Width
	 */
	private int width;
	/**
	 * Height
	 */
	private int height;
	/**
	 * PShapes
	 */
	private ArrayList<PShape> pShapes;
	/**
	 * PSquare Position
	 */
	private ArrayList<Square> pSquarePosition;
	private ArrayList<Vec> pCornerPoint;

	/**
	 * Plane Lego
	 */
	public PlaneLego() {
		super();
		pShapes = new ArrayList<PShape>();
		pSquarePosition = new ArrayList<Square>();
		pCornerPoint = new ArrayList<Vec>();
	}
	/**
	 * Plane Lego
	 * @param width
	 * @param height
	 * @param pShapes
	 */
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
	/**
	 * Get Width
	 * @return
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * Set Width
	 * @param width
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * Get Height
	 * @return
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * Set Height
	 * @param height
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	/**
	 * Get PShapes
	 * @return
	 */
	public ArrayList<PShape> getpShapes() {
		return pShapes;
	}
	/**
	 * Set PShapes
	 * @param pShapes
	 */
	public void setpShapes(ArrayList<PShape> pShapes) {
		this.pShapes = pShapes;
	}
	/**
	 * Get PSquare Position
	 * @return
	 */
	public ArrayList<Square> getpSquarePosition() {
		return pSquarePosition;
	}
	/**
	 * Set PSquare Position
	 * @param pSquarePosition
	 */
	public void setpSquarePosition(ArrayList<Square> pSquarePosition) {
		this.pSquarePosition = pSquarePosition;
	}
	/**
	 * Setup
	 */
	@Override
	public void setup() {
		pShapes = new ArrayList<PShape>();
		pSquarePosition = new ArrayList<Square>();
	}
	/**
	 * Add Shape
	 * @param pShape
	 */
	public void addShape(PShape pShape) {
		if (pShape == null)
			return;
		pShapes.add(pShape);
	}
	/**
	 * Add Square Position
	 * @param square
	 */
	public void addSquarePosition(Square square) {
		if (square == null)
			return;
		pSquarePosition.add(square);
	}
	/**
	 * Draw
	 */
	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}
	/**
	 * Key Pressed
	 */
	@Override
	public void keyPressed() {
		// TODO Auto-generated method stub

	}
	/**
	 * Mouse Clicked
	 */
	@Override
	public void mouseClicked() {
		// TODO Auto-generated method stub

	}
	/**
	 * Reset
	 */
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
