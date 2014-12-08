package magiclab.lego.plane;

import java.util.ArrayList;

import magiclab.lego.core.DrawableObject;
import magiclab.lego.core.Square;
import processing.core.PShape;

public class PlaneLego implements DrawableObject {

	private int width;
	private int height;
	private ArrayList<PShape> pShapes;
	private ArrayList<Square> pSquarePosition;

	public PlaneLego() {
		super();
	}

	public PlaneLego(int width, int height, ArrayList<PShape> pShapes) {
		super();
		this.width = width;
		this.height = height;
		this.pShapes = pShapes;
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

}
