package magiclab.lego.brick;

import java.util.ArrayList;

import magiclab.lego.core.BoundingPointOfBox;
import magiclab.lego.core.Box;
import magiclab.lego.core.BoxCollider;
import magiclab.lego.core.DrawableObject;
import magiclab.lego.core.Square;
import magiclab.lego.util.Util;
import magiclab.lego.xml.XmlBoxCollider;
import magiclab.lego.xml.XmlBrick;
import magiclab.lego.xml.XmlCenterPositionOfDot;
import magiclab.lego.xml.XmlDot;
import magiclab.lego.xml.XmlRotation;
import processing.core.PShape;
import remixlab.dandelion.geom.Vec;

public class Brick implements DrawableObject {

	/**
	 * For loading obj model from resource
	 */
	protected PShape model;
	/**
	 * For storing model name of brick
	 */
	protected String modelName;
	/**
	 * Consist of 7 points of a Box
	 */
	protected BoundingPointOfBox boundingPointOfBox;
	/**
	 * Scale of brick
	 */
	protected float scaleRatio;
	/**
	 * Rotation
	 */
	protected Vec rotation;
	/**
	 * Size of brick
	 */
	protected Vec sizeBrick;
	/**
	 * Position of brick
	 */
	protected Vec translation;
	/**
	 * Xml object that define this brick
	 */
	protected XmlBrick xmlBrick;
	/**
	 * Consist of some boxes according to brick shape
	 */
	protected BoxCollider boxCollider;
	/**
	 * A brick that exported from 3ds file has a little different position The
	 * origin position always is center of the first Dot so we must move a
	 * little
	 */
	protected Vec calibrateVec;
	/**
	 * When a brick rotation, sometimes, the position will be changed like 2x1
	 * brick. This info help to re-translate to correct position
	 */
	protected Vec translateForDrawAfterRotate;
	/**
	 * When a brick is initialized, we will store it to a dictionary for using
	 * next time
	 */
	protected boolean firstInit;
	/**
	 * Number of times rotation of brick
	 */
	protected int timesRotation;
	/**
	 * Identity of brick
	 */
	protected int id;
	/**
	 * Color of brick
	 */
	protected Vec color;
	/**
	 * Delete flag that means this brick is deleted
	 */
	protected boolean deleteFlag;
	/**
	 * All square on top brick
	 */
	protected ArrayList<Square> squareOnTopBrick;
	/**
	 * Translation Before Rotate
	 */
	protected Vec translationBeforeRotate;
	/**
	 * Brick
	 */
	public Brick() {
		super();
		color = new Vec(230, 0, 0);
		squareOnTopBrick = new ArrayList<Square>();
	}
	/**
	 * Load Model
	 * @param modelFileName
	 */
	public void LoadModel(String modelFileName) {
		if (Util.XML_BRICK_DICTIONARY.get(modelName) != null
				&& Util.XML_BRICK_DICTIONARY.get(modelName).isFinishLoading()) {
			this.xmlBrick = Util.XML_BRICK_DICTIONARY.get(modelName);
		} else {
			xmlBrick = new XmlBrick();
			xmlBrick.readXml(Util.PLUS_PATH_RESOURCE + modelFileName + ".xml",
					Util.MODEL_NAME_LIST.indexOf(modelName));
			Util.XML_BRICK_DICTIONARY.put(modelName, xmlBrick);
		}

		calibrateVec = xmlBrick.getCalibrateVec();
		sizeBrick = xmlBrick.getDefaultSizeBrick();
	}

	public Vec getTranslationBeforeRotate() {
		return translationBeforeRotate;
	}

	public void setTranslationBeforeRotate(Vec translationBeforeRotate) {
		this.translationBeforeRotate = translationBeforeRotate;
	}
	/**
	 * Get Model
	 * @return
	 */
	public PShape getModel() {
		return model;
	}
	/**
	 * Set Model
	 * @param model
	 */
	public void setModel(PShape model) {
		this.model = model;
	}
	/**
	 * Get Model Name
	 * @return
	 */
	public String getModelName() {
		return modelName;
	}
	/**
	 * Set Model Name
	 * @param modelName
	 */

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	/**
	 * Get Bounding Point Of Box
	 * @return
	 */

	public BoundingPointOfBox getBoundingPointOfBox() {
		return boundingPointOfBox;
	}
	/**
	 * Set Bounding Point Of Box
	 * @param boundingPointOfBox
	 */

	public void setBoundingPointOfBox(BoundingPointOfBox boundingPointOfBox) {
		this.boundingPointOfBox = boundingPointOfBox;
	}
	/**
	 * Get Scale Ratio
	 * @return
	 */

