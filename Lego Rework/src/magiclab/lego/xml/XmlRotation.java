package magiclab.lego.xml;

import java.util.ArrayList;

import magiclab.lego.util.Util;
import remixlab.dandelion.geom.Vec;

public class XmlRotation {
	Vec sizeBrick;
	Vec translateForDraw;
	int numberOfDot;
	int numberOfBotDot;
	int numberOfBoxColider;
	int numberOfCenterOfDot;
	int numberOfInteractiveFrame;
	int scale;
	int threshold;

	ArrayList<XmlDot> dots;
	ArrayList<XmlDot> botdots;
	ArrayList<XmlBoxCollider> boxColliders;
	ArrayList<XmlCenterPositionOfDot> centerPostionOfDots;

	/**
	 * Xml Rotation
	 */
	public XmlRotation() {
		super();
		dots = new ArrayList<XmlDot>();
		botdots = new ArrayList<XmlDot>();
		boxColliders = new ArrayList<XmlBoxCollider>();
		centerPostionOfDots = new ArrayList<XmlCenterPositionOfDot>();
	}

	public int getNumberOfBotDot() {
		return numberOfBotDot;
	}

	public void setNumberOfBotDot(int numberOfBotDot) {
		this.numberOfBotDot = numberOfBotDot;
	}

	public ArrayList<XmlDot> getBotdots() {
		return botdots;
	}

	public void setBotdots(ArrayList<XmlDot> botdots) {
		this.botdots = botdots;
	}

	/**
	 * Get Size Brick
	 * 
	 * @return
	 */
	public Vec getSizeBrick() {
		return sizeBrick;
	}

	/**
	 * Set Size Brick
	 * 
	 * @param sizeBrick
	 */
	public void setSizeBrick(Vec sizeBrick) {
		this.sizeBrick = Util.newVecFromVec(sizeBrick);
	}

	/**
	 * Get Translate For Draw
	 * 
	 * @return
	 */
	public Vec getTranslateForDraw() {
		return translateForDraw;
	}

	/**
	 * Set Translate For Draw
	 * 
	 * @param translateForDraw
	 */
	public void setTranslateForDraw(Vec translateForDraw) {
		this.translateForDraw = Util.newVecFromVec(translateForDraw);
	}

	/**
	 * Get Dots
	 * 
	 * @return
	 */
	public ArrayList<XmlDot> getDots() {
		return dots;
	}

	/**
	 * Set Dots
	 * 
	 * @param dots
	 */
	public void setDots(ArrayList<XmlDot> dots) {
		this.dots = dots;
	}

	/**
	 * Get Box Colliders
	 * 
	 * @return
	 */
	public ArrayList<XmlBoxCollider> getBoxColliders() {
		return boxColliders;
	}

	/**
	 * Set Box Colliders
	 * 
	 * @param boxColliders
	 */
	public void setBoxColliders(ArrayList<XmlBoxCollider> boxColliders) {
		this.boxColliders = boxColliders;
	}

	/**
	 * Get Center Postion Of Dots
	 * 
	 * @return
	 */
	public ArrayList<XmlCenterPositionOfDot> getCenterPostionOfDots() {
		return centerPostionOfDots;
	}

	/**
	 * Set Center Postion Of Dots
	 * 
	 * @param centerPostionOfDots
	 */
	public void setCenterPostionOfDots(
			ArrayList<XmlCenterPositionOfDot> centerPostionOfDots) {
		this.centerPostionOfDots = centerPostionOfDots;
	}

	/**
	 * Get Number Of Dot
	 * 
	 * @return
	 */
	public int getNumberOfDot() {
		return numberOfDot;
	}

	/**
	 * Set Number Of Dot
	 * 
	 * @param numberOfDot
	 */
	public void setNumberOfDot(int numberOfDot) {
		this.numberOfDot = numberOfDot;
	}

	/**
	 * Get Number Of Box Colider
	 * 
	 * @return
	 */
	public int getNumberOfBoxColider() {
		return numberOfBoxColider;
	}

	/**
	 * Set Number Of Box Colider
	 * 
	 * @param numberOfBoxColider
	 */
	public void setNumberOfBoxColider(int numberOfBoxColider) {
		this.numberOfBoxColider = numberOfBoxColider;
	}

	/**
	 * Get Number Of Center Of Dot
	 * 
	 * @return
	 */
	public int getNumberOfCenterOfDot() {
		return numberOfCenterOfDot;
	}

	/**
	 * Set Number Of Center Of Dot
	 * 
	 * @param numberOfCenterOfDot
	 */
	public void setNumberOfCenterOfDot(int numberOfCenterOfDot) {
		this.numberOfCenterOfDot = numberOfCenterOfDot;
	}

	/**
	 * Get Number Of Interactive Frame
	 * 
	 * @return
	 */
	public int getNumberOfInteractiveFrame() {
		return numberOfInteractiveFrame;
	}

	/**
	 * Set Number Of Interactive Frame
	 * 
	 * @param numberOfInteractiveFrame
	 */
	public void setNumberOfInteractiveFrame(int numberOfInteractiveFrame) {
		this.numberOfInteractiveFrame = numberOfInteractiveFrame;
	}

	/**
	 * Get Scale
	 * 
	 * @return
	 */
	public int getScale() {
		return scale;
	}

	/**
	 * Set Scale
	 * 
	 * @param scale
	 */
	public void setScale(int scale) {
		this.scale = scale;
	}

	/**
	 * Get Threshold
	 * 
	 * @return
	 */
	public int getThreshold() {
		return threshold;
	}

	/**
	 * Set Threshold
	 * 
	 * @param threshold
	 */
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

}