	public float getScaleRatio() {
		return scaleRatio;
	}
	/**
	 * Set Scale Ratio
	 * @param scaleRatio
	 */

	public void setScaleRatio(float scaleRatio) {
		this.scaleRatio = scaleRatio;
	}
	/**
	 * Get Rotation
	 * @return
	 */

	public Vec getRotation() {
		return rotation;
	}
	/**
	 * Set Rotation
	 * @param rotation
	 */

	public void setRotation(Vec rotation) {
		this.rotation = Util.newVecFromVec(rotation);
	}
	/**
	 * Get Size Brick
	 * @return
	 */

	public Vec getSizeBrick() {
		return sizeBrick;
	}
	/**
	 * Set Size Brick
	 * @param sizeBrick
	 */

	public void setSizeBrick(Vec sizeBrick) {
		this.sizeBrick = Util.newVecFromVec(sizeBrick);
	}
	/**
	 * Get Translation
	 * @return
	 */

	public Vec getTranslation() {
		return translation;
	}
	/**
	 * Set Translation
	 * @param translation
	 */

	public void setTranslation(Vec translation) {
		this.translation = Util.newVecFromVec(translation);
	}
	/**
	 * Get Xml Brick
	 * @return
	 */

	public XmlBrick getXmlBrick() {
		return xmlBrick;
	}
	/**
	 * Set Xml Brick
	 * @param xmlBrick
	 */

	public void setXmlBrick(XmlBrick xmlBrick) {
		this.xmlBrick = xmlBrick;
	}
	/**
	 * Get Box Collider
	 * @return
	 */

	public BoxCollider getBoxCollider() {
		return boxCollider;
	}
	/**
	 * Set Box Collider
	 * @param boxCollider
	 */

	public void setBoxCollider(BoxCollider boxCollider) {
		this.boxCollider = boxCollider;
	}
	/**
	 * Get Calibrate Vec
	 * @return
	 */

	public Vec getCalibrateVec() {
		return calibrateVec;
	}
	/**
	 * Set Calibrate Vec
	 * @param calibrateVec
	 */

	public void setCalibrateVec(Vec calibrateVec) {
		this.calibrateVec = Util.newVecFromVec(calibrateVec);
	}
	/**
	 * Get Translate For Draw After Rotate
	 * @return
	 */

	public Vec getTranslateForDrawAfterRotate() {
		return translateForDrawAfterRotate;
	}
	/**
	 * Set Translate For Draw After Rotate
	 * @param translateForDrawAfterRotate
	 */

	public void setTranslateForDrawAfterRotate(Vec translateForDrawAfterRotate) {
		this.translateForDrawAfterRotate = Util
				.newVecFromVec(translateForDrawAfterRotate);
	}
	/**
	 * Is First Init
	 * @return
	 */

	public boolean isFirstInit() {
		return firstInit;
	}
	/**
	 * Set First Init
	 * @param firstInit
	 */

	public void setFirstInit(boolean firstInit) {
		this.firstInit = firstInit;
	}
	/**
	 * Get Times Rotation
	 * @return
	 */

	public int getTimesRotation() {
		return timesRotation;
	}
	/**
	 * Set Times Rotation
	 * @param timesRotation
	 */

	public void setTimesRotation(int timesRotation) {
		this.timesRotation = timesRotation;
	}
	/**
	 * Get Id
	 * @return
	 */

	public int getId() {
		return id;
	}
	/**
	 * Set Id
	 * @param id
	 */

	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Get Color
	 * @return
	 */

	public Vec getColor() {
		return color;
	}
	/**
	 * Set Color
	 * @param color
	 */

	public void setColor(Vec color) {
		this.color = Util.newVecFromVec(color);
	}
	/**
	 * Is Delete Flag
	 * @return
	 */

	public boolean isDeleteFlag() {
		return deleteFlag;
	}
	/**
	 * Set Delete Flag
	 * @param deleteFlag
	 */

	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	/**
	 * Generate Box Collider
	 */

	public void generateBoxCollider() {
		generateBoxCollider(xmlBrick.getRotations().get(timesRotation)
				.getBoxColliders());
	}

	/**
	 * Generate box collider for this brick
	 * 
	 * @param xmlBoxColliders
	 *            An array box colliders that define from xml
	 */

	public void generateBoxCollider(ArrayList<XmlBoxCollider> xmlBoxColliders) {
		boxCollider = new BoxCollider();
		ArrayList<Box> boxes = new ArrayList<Box>();
		for (int i = 0; i < xmlBoxColliders.size(); i++) {
			XmlBoxCollider xmlBoxCollider = xmlBoxColliders.get(i);
			Box box = new Box();
			box.setPosition(Vec.add(translation,
					Vec.multiply(xmlBoxCollider.getPosition(), Util.BRICK_SIZE)));
			box.setWidth(Util.BRICK_SIZE * xmlBoxCollider.getSize().x());
			box.setHeight(Util.BRICK_SIZE * xmlBoxCollider.getSize().y());
			box.setDepth(Util.BRICK_HEIGHT * xmlBoxCollider.getSize().z()
					+ Util.BRICK_DOT_HEIGHT);
			box.setSize(xmlBoxCollider.getSize());
			boxes.add(box);
		}
		boxCollider.setBoxes(boxes);
	}

	/**
	 * Generate a bounding point of box of this brick
	 * 
	 * @param xmlCenterPositionOfDots
	 *            a list center position of dots
	 */

	public void generateBoundingPointOfBox(
			ArrayList<XmlCenterPositionOfDot> xmlCenterPositionOfDots) {
		boundingPointOfBox = new BoundingPointOfBox();
		for (int i = 0; i < boxCollider.getBoxes().size(); i++) {
			Box box = boxCollider.getBoxes().get(i);
			for (int j = 0; j < box.getSize().x(); j++) {
				for (int k = 0; k < box.getSize().y(); k++) {
					for (int l = 0; l < box.getSize().z(); l++) {
						float x = box.getPosition().x() + Util.BRICK_SIZE * j;
						float y = box.getPosition().y() + Util.BRICK_SIZE * k;
						float z = box.getPosition().z() + Util.BRICK_HEIGHT * l;

						boundingPointOfBox.addPoint(new Vec(x + Util.BRICK_SIZE
								/ 2, y + Util.BRICK_SIZE / 2, z
								+ box.getSize().z() * Util.BRICK_HEIGHT / 2));

					}
				}
			}
		}
	}

	/**
	 * Increase times rotation
	 */

	public void increaseTimesRotate() {
		if (translationBeforeRotate != null)
			translation = Util.newVecFromVec(translationBeforeRotate);
		if (timesRotation == (xmlBrick.getNumberOfTimeRotation() - 1)) {
			timesRotation = 0;
		} else {
			timesRotation++;
		}

		sizeBrick = xmlBrick.getRotations().get(timesRotation).getSizeBrick();
		if (timesRotation == 2) {
			translationBeforeRotate = Util.newVecFromVec(translation);
			translation.setX(translation.x() - (sizeBrick.x() - 1)
					* Util.BRICK_SIZE);
		}
		if (timesRotation == 3) {
			translationBeforeRotate = Util.newVecFromVec(translation);
			translation.setY(translation.y() - (sizeBrick.y() - 1)
					* Util.BRICK_SIZE);
		}
	}

	/**
	 * Increase Times Rotate
	 * @param center
	 */
	public void increaseTimesRotate(Vec center) {
		if (translationBeforeRotate != null)
			translation = Util.newVecFromVec(translationBeforeRotate);
		if (timesRotation == (xmlBrick.getNumberOfTimeRotation() - 1)) {
			timesRotation = 0;
		} else {
			timesRotation++;
		}

		sizeBrick = xmlBrick.getRotations().get(timesRotation).getSizeBrick();
		if (timesRotation == 2) {
			translationBeforeRotate = Util.newVecFromVec(translation);
			translation.setX(translation.x() - (sizeBrick.x() - 1)
					* Util.BRICK_SIZE);
		}
		if (timesRotation == 3) {
			translationBeforeRotate = Util.newVecFromVec(translation);
			translation.setY(translation.y() - (sizeBrick.y() - 1)
					* Util.BRICK_SIZE);
		}
	}

	/**
	 * Decrease times rotation
	 */

	public void decreaseTimesRotate() {
		if (translationBeforeRotate != null)
			translation = Util.newVecFromVec(translationBeforeRotate);
		if (timesRotation == 0) {
			timesRotation = xmlBrick.getNumberOfTimeRotation() - 1;
		} else {
			timesRotation--;
		}

		sizeBrick = xmlBrick.getRotations().get(timesRotation).getSizeBrick();
		if (timesRotation == 2) {
			translationBeforeRotate = Util.newVecFromVec(translation);
			translation.setX(translation.x() - (sizeBrick.x() - 1)
					* Util.BRICK_SIZE);
		}
		if (timesRotation == 3) {
			translationBeforeRotate = Util.newVecFromVec(translation);
			translation.setY(translation.y() - (sizeBrick.y() - 1)
					* Util.BRICK_SIZE);
		}
	}

	/**
	 * Decrease Times Rotate
	 * @param center
	 */
	
	public void decreaseTimesRotate(Vec center) {
		if (translationBeforeRotate != null)
			translation = Util.newVecFromVec(translationBeforeRotate);
		if (timesRotation == 0) {
			timesRotation = xmlBrick.getNumberOfTimeRotation() - 1;
		} else {
			timesRotation--;
		}

		sizeBrick = xmlBrick.getRotations().get(timesRotation).getSizeBrick();

		if (timesRotation == 1) {
			translationBeforeRotate = Util.newVecFromVec(translation);
			translation.setX(translation.x() - (sizeBrick.x() - 1)
					* Util.BRICK_SIZE);
		}

		if (timesRotation == 2) {
			translationBeforeRotate = Util.newVecFromVec(translation);
			translation.setX(translation.x() - (sizeBrick.x() - 1)
					* Util.BRICK_SIZE);
		}
		if (timesRotation == 3) {
			translationBeforeRotate = Util.newVecFromVec(translation);
			translation.setY(translation.y() - (sizeBrick.y() - 1)
					* Util.BRICK_SIZE);

		}
	}
	/**
	 * Get Square On Top Brick
	 * @return
	 */

	public ArrayList<Square> getSquareOnTopBrick() {
		return squareOnTopBrick;
	}
	/**
	 * Set Square On Top Brick
	 * @param squareOnTopBrick
	 */

	public void setSquareOnTopBrick(ArrayList<Square> squareOnTopBrick) {
		this.squareOnTopBrick = squareOnTopBrick;
	}

	/**
	 * Calibrate brick position after rotating
	 */
	public void calibrateAfterRotate() {
		translateForDrawAfterRotate = xmlBrick.getRotations()
				.get(timesRotation).getTranslateForDraw();
	}

	/**
	 * Calibrate brick position after rotating in a group
	 */

	public void calibrateAfterRotateInGroup(int rotate) {
		switch (rotate) {
		case 0:
			break;
		case 1:
			translateForDrawAfterRotate.setX(translateForDrawAfterRotate.x()
					- (sizeBrick.x() - 1) * Util.BRICK_SIZE);
			break;
		case 2:

			break;
		case 3:
			break;
		case 4:
			break;
		default:
			break;
		}
	}

	/**
	 * Generate initialize data of this brick
	 */
	public void generateInitData() {
		XmlRotation xmlRotation = xmlBrick.getRotations().get(timesRotation);
		generateBoxCollider(xmlRotation.getBoxColliders());
		generateBoundingPointOfBox(xmlRotation.getCenterPostionOfDots());
		generateSquareTopBrick(xmlRotation.getDots());
	}
	/**
	 * Generate Square Top Brick
	 * @param dots
	 */
	private void generateSquareTopBrick(ArrayList<XmlDot> dots) {
		for (int i = 0; i < dots.size(); i++) {
			XmlDot dot = dots.get(i);
			Square square = new Square();
			square.setPosition(Vec.add(
					Vec.add(translation,
							Vec.multiply(dot.getPosition(), Util.BRICK_SIZE)),
					new Vec(0, 0, sizeBrick.z() * Util.BRICK_HEIGHT
							+ Util.BRICK_DOT_HEIGHT)));
			square.setWidth(Util.BRICK_SIZE);
			square.setHeight(Util.BRICK_SIZE);
			square.setIndexSquare("");
			squareOnTopBrick.add(square);
			Util.LIST_SQUARE_ON_TOP_BRICKS.add(square);
		}
	}

	/**
	 * Generate an array square place expand of this brick according to brick
	 * follow mouse
	 * 
	 * @param brickFollowMouse
	 *            The brick that following mouse
	 * @param squareExpand
	 *            An array square input
	 */
	public void generatePlaceInSpecialCase(Brick brickFollowMouse,
			ArrayList<Square> squareExpand) {
		if (brickFollowMouse == null)
			return;

		int w = (int) brickFollowMouse.getSizeBrick().x();
		int h = (int) brickFollowMouse.getSizeBrick().y();

		if (xmlBrick == null)
			return;
		if (xmlBrick.getRotations().size() == 0)
			return;

		int numberOfDot;
		ArrayList<XmlDot> xmlDots = new ArrayList<XmlDot>();
		numberOfDot = xmlBrick.getRotations().get(timesRotation)
				.getNumberOfDot();
		xmlDots = xmlBrick.getRotations().get(timesRotation).getDots();

		for (int k = 0; k < numberOfDot; k++) {
			XmlDot dot = xmlDots.get(k);

			Vec startGeneratePosition = new Vec(this.translation.x()
					+ dot.getPosition().x() * Util.BRICK_SIZE,
					this.translation.y() + dot.getPosition().y()
							* Util.BRICK_SIZE, this.translation.z()
							+ dot.getPosition().z() * Util.BRICK_HEIGHT
							+ Util.BRICK_DOT_HEIGHT + Util.BRICK_HEIGHT
							* getSizeBrick().z());

			for (int i = -(w - 1); i <= 0; i++) {
				for (int j = -(h - 1); j <= 0; j++) {
					if (i == j && j == 0)
						continue;
					Vec framePosition = new Vec(startGeneratePosition.x() + i
							* Util.BRICK_SIZE, startGeneratePosition.y() + j
							* Util.BRICK_SIZE, startGeneratePosition.z());
					Square square = new Square(framePosition, Util.BRICK_SIZE,
							Util.BRICK_SIZE);
					square.setIndexSquare("");
					boolean checkExist = false;

					for (int l = 0; l < Util.LIST_SQUARE_ON_TOP_BRICKS.size(); l++) {
						if (square.compare(Util.LIST_SQUARE_ON_TOP_BRICKS
								.get(l))) {
							checkExist = true;
						}
					}
					if (!checkExist) {
						squareExpand.add(square);
					}

				}
			}
		}
	}
	/**
	 * Setup
	 */
	@Override
	public void setup() {
	}
	/**
	 * Setup
	 * @param tempBrick
	 */
	public void setup(Brick tempBrick) {
		model = tempBrick.getModel();
		modelName = tempBrick.getModelName();
		rotation = Util.newVecFromVec(tempBrick.getRotation());
		scaleRatio = tempBrick.getScaleRatio();
		sizeBrick = Util.newVecFromVec(tempBrick.getSizeBrick());
		translation = Util.newVecFromVec(tempBrick.getTranslation());
		timesRotation = tempBrick.getTimesRotation();
		translateForDrawAfterRotate = tempBrick
				.getTranslateForDrawAfterRotate();
		color = Util.newVecFromVec(tempBrick.getColor());
		translationBeforeRotate = Util.newVecFromVec(translation);
		generateInitData();
	}
	/**
	 * Draw
	 */
	@Override
	public void draw() {

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

	public ArrayList<Vec> generateEightPoint() {
		Vec vec0 = Util.newVecFromVec(translation);
		Vec vec1 = new Vec(translation.x() + sizeBrick.x() * Util.BRICK_SIZE,
				translation.y(), translation.z());
		Vec vec2 = new Vec(translation.x() + sizeBrick.x() * Util.BRICK_SIZE,
				translation.y() + sizeBrick.y() * Util.BRICK_SIZE,
				translation.z());
		Vec vec3 = new Vec(translation.x(), translation.y() + sizeBrick.y()
				* Util.BRICK_SIZE, translation.z());
		Vec vec4 = new Vec(translation.x(), translation.y(), translation.z()
				+ sizeBrick.z() * Util.BRICK_HEIGHT + Util.BRICK_DOT_HEIGHT);
		Vec vec5 = new Vec(translation.x() + sizeBrick.x() * Util.BRICK_SIZE,
				translation.y(), translation.z() + sizeBrick.z()
						* Util.BRICK_HEIGHT + Util.BRICK_DOT_HEIGHT);
		Vec vec6 = new Vec(translation.x() + sizeBrick.x() * Util.BRICK_SIZE,
				translation.y() + sizeBrick.y() * Util.BRICK_SIZE,
				translation.z() + sizeBrick.z() * Util.BRICK_HEIGHT
						+ Util.BRICK_DOT_HEIGHT);
		Vec vec7 = new Vec(translation.x(), translation.y() + sizeBrick.y()
				* Util.BRICK_SIZE, translation.z() + sizeBrick.z()
				* Util.BRICK_HEIGHT + Util.BRICK_DOT_HEIGHT);
		
		ArrayList<Vec> vecs = new ArrayList<Vec>();
		vecs.add(vec0);
		vecs.add(vec1);
		vecs.add(vec2);
		vecs.add(vec3);
		vecs.add(vec4);
		vecs.add(vec5);
		vecs.add(vec6);
		vecs.add(vec7);
		
		return vecs;
	}

}

