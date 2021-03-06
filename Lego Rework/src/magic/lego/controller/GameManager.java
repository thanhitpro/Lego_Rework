package magic.lego.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import magiclab.lego.brick.Brick;
import magiclab.lego.brick.BrickFactory;
import magiclab.lego.brick.object.Brick_1x2;
import magiclab.lego.core.Box;
import magiclab.lego.core.BoxSelected;
import magiclab.lego.core.ObjectSelected;
import magiclab.lego.core.Rect;
import magiclab.lego.core.Square;
import magiclab.lego.core.UndoRedo;
import magiclab.lego.gameState.GroupBrickState;
import magiclab.lego.plane.PlaneLego;
import magiclab.lego.util.Util;
import magiclab.lego.xml.XmlDot;
import magiclab.lego.xml.XmlRotation;
import magiclab.main.GuiGame;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PMatrix3D;
import processing.core.PShape;
import processing.opengl.PGraphics3D;
import remixlab.dandelion.geom.Vec;

public class GameManager {
	/**
	 * Board game of Lego
	 */
	private PlaneLego planeLego;
	/**
	 * All brick that placed on board
	 */
	private ArrayList<Brick> bricks;
	/**
	 * The brick is following the mouse
	 */
	private Brick brickFollowMouse;
	/**
	 * Disable the brick following mouse flag
	 */
	private boolean disableBrickFollowMouse;
	/**
	 * A dictionary stores all obj model of Lego game
	 */
	private Dictionary<String, PShape> brickModelDictionary;
	/**
	 * A dictionary stores all brick faces of Lego game
	 */
	private Dictionary<String, PShape[]> brickFacesDictionary;

	/**
	 * A factory is responsible create a new brick
	 */
	private BrickFactory brickFactory;
	/**
	 * The applet parent
	 */
	private PApplet parent;
	/**
	 * The current brick of this game
	 */
	private Brick curBrick;
	/**
	 * Current color
	 */
	private Vec curColor;
	/**
	 * An array undo brick;
	 */
	private ArrayList<Brick> undoBricks;
	/**
	 * An array square expand
	 */
	private ArrayList<Square> squareExpand;
	/**
	 * When player place a new brick on plane, all brick will be reset delete.
	 */
	private boolean resetDeletedFlag;
	/**
	 * Object selected by mouse
	 */
	private ObjectSelected objectHovered;
	/**
	 * Object selected previous by mouse
	 */
	private ObjectSelected objectHoveredPrev;
	/**
	 * Enable when brick rotated
	 */
	private boolean rotateBrickFlag;
	/**
	 * Enable when switch brick
	 */
	private boolean switchBrick;
	/**
	 * Enable when a brick is selected
	 */
	private boolean selectedBrickFlag;
	/**
	 * Brick ID of the selected brick
	 */
	private int selectedBrickID;
	private ArrayList<Integer> selectedBrickIDMulti;

	/*
	 * Array list stores the selected bricks
	 */
	public ArrayList<Integer> getSelectedBrickIDMulti() {
		return selectedBrickIDMulti;
	}

	/**
	 * Set Selected Brick ID Multi
	 * 
	 * @param selectedBrickIDMulti
	 */
	public void setSelectedBrickIDMulti(ArrayList<Integer> selectedBrickIDMulti) {
		this.selectedBrickIDMulti = selectedBrickIDMulti;
	}

	/*
	 * Array list stores the copied, cut bricks
	 */
	private ArrayList<Brick> copiedBrickIDMulti;

	/**
	 * Get Copyed Brick ID Multi
	 * 
	 * @return
	 */
	public ArrayList<Brick> getCopyedBrickIDMulti() {
		return copiedBrickIDMulti;
	}

	/**
	 * Set Copyed Brick ID Multi
	 * 
	 * @param copyedBrickIDMulti
	 */
	public void setCopyedBrickIDMulti(ArrayList<Brick> copyedBrickIDMulti) {
		this.copiedBrickIDMulti = copyedBrickIDMulti;
	}

	/**
	 * Brick selected previous
	 */
	private Brick brickSelectedOld;
	/**
	 * Enable when finish loading new game
	 */
	private boolean finishLoadingNewGame;
	/**
	 * Process menu action
	 */
	private MenuController menuController;
	/**
	 * Expand size
	 */
	private Vec expandSize;
	/*
	 * Game modified
	 */
	private boolean gameModified;
	/*
	 * Hold ctr key
	 */
	private boolean holdControlKey;
	/*
	 * Group brick follow mouse
	 */
	private GroupBrickState groupBrickStates;
	/**
	 * Undo Redo
	 */
	private UndoRedo undoRedo;
	/**
	 * Hold Select Key
	 */
	private boolean holdSelectKey = false;
	/**
	 * Mouse Drag
	 */
	private boolean mouseDrag = false;
	private int testCase = 1316;
	/*
	 * Box selected
	 */
	private BoxSelected boxSelected;
	PGraphics3D g3;

	/**
	 * Game Manager
	 * 
	 * @param parent
	 */
	public GameManager(PApplet parent) {
		super();
		this.parent = parent;
	}

	/**
	 * Reset Game
	 */
	public void resetGame() {
		bricks.clear();
		bricks = new ArrayList<Brick>();
		undoBricks = new ArrayList<Brick>();
		curBrick = new Brick_1x2();
		curBrick.setModelName(Util.DEFAULT_BRICK_NAME);
		curColor = new Vec(230, 0, 0);
		squareExpand = new ArrayList<Square>();
		objectHovered = new ObjectSelected();
		objectHoveredPrev = new ObjectSelected();
		finishLoadingNewGame = true;
		Util.LIST_SQUARE_ON_TOP_BRICKS.clear();
		brickSelectedOld = null;
		selectedBrickID = -1;
		selectedBrickIDMulti.clear();
		selectedBrickFlag = false;
		gameModified = false;
		holdControlKey = false;
		selectedBrickIDMulti = new ArrayList<>();
		copiedBrickIDMulti = new ArrayList<Brick>();
		groupBrickStates = new GroupBrickState();
		undoRedo = new UndoRedo();
		undoRedo.setBricks(bricks);
		boxSelected = new BoxSelected();
	}

	/**
	 * Setup
	 */
	public void setup() {
		brickFactory = new BrickFactory();
		planeLego = new PlaneLego();
		planeLego.setup();
		bricks = new ArrayList<Brick>();
		undoBricks = new ArrayList<Brick>();
		setupBrickModel();
		setupBrickFactory();
		curBrick = new Brick_1x2();
		curBrick.setModelName(Util.DEFAULT_BRICK_NAME);
		curColor = new Vec(230, 0, 0);
		squareExpand = new ArrayList<Square>();
		objectHovered = new ObjectSelected();
		objectHoveredPrev = new ObjectSelected();
		finishLoadingNewGame = true;
		menuController = new MenuController(this);
		Util.tempImage = parent.loadImage("circle_2.png");
		gameModified = false;
		holdControlKey = false;
		selectedBrickIDMulti = new ArrayList<>();
		copiedBrickIDMulti = new ArrayList<Brick>();
		groupBrickStates = new GroupBrickState();
		undoRedo = new UndoRedo();
		undoRedo.setBricks(bricks);
		boxSelected = new BoxSelected();
		g3 = (PGraphics3D) parent.g;
	}

	/**
	 * Setup Brick Model
	 */
	private void setupBrickModel() {
		brickModelDictionary = new Hashtable<String, PShape>();
		brickFacesDictionary = new Hashtable<String, PShape[]>();
		for (int i = 0; i < Util.MODEL_NAME_LIST.size(); i++) {
			if (Util.MODEL_NAME_LIST.get(i).charAt(0) == '_') {
				continue;
			}
			PShape objModel = parent.loadShape(Util.MODEL_NAME_LIST.get(i)
					+ ".obj");
			PShape[] faces = createModel(objModel);
			brickFacesDictionary.put(Util.MODEL_NAME_LIST.get(i), faces);
			setTextureModel(faces, Util.tempImage);
			objModel.scale(Util.MODEL_SCALE);
			brickModelDictionary.put(Util.MODEL_NAME_LIST.get(i), objModel);
		}
	}

	/**
	 * Setup Brick Factory
	 */
	private void setupBrickFactory() {
		for (int i = 0; i < Util.MODEL_NAME_LIST.size(); i++) {
			if (Util.MODEL_NAME_LIST.get(i).charAt(0) == '_') {
				continue;
			}
			try {
				brickFactory.registerBrick(
						Util.MODEL_NAME_LIST.get(i),
						Class.forName(Util.PLUS_PATH_CLASS_NAME
								+ Util.MODEL_NAME_LIST.get(i)));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Generate Place In Special Case
	 * 
	 * @param brick
	 */
	private void generatePlaceInSpecialCase(Brick brick) {
		if (brickSelectedOld == null
				|| brick.getId() != brickSelectedOld.getId()
				|| (brick.getId() == brickSelectedOld.getId() && rotateBrickFlag)
				|| switchBrick) {
			squareExpand = new ArrayList<Square>();
			brick.generatePlaceInSpecialCase(brickFollowMouse, squareExpand);
			switchBrick = false;
		}
	}

	/**
	 * Undo
	 */
	public void undo() {

		undoRedo.undo(1);

		brickSelectedOld = null;
		selectedBrickID = -1;
		selectedBrickIDMulti.clear();
		selectedBrickFlag = false;
		gameModified = true;
		objectHovered.setIndexNameObject(-1);
	}

	/**
	 * Add Brick To Undo List
	 * 
	 * @param lastBrick
	 */
	private void addBrickToUndoList(Brick lastBrick) {
		undoBricks.add(lastBrick);
	}

	/**
	 * Delete
	 * 
	 * @param brickSelected
	 */
	public void delete(int brickSelected) {
		Brick selectedBrick = bricks.get(brickSelected);
		selectedBrick.setDeleteFlag(true);
		addBrickToUndoList(selectedBrick);
		bricks.remove(brickSelected);
		gameModified = true;
	}

	/**
	 * Redo
	 */
	public void redo() {
		undoRedo.redo(1);

		brickSelectedOld = null;
		selectedBrickID = -1;
		selectedBrickIDMulti.clear();
		selectedBrickFlag = false;
		gameModified = true;
		objectHovered.setIndexNameObject(-1);
	}

	/**
	 * Set ID For Brick
	 * 
	 * @param brick
	 */
	private void setIDForBrick(Brick brick) {
		if (bricks.size() > 0) {
			brick.setId(bricks.get(bricks.size() - 1).getId() + 1);
		} else {
			brick.setId(0);
		}
	}

	/**
	 * Setup Plane
	 */
	public void setupPlane() {
		PShape pShape;
		// pShape = parent.loadShape("circle95.svg");
		pShape = parent.createShape(PConstants.RECT, 0, 0, 20, 20);
		for (int i = -Util.LEFT_WIDTH; i < Util.RIGHT_WIDTH; i++) {
			for (int j = -Util.UP_HEIGHT; j < Util.DOWN_HEIGHT; j++) {

				/*
				 * Roofing square for the plane Each square has size 20x20
				 */
				Square squarePosition = new Square();
				squarePosition.setWidth(Util.BRICK_SIZE);
				squarePosition.setHeight(Util.BRICK_SIZE);
				squarePosition.setPosition(new Vec(Util.BRICK_SIZE * i,
						Util.BRICK_SIZE * j, 0));
				squarePosition.setIndexSquare(squarePosition.getPosition().x()
						+ "x" + squarePosition.getPosition().y());

				pShape.setTexture(Util.tempImage);
				pShape.setStroke(false);
				planeLego.addShape(pShape);
				planeLego.addSquarePosition(squarePosition);
			}
		}

		planeLego.generateFourPoint();
	}

	/**
	 * Draw Plane
	 */
	public void drawPlane() {
		for (int i = 0; i < planeLego.getpShapes().size(); i++) {
			// Draw interactive frame on screen
			parent.pushMatrix();
			// planeLego.getpShapes().get(i);
			// Draw plane
			parent.shape(planeLego.getpShapes().get(i), planeLego
					.getpSquarePosition().get(i).getPosition().x(), planeLego
					.getpSquarePosition().get(i).getPosition().y());
			parent.popMatrix();
		}
	}

	/**
	 * Check Collision Point And Box
	 * 
	 * @param pos
	 * @return
	 */
	public int checkCollisionPointAndBox(Vec pos) {
		if (squareExpand.size() > 0) {
			for (int i = 0; i < squareExpand.size(); i++) {
				if (pos.z() <= squareExpand.get(i).getPosition().z()
						&& pos.z() > (squareExpand.get(i).getPosition().z() - Util.BRICK_DOT_HEIGHT)
						&& squareExpand.get(i).container(pos)) {
					objectHovered.setIndexNameObject(3);
					objectHovered.setIndexObject(i);
					return i;
				}
			}
		}

		for (int i = 0; i < bricks.size(); i++) {
			for (int j = 0; j < bricks.get(i).getSquareOnTopBrick().size(); j++) {
				if (pos.z() <= bricks.get(i).getSquareOnTopBrick().get(j)
						.getPosition().z()) {
					if (pos.z() > (bricks.get(i).getSquareOnTopBrick().get(j)
							.getPosition().z() - Util.BRICK_DOT_HEIGHT)) {
						if (bricks.get(i).getSquareOnTopBrick().get(j)
								.container(pos)) {
							objectHovered.setIndexNameObject(0);
							objectHovered.setIndexObject(i);
							objectHovered.setIndexDot(j);
							disableBrickFollowMouse = false;
							return i;
						}
					}
				}
			}

			for (int j = 0; j < bricks.get(i).getBoxCollider().getBoxes()
					.size(); j++) {
				Vec tempVec = Util.newVecFromVec(bricks.get(i).getBoxCollider()
						.getBoxes().get(j).getPosition());
				Box box = new Box();
				box.setPosition(tempVec);
				box.setWidth(bricks.get(i).getBoxCollider().getBoxes().get(j)
						.getWidth());
				box.setHeight(bricks.get(i).getBoxCollider().getBoxes().get(j)
						.getHeight());
				box.setDepth(bricks.get(i).getBoxCollider().getBoxes().get(j)
						.getDepth());
				if (box.Container(pos)) {
					objectHovered.setIndexNameObject(1);
					objectHovered.setIndexObject(i);
					return i;
				}
			}
		}

		if (pos.z() <= 0) {
			for (int i = 0; i < planeLego.getpSquarePosition().size(); i++) {
				if (planeLego.getpSquarePosition().get(i).container(pos)) {
					objectHovered.setIndexNameObject(2);
					objectHovered.setIndexObject(i);
					return i;
				}
			}
		}

		objectHovered.setIndexNameObject(-1);
		objectHovered.setIndexObject(-1);

		return -1;
	}

	public int getTestCase() {
		return testCase;
	}

	public void setTestCase(int testCase) {
		this.testCase = testCase;
	}

	/**
	 * Get Plane Lego
	 * 
	 * @return
	 */
	public PlaneLego getPlaneLego() {
		return planeLego;
	}

	/**
	 * Set Plane Lego
	 * 
	 * @param planeLego
	 */
	public void setPlaneLego(PlaneLego planeLego) {
		this.planeLego = planeLego;
	}

	/**
	 * Get Bricks
	 * 
	 * @return
	 */
	public ArrayList<Brick> getBricks() {
		return bricks;
	}

	/**
	 * Set Bricks
	 * 
	 * @param bricks
	 */
	public void setBricks(ArrayList<Brick> bricks) {
		this.bricks = bricks;
	}

	/**
	 * Get Brick Follow Mouse
	 * 
	 * @return
	 */
	public Brick getBrickFollowMouse() {
		return brickFollowMouse;
	}

	/**
	 * Set Brick Follow Mouse
	 * 
	 * @param brickFollowMouse
	 */
	public void setBrickFollowMouse(Brick brickFollowMouse) {
		this.brickFollowMouse = brickFollowMouse;
	}

	/**
	 * Is Disable Brick Follow Mouse
	 * 
	 * @return
	 */
	public boolean isDisableBrickFollowMouse() {
		return disableBrickFollowMouse;
	}

	/**
	 * Set Disable Brick Follow Mouse
	 * 
	 * @param disableBrickFollowMouse
	 */
	public void setDisableBrickFollowMouse(boolean disableBrickFollowMouse) {
		this.disableBrickFollowMouse = disableBrickFollowMouse;
	}

	/**
	 * Get Brick Model Dictionary
	 * 
	 * @return
	 */
	public Dictionary<String, PShape> getBrickModelDictionary() {
		return brickModelDictionary;
	}

	/**
	 * Set Brick Model Dictionary
	 * 
	 * @param brickModelDictionary
	 */
	public void setBrickModelDictionary(
			Dictionary<String, PShape> brickModelDictionary) {
		this.brickModelDictionary = brickModelDictionary;
	}

	/**
	 * Get Brick Factory
	 * 
	 * @return
	 */
	public BrickFactory getBrickFactory() {
		return brickFactory;
	}

	/**
	 * Set Brick Factory
	 * 
	 * @param brickFactory
	 */
	public void setBrickFactory(BrickFactory brickFactory) {
		this.brickFactory = brickFactory;
	}

	/**
	 * Get Parent
	 * 
	 * @return
	 */
	public PApplet getParent() {
		return parent;
	}

	/**
	 * Set Parent
	 * 
	 * @param parent
	 */
	public void setParent(PApplet parent) {
		this.parent = parent;
	}

	/**
	 * Get Cur Brick
	 * 
	 * @return
	 */
	public Brick getCurBrick() {
		return curBrick;
	}

	/**
	 * Set Cur Brick
	 * 
	 * @param curBrick
	 */
	public void setCurBrick(Brick curBrick) {
		this.curBrick = curBrick;
	}

	/**
	 * Get Cur Color
	 * 
	 * @return
	 */
	public Vec getCurColor() {
		return curColor;
	}

	/**
	 * Set Cur Color
	 * 
	 * @param curColor
	 */
	public void setCurColor(Vec curColor) {
		this.curColor = Util.newVecFromVec(curColor);
	}

	/**
	 * Get Undo Bricks
	 * 
	 * @return
	 */
	public ArrayList<Brick> getUndoBricks() {
		return undoBricks;
	}

	/**
	 * Set Undo Bricks
	 * 
	 * @param undoBricks
	 */
	public void setUndoBricks(ArrayList<Brick> undoBricks) {
		this.undoBricks = undoBricks;
	}

	/**
	 * Get Square Expand
	 * 
	 * @return
	 */
	public ArrayList<Square> getSquareExpand() {
		return squareExpand;
	}

	/**
	 * Set Square Expand
	 * 
	 * @param squareExpand
	 */
	public void setSquareExpand(ArrayList<Square> squareExpand) {
		this.squareExpand = squareExpand;
	}

	/**
	 * Is Reset Deleted Flag
	 * 
	 * @return
	 */
	public boolean isResetDeletedFlag() {
		return resetDeletedFlag;
	}

	/**
	 * Set Reset Deleted Flag
	 * 
	 * @param resetDeletedFlag
	 */
	public void setResetDeletedFlag(boolean resetDeletedFlag) {
		this.resetDeletedFlag = resetDeletedFlag;
	}

	/**
	 * Get Object Selected
	 * 
	 * @return
	 */
	public ObjectSelected getObjectSelected() {
		return objectHovered;
	}

	/**
	 * 
	 * @param objectSelected
	 */
	public void setObjectSelected(ObjectSelected objectSelected) {
		this.objectHovered = objectSelected;
	}

	/**
	 * Get Object Selected Prev
	 * 
	 * @return
	 */
	public ObjectSelected getObjectSelectedPrev() {
		return objectHoveredPrev;
	}

	/**
	 * Set Object Selected Prev
	 * 
	 * @param objectSelectedPrev
	 */
	public void setObjectSelectedPrev(ObjectSelected objectSelectedPrev) {
		this.objectHoveredPrev = objectSelectedPrev;
	}

	/**
	 * Get Object Hovered
	 * 
	 * @return
	 */
	public ObjectSelected getObjectHovered() {
		return objectHovered;
	}

	/**
	 * Set Object Hovered
	 * 
	 * @param objectHovered
	 */
	public void setObjectHovered(ObjectSelected objectHovered) {
		this.objectHovered = objectHovered;
	}

	/**
	 * Get Object Hovered Prev
	 * 
	 * @return
	 */
	public ObjectSelected getObjectHoveredPrev() {
		return objectHoveredPrev;
	}

	/**
	 * Set Object Hovered Prev
	 * 
	 * @param objectHoveredPrev
	 */
	public void setObjectHoveredPrev(ObjectSelected objectHoveredPrev) {
		this.objectHoveredPrev = objectHoveredPrev;
	}

	/**
	 * Is Rotate Brick Flag
	 * 
	 * @return
	 */
	public boolean isRotateBrickFlag() {
		return rotateBrickFlag;
	}

	/**
	 * Set Rotate Brick Flag
	 * 
	 * @param rotateBrickFlag
	 */
	public void setRotateBrickFlag(boolean rotateBrickFlag) {
		this.rotateBrickFlag = rotateBrickFlag;
	}

	/**
	 * Is Switch Brick
	 * 
	 * @return
	 */
	public boolean isSwitchBrick() {
		return switchBrick;
	}

	/**
	 * Set Switch Brick
	 * 
	 * @param switchBrick
	 */
	public void setSwitchBrick(boolean switchBrick) {
		this.switchBrick = switchBrick;
	}

	/**
	 * Is Selected Brick Flag
	 * 
	 * @return
	 */
	public boolean isSelectedBrickFlag() {
		return selectedBrickFlag;
	}

	/**
	 * Set Selected Brick Flag
	 * 
	 * @param selectedBrickFlag
	 */
	public void setSelectedBrickFlag(boolean selectedBrickFlag) {
		this.selectedBrickFlag = selectedBrickFlag;
	}

	/**
	 * Get Selected Brick ID
	 * 
	 * @return
	 */
	public int getSelectedBrickID() {
		return selectedBrickID;
	}

	/**
	 * Set Selected Brick ID
	 * 
	 * @param selectedBrickID
	 */
	public void setSelectedBrickID(int selectedBrickID) {
		this.selectedBrickID = selectedBrickID;
	}

	/**
	 * Get Brick Selected Old
	 * 
	 * @return
	 */
	public Brick getBrickSelectedOld() {
		return brickSelectedOld;
	}

	/**
	 * Set Brick Selected Old
	 * 
	 * @param brickSelectedOld
	 */
	public void setBrickSelectedOld(Brick brickSelectedOld) {
		this.brickSelectedOld = brickSelectedOld;
	}

	/**
	 * Is Finish Loading New Game
	 * 
	 * @return
	 */
	public boolean isFinishLoadingNewGame() {
		return finishLoadingNewGame;
	}

	/**
	 * Set Finish Loading New Game
	 * 
	 * @param finishLoadingNewGame
	 */
	public void setFinishLoadingNewGame(boolean finishLoadingNewGame) {
		this.finishLoadingNewGame = finishLoadingNewGame;
	}

	/**
	 * Get Menu Controller
	 * 
	 * @return
	 */
	public MenuController getMenuController() {
		return menuController;
	}

	/**
	 * Set Menu Controller
	 * 
	 * @param menuController
	 */
	public void setMenuController(MenuController menuController) {
		this.menuController = menuController;
	}

	/**
	 * Get Expand Size
	 * 
	 * @return
	 */
	public Vec getExpandSize() {
		return expandSize;
	}

	/**
	 * Set Expand Size
	 * 
	 * @param expandSize
	 */
	public void setExpandSize(Vec expandSize) {
		this.expandSize = expandSize;
	}

	/**
	 * Draw Brick Follow Mouse
	 */
	public void drawBrickFollowMouse() {
		if (holdControlKey && groupBrickStates.getBrickStates().size() < 0) {
			disableBrickFollowMouse = true;
		}
		if (brickFollowMouse != null && disableBrickFollowMouse == false) {
			parent.pushMatrix();

			parent.translate(brickFollowMouse.getTranslation().x()
					+ brickFollowMouse.getCalibrateVec().x(), brickFollowMouse
					.getTranslation().y()
					+ brickFollowMouse.getCalibrateVec().y(), brickFollowMouse
					.getTranslation().z()
					+ brickFollowMouse.getCalibrateVec().z());
			if (brickFollowMouse.getTranslateForDrawAfterRotate() != null) {
				parent.translate(brickFollowMouse
						.getTranslateForDrawAfterRotate().x(), brickFollowMouse
						.getTranslateForDrawAfterRotate().y(), brickFollowMouse
						.getTranslateForDrawAfterRotate().z());
			}

			parent.scale(brickFollowMouse.getScaleRatio());
			parent.rotateX(brickFollowMouse.getRotation().x());
			parent.rotateY(brickFollowMouse.getRotation().y());
			brickFollowMouse.getModel().setFill(
					parent.color(brickFollowMouse.getColor().x(),
							brickFollowMouse.getColor().y(), brickFollowMouse
									.getColor().z()));
			// brickFollowMouse.getModel().setTexture(Util.tempImage);
			// brickFollowMouse.getModel().disableMaterial();
			parent.shape(brickFollowMouse.getModel());
			/*
			 * PShape[] faces = brickFacesDictionary.get(brickFollowMouse
			 * .getModelName()); for (int i = 0; i < faces.length; i++) {
			 * parent.shape(faces[i]); }
			 */
			parent.popMatrix();

			if (groupBrickStates.getBrickStates().size() > 0) {
				for (int i = 0; i < groupBrickStates.getBrickStates().size(); i++) {
					Brick tempBrick = groupBrickStates.getBrickStates().get(i);
					tempBrick.setTranslation(Vec.subtract(
							groupBrickStates.getConnectPoint(),
							groupBrickStates.getBrickPosition().get(i)));
					parent.pushMatrix();

					parent.translate(tempBrick.getTranslation().x()
							+ tempBrick.getCalibrateVec().x(), tempBrick
							.getTranslation().y()
							+ tempBrick.getCalibrateVec().y(), tempBrick
							.getTranslation().z()
							+ tempBrick.getCalibrateVec().z());
					if (tempBrick.getTranslateForDrawAfterRotate() != null) {
						parent.translate(tempBrick
								.getTranslateForDrawAfterRotate().x(),
								tempBrick.getTranslateForDrawAfterRotate().y(),
								tempBrick.getTranslateForDrawAfterRotate().z());
					}

					parent.scale(tempBrick.getScaleRatio());
					parent.rotateX(tempBrick.getRotation().x());
					parent.rotateY(tempBrick.getRotation().y());
					brickFollowMouse.getModel().setFill(
							parent.color(tempBrick.getColor().x(), tempBrick
									.getColor().y(), tempBrick.getColor().z()));
					parent.shape(tempBrick.getModel());
					parent.popMatrix();
				}
			}
		}

	}

	/**
	 * Create Model
	 * 
	 * @param parent
	 * @return
	 */
	PShape[] createModel(PShape parent) {
		PShape[] faces;
		faces = new PShape[parent.getChildCount()];
		for (int i = 0; i < faces.length; i++) {
			faces[i] = parent.getChild(i);
		}
		return faces;
	}

	/**
	 * Set Texture Model
	 * 
	 * @param faces
	 * @param img
	 */
	void setTextureModel(PShape[] faces, PImage img) {
		for (int i = 0; i < faces.length; i++) {
			PShape s = parent.createShape();
			s.beginShape();
			s.textureMode(PConstants.NORMAL);
			s.texture(img);
			for (int j = 0; j < faces[i].getVertexCount(); j++) {
				int r1 = (int) PApplet.constrain(parent.random(3), 0, 1);
				int r2 = (int) PApplet.constrain(parent.random(3), 0, 1);
				s.vertex(faces[i].getVertex(j).x, faces[i].getVertex(j).y,
						faces[i].getVertex(j).z, r1, r2);
			}
			s.endShape();
			faces[i] = s;
		}
	}

	/**
	 * Display Model
	 * 
	 * @param faces
	 */
	void displayModel(PShape[] faces) {
		for (int i = 0; i < faces.length; i++) {
			parent.shape(faces[i]);
		}
	}

	/**
	 * Mouse Hover Process
	 */
	public void mouseHoverProcess() {
		if (holdSelectKey && mouseDrag) {
			disableBrickFollowMouse = true;
			return;
		}
		/**
		 * Hover on a plane
		 */
		if (objectHovered.getIndexNameObject() == 2) {
			mouseHoverPlaneProcess();
			disableBrickFollowMouse = false;
		}
		if (objectHovered.getIndexNameObject() == 1) {
			mouseHoverBrickProcess();
			disableBrickFollowMouse = true;
		}
		if (objectHovered.getIndexNameObject() == 0) {
			mouseHoverDotBrickProcess();
			disableBrickFollowMouse = false;
			generatePlaceInSpecialCase(bricks.get(objectHovered
					.getIndexObject()));
			brickSelectedOld = bricks.get(objectHovered.getIndexObject());

			if (brickFollowMouse.getModelName().contains("Invert")) {
				ArrayList<XmlDot> botdot = brickFollowMouse.getXmlBrick()
						.getRotations()
						.get(brickFollowMouse.getTimesRotation()).getBotdots();

				int count = 0;
				for (int i = 0; i < botdot.size(); i++) {
					Vec dotPosition = Vec.add(Vec.add(brickFollowMouse
							.getTranslation(), Vec.multiply(botdot.get(i)
							.getPosition(), Util.BRICK_SIZE)), new Vec(0, 0,
							+Util.BRICK_DOT_HEIGHT));
					for (int j = 0; j < Util.LIST_SQUARE_ON_TOP_BRICKS.size(); j++) {
						if (dotPosition.equals(Util.LIST_SQUARE_ON_TOP_BRICKS
								.get(j).getPosition())) {
							count++;
						}
					}

					for (int j = 0; j < squareExpand.size(); j++) {
						if (dotPosition.equals(squareExpand.get(j))) {
							count++;
						}
					}
				}

				if (count == 0) {
					disableBrickFollowMouse = true;
					return;
				}

			}
		}
		if (objectHovered.getIndexNameObject() == 3) {
			mouseHoverDotExpandProcess();
			disableBrickFollowMouse = false;
			if (brickFollowMouse.getModelName().contains("Invert")) {
				ArrayList<XmlDot> botdot = brickFollowMouse.getXmlBrick()
						.getRotations()
						.get(brickFollowMouse.getTimesRotation()).getBotdots();

				int count = 0;
				for (int i = 0; i < botdot.size(); i++) {
					Vec dotPosition = Vec.add(Vec.add(brickFollowMouse
							.getTranslation(), Vec.multiply(botdot.get(i)
							.getPosition(), Util.BRICK_SIZE)), new Vec(0, 0,
							+Util.BRICK_DOT_HEIGHT));
					for (int j = 0; j < Util.LIST_SQUARE_ON_TOP_BRICKS.size(); j++) {
						if (dotPosition.equals(Util.LIST_SQUARE_ON_TOP_BRICKS
								.get(j).getPosition())) {
							count++;
						}
					}

					for (int j = 0; j < squareExpand.size(); j++) {
						if (dotPosition.equals(squareExpand.get(j))) {
							count++;
						}
					}
				}

				if (count == 0) {
					disableBrickFollowMouse = true;
					squareExpand.remove(objectHovered.getIndexObject());
					return;
				}

			} else {
				int count = 0;
				ArrayList<XmlDot> topdot = brickFollowMouse.getXmlBrick()
						.getRotations()
						.get(brickFollowMouse.getTimesRotation()).getDots();
				for (int i = 0; i < topdot.size(); i++) {
					Vec dotPosition = Vec.add(Vec.add(brickFollowMouse
							.getTranslation(), Vec.multiply(topdot.get(i)
							.getPosition(), Util.BRICK_SIZE)), new Vec(0, 0,
							+Util.BRICK_DOT_HEIGHT));
					for (int j = 0; j < Util.LIST_SQUARE_ON_TOP_BRICKS.size(); j++) {
						if (dotPosition.equals(Util.LIST_SQUARE_ON_TOP_BRICKS
								.get(j).getPosition())) {
							count++;
						}
					}
				}

				if (count == 0) {
					disableBrickFollowMouse = true;
					squareExpand.remove(objectHovered.getIndexObject());
					return;
				}
			}
		}
		checkCollisionBoxAndBox();
		if (brickFollowMouse != null) {
			groupBrickStates.setConnectPoint(Util
					.newVecFromVec(brickFollowMouse.getTranslation()));
			if (brickFollowMouse.getTimesRotation() == 2) {
				Util.ROTATE_A_CIRCLE_2 = false;
				if (brickFollowMouse.getTranslation() != brickFollowMouse
						.getTranslationBeforeRotate()) {
					Util.LISTEN_CHANGE_POSITION = true;
				}

				if (Util.LISTEN_CHANGE_POSITION) {
					groupBrickStates.getConnectPoint().setX(
							groupBrickStates.getConnectPoint().x()
									+ (brickFollowMouse.getSizeBrick().x() - 1)
									* Util.BRICK_SIZE);
					Util.LISTEN_CHANGE_POSITION = false;
					Util.ROTATE_A_CIRCLE_2 = true;
				}
			}

			if (brickFollowMouse.getTimesRotation() == 3) {
				Util.ROTATE_A_CIRCLE = false;
				if (brickFollowMouse.getTranslation() != brickFollowMouse
						.getTranslationBeforeRotate()) {
					Util.LISTEN_CHANGE_POSITION = true;
				}

				if (Util.LISTEN_CHANGE_POSITION) {
					if (Util.ROTATE_A_CIRCLE_2 == true) {
						groupBrickStates
								.getConnectPoint()
								.setX(groupBrickStates.getConnectPoint().x()
										+ (brickFollowMouse.getSizeBrick().y() - 1)
										* Util.BRICK_SIZE);
					}
					groupBrickStates.getConnectPoint().setY(
							groupBrickStates.getConnectPoint().y()
									+ (brickFollowMouse.getSizeBrick().y() - 1)
									* Util.BRICK_SIZE);
					Util.LISTEN_CHANGE_POSITION = false;
					Util.ROTATE_A_CIRCLE = true;
				}
			}

			if (brickFollowMouse.getTimesRotation() == 0
					&& Util.ROTATE_A_CIRCLE) {
				groupBrickStates.setBrickPosition(groupBrickStates
						.getBrickPositionBeforeRotate());
				Util.ROTATE_A_CIRCLE = false;
			}

		}
	}

	/**
	 * MouseHover Dot Expand Process
	 */
	private void mouseHoverDotExpandProcess() {
		displayBrickFollowMouse(squareExpand
				.get(objectHovered.getIndexObject()));
	}

	/**
	 * Mouse Hover Dot Brick Process
	 */
	private void mouseHoverDotBrickProcess() {
		displayBrickFollowMouse(bricks.get(objectHovered.getIndexObject())
				.getSquareOnTopBrick().get(objectHovered.getIndexDot()));
		/* System.out.println("Dot: " + objectHovered.getIndexObject()); */
	}

	/**
	 * Mouse Hover Brick Process
	 */
	private void mouseHoverBrickProcess() {
		disableBrickFollowMouse = true;
	}

	/**
	 * Mouse Hover Plane Process
	 */
	private void mouseHoverPlaneProcess() {
		Square squareHovered = planeLego.getpSquarePosition().get(
				objectHovered.getIndexObject());
		displayBrickFollowMouse(squareHovered);
		/*
		 * Check remove expanded plane area
		 */
		// Left

		if (disableBrickFollowMouse)
			return;
		if (Util.LEFT_WIDTH > -(squareHovered.getPosition().x() / Util.BRICK_SIZE)
				&& Util.LEFT_WIDTH > Util.LEFT_WIDTH_DEFAULT) {
			int leftValueReduce = (Util.LEFT_WIDTH - (int) (-squareHovered
					.getPosition().x() / Util.BRICK_SIZE));
			Util.LEFT_WIDTH -= leftValueReduce;
			if (Util.LEFT_WIDTH < Util.LEFT_WIDTH_DEFAULT) {
				Util.LEFT_WIDTH = Util.LEFT_WIDTH_DEFAULT;
			}
			// System.out.println("l0: " + leftValueReduce);
		}

		// Right
		if (Util.RIGHT_WIDTH > (squareHovered.getPosition().x() / Util.BRICK_SIZE)
				&& Util.RIGHT_WIDTH > Util.RIGHT_WIDTH_DEFAULT) {
			int rightValueReduce = Util.RIGHT_WIDTH
					- (int) (squareHovered.getPosition().x() / Util.BRICK_SIZE)
					- 1;
			Util.RIGHT_WIDTH -= rightValueReduce;
			if (Util.RIGHT_WIDTH < Util.RIGHT_WIDTH_DEFAULT) {
				Util.RIGHT_WIDTH = Util.RIGHT_WIDTH_DEFAULT;
			}
			// System.out.println("r0: " + rightValueReduce);
		}
		// Up
		if (Util.UP_HEIGHT > -(squareHovered.getPosition().y() / Util.BRICK_SIZE)
				&& Util.UP_HEIGHT > Util.UP_HEIGHT_DEFAULT) {
			int upValueReduce = Util.UP_HEIGHT
					- (int) (-squareHovered.getPosition().y() / Util.BRICK_SIZE);
			Util.UP_HEIGHT -= upValueReduce;
			if (Util.UP_HEIGHT < Util.UP_HEIGHT_DEFAULT) {
				Util.UP_HEIGHT = Util.UP_HEIGHT_DEFAULT;
			}
			// System.out.println("0" + upValueReduce);
		}
		// Down
		if (Util.DOWN_HEIGHT > (squareHovered.getPosition().y() / Util.BRICK_SIZE)
				&& Util.DOWN_HEIGHT > Util.DOWN_HEIGHT_DEFAULT) {
			int downValueReduce = Util.DOWN_HEIGHT
					- (int) (squareHovered.getPosition().y() / Util.BRICK_SIZE)
					- 1;
			Util.DOWN_HEIGHT -= downValueReduce;
			if (Util.DOWN_HEIGHT < Util.DOWN_HEIGHT_DEFAULT) {
				Util.DOWN_HEIGHT = Util.DOWN_HEIGHT_DEFAULT;
			}
			// System.out.println("0: " + downValueReduce);
		}
		if (Util.LEFT_WIDTH >= Util.LEFT_WIDTH_DEFAULT
				|| Util.RIGHT_WIDTH >= Util.RIGHT_WIDTH_DEFAULT
				|| Util.UP_HEIGHT >= Util.UP_HEIGHT_DEFAULT
				|| Util.DOWN_HEIGHT >= Util.DOWN_HEIGHT_DEFAULT) {
			planeLego.setup();
			setupPlane();
		}
	}

	/**
	 * Display Brick Follow Mouse
	 * 
	 * @param squareHovered
	 */
	private void displayBrickFollowMouse(Square squareHovered) {
		Brick brick;

		try {
			brick = brickFactory.createBrick(curBrick.getModelName());
			brick.setModelName(curBrick.getModelName());
			brick.setTranslation(squareHovered.getPosition());
			if (objectHovered.getIndexNameObject() == 0
					|| objectHovered.getIndexNameObject() == 3) {
				brick.getTranslation().subtract(
						new Vec(0, 0, Util.BRICK_DOT_HEIGHT));
			}

			if (brickFollowMouse != null
					&& brick.getModelName() == brickFollowMouse.getModelName()
					&& brickFollowMouse.getTranslateForDrawAfterRotate() != null) {
				brick.setTranslateForDrawAfterRotate(brickFollowMouse
						.getTranslateForDrawAfterRotate());
				brick.setTimesRotation(brickFollowMouse.getTimesRotation());
				brick.setRotation(brickFollowMouse.getRotation());
				brick.setSizeBrick(brickFollowMouse.getSizeBrick());
			} else {
				brick.setTranslateForDrawAfterRotate(new Vec(0, 0, 0));
				brick.setRotation(Util.DEFAULT_ROTATE);
				brick.setTimesRotation(0);
			}

			brick.setScaleRatio(Util.OBJECT_SCALE);
			brick.setModel(brickModelDictionary.get(brick.getModelName()));
			brick.generateBoxCollider();
			brick.setColor(curColor);
			brickFollowMouse = brick;
			if (groupBrickStates.getBrickStates().size() > 0) {
				groupBrickStates.generateBoxCollider();
			}
		} catch (NoSuchMethodException | SecurityException
				| InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Check Collision Box And Box
	 */
	private void checkCollisionBoxAndBox() {
		for (int i = 0; i < bricks.size(); i++) {
			Brick brick = bricks.get(i);
			for (int j = 0; j < brick.getBoundingPointOfBox().getPoints()
					.size(); j++) {
				Vec vec = brick.getBoundingPointOfBox().getPoints().get(j);
				for (int k = 0; k < brickFollowMouse.getBoxCollider()
						.getBoxes().size(); k++) {
					Box box = brickFollowMouse.getBoxCollider().getBoxes()
							.get(k);
					if (box.Container(vec)) {
						disableBrickFollowMouse = true;
						return;
					}
				}

				if (groupBrickStates.getBrickStates().size() > 0) {
					for (int k = 0; k < groupBrickStates.getBrickStates()
							.size(); k++) {
						Brick tempBrick = groupBrickStates.getBrickStates()
								.get(k);
						for (int l = 0; l < tempBrick.getBoxCollider()
								.getBoxes().size(); l++) {
							Box box = tempBrick.getBoxCollider().getBoxes()
									.get(l);
							if (box.Container(vec)) {
								disableBrickFollowMouse = true;
								return;
							}
						}
					}
				}
			}
		}

		if (objectHovered.getIndexNameObject() != 1)
			disableBrickFollowMouse = false;
	}

	/**
	 * Key Pressed Process
	 */
	public void keyPressedProcess() {
		if (parent.keyCode == 37) {
			if (groupBrickStates.getBrickStates().size() > 0) {
				return;
			}
			brickFollowMouse.getRotation().add(0, Util.ROTATE_ANGLE_ADDED, 0);
			brickFollowMouse.decreaseTimesRotate();
			brickFollowMouse.calibrateAfterRotate();
			rotateBrickFlag = true;
			switchBrick = false;
			brickFollowMouse.generateBoxCollider();

			checkCollisionBoxAndBox();

			if (groupBrickStates.getBrickStates().size() > 0) {
				groupBrickStates.decreaseTimesRotate();
				for (int i = 0; i < groupBrickStates.getBrickStates().size(); i++) {
					Brick tempBrick = groupBrickStates.getBrickStates().get(i);
					tempBrick.getRotation().add(0, Util.ROTATE_ANGLE_ADDED, 0);
					Vec temp = Util.newVecFromVec(groupBrickStates
							.getBrickPositionBeforeRotate().get(i));
					tempBrick.decreaseTimesRotate();
					if (groupBrickStates.getTimesRotation() == 1) {
						groupBrickStates.getBrickPosition().get(i)
								.setY(temp.x());
						groupBrickStates.getBrickPosition().get(i)
								.setX(-temp.y());
					}
					if (groupBrickStates.getTimesRotation() == 2) {
						groupBrickStates.getBrickPosition().get(i)
								.setY(-temp.y());
						groupBrickStates.getBrickPosition().get(i)
								.setX(-temp.x());
					}
					if (groupBrickStates.getTimesRotation() == 3) {
						groupBrickStates.getBrickPosition().get(i)
								.setY(-temp.x());
						groupBrickStates.getBrickPosition().get(i)
								.setX(temp.y());
					}
					if (groupBrickStates.getTimesRotation() == 0) {
						groupBrickStates.getBrickPosition().get(i)
								.setY(temp.y());
						groupBrickStates.getBrickPosition().get(i)
								.setX(temp.x());
					} //
					tempBrick.calibrateAfterRotate();
					tempBrick.generateBoxCollider();
				}
			}

		}

		if (parent.keyCode == 39) {

			if (groupBrickStates.getBrickStates().size() > 0) {
				return;
			}

			if (groupBrickStates.getBrickStates().size() > 0) {
				groupBrickStates.increaseTimesRotate();
				for (int i = 0; i < groupBrickStates.getBrickStates().size(); i++) {
					Brick tempBrick = groupBrickStates.getBrickStates().get(i);
					if (groupBrickStates.getTimesRotation() == 0) {
						if (Util.ROTATE_A_CIRCLE) {
							groupBrickStates.setConnectPoint(Util
									.newVecFromVec(brickFollowMouse
											.getTranslation()));
						} else {
							tempBrick
									.getTranslation()
									.setY(tempBrick.getTranslation().y()
											+ (brickFollowMouse.getSizeBrick()
													.y() - 1) * Util.BRICK_SIZE);
						}
					}
					Vec rotateBrick = Util.rotateAroundAPoint((-90),
							groupBrickStates.getConnectPoint(),
							tempBrick.getTranslation());
					groupBrickStates.getBrickPosition().get(i)
							.setY(rotateBrick.y());
					groupBrickStates.getBrickPosition().get(i)
							.setX(rotateBrick.x());
					tempBrick.getRotation().subtract(0,
							Util.ROTATE_ANGLE_ADDED, 0);
					tempBrick.increaseTimesRotate();

					if (groupBrickStates.getTimesRotation() == 2) {
						groupBrickStates
								.getBrickPosition()
								.get(i)
								.setX(groupBrickStates.getBrickPosition()
										.get(i).x()
										+ (tempBrick.getSizeBrick().x() - 1)
										* Util.BRICK_SIZE);
					}

					tempBrick.calibrateAfterRotate();
					tempBrick.generateBoxCollider();

				}
			}

			brickFollowMouse.getRotation().subtract(0, Util.ROTATE_ANGLE_ADDED,
					0);
			brickFollowMouse.increaseTimesRotate();
			brickFollowMouse.calibrateAfterRotate();

			rotateBrickFlag = true;
			switchBrick = false;
			brickFollowMouse.generateBoxCollider();
			checkCollisionBoxAndBox();

		}

		/*
		 * if (Util.KEY_SWITCH_BRICK.indexOf(parent.key) != -1) { // Process
		 * switching brick
		 * curBrick.setModelName(Util.MODEL_NAME_LIST.get((Integer
		 * .parseInt(String.valueOf(parent.key)) - 1))); rotateBrickFlag =
		 * false; switchBrick = true; }
		 */

		if (parent.key == PConstants.DELETE) {
			if (selectedBrickFlag && selectedBrickIDMulti.size() > 0) {
				// System.out.println("Delete");
				deleteBrickByKey();
				selectedBrickFlag = false;
				selectedBrickID = -1;
				selectedBrickIDMulti.clear();
			}
		}

		// Ctrl
		if (parent.keyCode == 17) {
			holdControlKey = true;
		}

		// B
		if (parent.keyCode == 66) {
			holdSelectKey = true;
		}

		if (parent.keyCode == 83) {
			generateBricks();
		}

		if (holdControlKey) {
			// X
			if (parent.keyCode == 88) {
				cut();
			}
			// C
			if (parent.keyCode == 67) {
				copy();
			}
			// P
			if (parent.keyCode == 86) {
				paste();
			}
			// N
			if (parent.keyCode == 78) {
				resetGame();
			}
			// O
			if (parent.keyCode == 79) {
				GuiGame.openFile();
			}
			// S
			if (parent.keyCode == 83) {
				GuiGame.saveGame();
			}
			// Q
			if (parent.keyCode == 81) {
				GuiGame.closeGame();
			}
			// Z
			if (parent.keyCode == 90) {
				undo();
			}
			// Y
			if (parent.keyCode == 89) {
				redo();
			}
			// A
			if (parent.keyCode == 65) {
				selectAll();
			}
		}
	}

	/**
	 * generateBricks
	 */
	public void generateBricks() {
		try {
			int count = 0;
			bricks.clear();
			for (int i = 0; i < 100;) {
				for (int j = -Util.LEFT_WIDTH_DEFAULT; j < Util.RIGHT_WIDTH_DEFAULT;) {
					for (int k = -Util.UP_HEIGHT_DEFAULT; k < Util.DOWN_HEIGHT_DEFAULT;) {
						Brick brickForPlacing;
						brickForPlacing = brickFactory.createBrick("Brick_1x1");
						brickForPlacing.setModelName("Brick_1x1");
						brickForPlacing.setModel(brickModelDictionary
								.get(brickForPlacing.getModelName()));
						brickForPlacing.setTranslateForDrawAfterRotate(new Vec(
								0, 0, 0));
						brickForPlacing.setRotation(Util.DEFAULT_ROTATE);
						brickForPlacing.setTimesRotation(0);
						brickForPlacing.setScaleRatio(Util.OBJECT_SCALE);
						brickForPlacing.setColor(curColor);
						brickForPlacing.setTranslation(new Vec(j
								* Util.BRICK_SIZE, k * Util.BRICK_SIZE, i
								* Util.BRICK_HEIGHT));
						// brickForPlacing.generateInitData();
						bricks.add(brickForPlacing);
						count++;
						if (count > testCase) {
							return;
						}
						k += 2;
					}
					j += 2;
				}
				curColor = new Vec((float) (Math.random() * 100 % 255),
						(float) (Math.random() * 100 % 255),
						(float) (Math.random() * 100 % 255));
				i += 2;
			}
		} catch (NoSuchMethodException | SecurityException
				| InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Select All
	 */
	private void selectAll() {
		selectedBrickIDMulti = new ArrayList<Integer>();
		for (int i = 0; i < bricks.size(); i++) {
			selectedBrickIDMulti.add(i);
		}

		if (selectedBrickIDMulti.size() > 0) {
			selectedBrickFlag = true;
		} else {
			selectedBrickFlag = false;
		}
	}

	/**
	 * Delete Brick By Key
	 */
	private void deleteBrickByKey() {
		for (int i = 0; i < selectedBrickIDMulti.size(); i++) {
			Brick selectedBrick = bricks.get((int) selectedBrickIDMulti.get(i));
			selectedBrick.setDeleteFlag(true);
			removeBrickFromGame((int) selectedBrickIDMulti.get(i));
		}

		ArrayList<Brick> tempBricks = new ArrayList<Brick>();
		for (int i = 0; i < selectedBrickIDMulti.size(); i++) {
			tempBricks.add(bricks.get((int) selectedBrickIDMulti.get(i)));
		}

		for (int i = 0; i < tempBricks.size(); i++) {
			bricks.remove(tempBricks.get(i));
		}
		undoRedo.InsertInUnDoRedoForRemove(tempBricks);

		gameModified = true;
		objectHovered.reset();
	}

	/**
	 * Remove Brick From Game
	 * 
	 * @param brickID
	 */
	private void removeBrickFromGame(int brickID) {
		for (int i = 0; i < bricks.get(brickID).getSquareOnTopBrick().size(); i++) {
			Util.LIST_SQUARE_ON_TOP_BRICKS.remove(bricks.get(brickID)
					.getSquareOnTopBrick().get(i));
		}
		undoBricks.add(bricks.get(brickID));
	}

	/**
	 * Place Brick
	 */
	public void placeBrick() {
		if (holdSelectKey) {
			disableBrickFollowMouse = true;
		}
		if (brickFollowMouse == null || disableBrickFollowMouse)
			return;

		Brick brickForPlacing;
		try {
			brickForPlacing = brickFactory.createBrick(brickFollowMouse
					.getModelName());
			brickForPlacing.setup(brickFollowMouse);
			setIDForBrick(brickForPlacing);
			ArrayList<Brick> addBricks = new ArrayList<Brick>();
			addBricks.add(brickForPlacing);
			bricks.add(brickForPlacing);
			if (groupBrickStates.getBrickStates().size() > 0) {
				for (int i = 0; i < groupBrickStates.getBrickStates().size(); i++) {
					Brick tempBrick = groupBrickStates.getBrickStates().get(i);
					tempBrick.setTranslation(Vec.subtract(brickFollowMouse
							.getTranslation(), groupBrickStates
							.getBrickPosition().get(i)));

					try {
						Brick brickForPlacingGroupBrick = brickFactory
								.createBrick(tempBrick.getModelName());
						brickForPlacingGroupBrick.setup(tempBrick);
						setIDForBrick(brickForPlacingGroupBrick);
						addBricks.add(brickForPlacingGroupBrick);
						bricks.add(brickForPlacingGroupBrick);
					} catch (NoSuchMethodException | SecurityException
							| InstantiationException | IllegalAccessException
							| IllegalArgumentException
							| InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
			// bricks.add(brickForPlacing);

			undoRedo.InsertInUnDoRedoForAdd(addBricks);
			/*
			 * if ((int) brickForPlacing.getTranslation().x() / Util.BRICK_SIZE
			 * < (-Util.LEFT_WIDTH_FIX)) { Util.LEFT_WIDTH = (int)
			 * (-brickForPlacing.getTranslation().x() / Util.BRICK_SIZE);
			 * Util.LEFT_WIDTH_FIX = (int)
			 * (-brickForPlacing.getTranslation().x() / Util.BRICK_SIZE); }
			 * 
			 * if ((int) brickForPlacing.getTranslation().x() / Util.BRICK_SIZE
			 * > Util.RIGHT_WIDTH_FIX) { Util.RIGHT_WIDTH = (int)
			 * (brickForPlacing.getTranslation().x() / Util.BRICK_SIZE);
			 * Util.RIGHT_WIDTH_FIX = (int)
			 * (brickForPlacing.getTranslation().x() / Util.BRICK_SIZE); }
			 * 
			 * if ((int) brickForPlacing.getTranslation().y() / Util.BRICK_SIZE
			 * < (-Util.UP_HEIGHT_FIX)) { Util.UP_HEIGHT = (int)
			 * (-brickForPlacing.getTranslation().y() / Util.BRICK_SIZE);
			 * Util.UP_HEIGHT_FIX = (int) (-brickForPlacing.getTranslation().y()
			 * / Util.BRICK_SIZE); } if ((int)
			 * brickForPlacing.getTranslation().y() / Util.BRICK_SIZE >
			 * Util.DOWN_HEIGHT_FIX) { Util.DOWN_HEIGHT = (int)
			 * (brickForPlacing.getTranslation().y() / Util.BRICK_SIZE);
			 * Util.DOWN_HEIGHT_FIX = (int)
			 * (brickForPlacing.getTranslation().y() / Util.BRICK_SIZE); }
			 */
		} catch (NoSuchMethodException | SecurityException
				| InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

		/*
		 * Draw group brick stated follow mouse
		 */
		/*
		 * if (groupBrickStates.getBrickStates().size() > 0) { for (int i = 0; i
		 * < groupBrickStates.getBrickStates().size(); i++) { Brick tempBrick =
		 * groupBrickStates.getBrickStates().get(i);
		 * tempBrick.setTranslation(Vec.subtract(brickFollowMouse
		 * .getTranslation(), groupBrickStates.getBrickPosition() .get(i)));
		 * 
		 * try { Brick brickForPlacingGroupBrick = brickFactory
		 * .createBrick(tempBrick.getModelName());
		 * brickForPlacingGroupBrick.setup(tempBrick);
		 * setIDForBrick(brickForPlacingGroupBrick);
		 * bricks.add(brickForPlacingGroupBrick); } catch (NoSuchMethodException
		 * | SecurityException | InstantiationException | IllegalAccessException
		 * | IllegalArgumentException | InvocationTargetException e) {
		 * e.printStackTrace(); } } }
		 */
		selectedBrickFlag = false;
		selectedBrickID = -1;
		gameModified = true;
		selectedBrickIDMulti.clear();
		groupBrickStates.reset();
		if (Util.LEFT_WIDTH > Util.LEFT_WIDTH_DEFAULT) {
			Util.LEFT_WIDTH_DEFAULT = Util.LEFT_WIDTH;
		}

		if (Util.RIGHT_WIDTH > Util.RIGHT_WIDTH_DEFAULT) {
			Util.RIGHT_WIDTH_DEFAULT = Util.RIGHT_WIDTH;
		}

		if (Util.UP_HEIGHT > Util.UP_HEIGHT_DEFAULT) {
			Util.UP_HEIGHT_DEFAULT = Util.UP_HEIGHT;
		}

		if (Util.DOWN_HEIGHT > Util.DOWN_HEIGHT_DEFAULT) {
			Util.DOWN_HEIGHT_DEFAULT = Util.DOWN_HEIGHT;
		}
	}

	/**
	 * Select Brick
	 */
	public void selectBrick() {
		if (disableBrickFollowMouse && objectHovered.getIndexNameObject() == 1) {
			boolean selectedBrick = false;
			for (int i = 0; i < selectedBrickIDMulti.size(); i++) {
				if (((int) selectedBrickIDMulti.get(i)) == objectHovered
						.getIndexObject()) {
					selectedBrick = true;
					if (holdControlKey) {
						selectedBrickIDMulti.remove(i);
					} else {
						selectedBrickIDMulti.clear();
					}
					break;
				}
			}

			if (!selectedBrick) {
				if (holdControlKey) {
					if (selectedBrickIDMulti.size() > 0
							&& bricks.get(objectHovered.getIndexObject())
									.getTranslation().z() <= bricks
									.get(selectedBrickIDMulti.get(0))
									.getTranslation().z()) {
						selectedBrickIDMulti.add(0,
								objectHovered.getIndexObject());
					} else {
						selectedBrickIDMulti
								.add(objectHovered.getIndexObject());
					}

				} else {
					selectedBrickIDMulti.clear();
					selectedBrickIDMulti.add(objectHovered.getIndexObject());
				}
			}

			if (selectedBrickIDMulti.size() > 0) {
				selectedBrickFlag = true;
			} else {
				selectedBrickFlag = false;
			}
		}
	}

	/**
	 * Draw Brick On Plane
	 */
	public void drawBrickOnPlane() {
		try {
			for (int i = 0; i < bricks.size(); i++) {
				Brick brick = bricks.get(i);
				parent.pushMatrix();
				parent.translate(brick.getTranslation().x()
						+ brick.getCalibrateVec().x(), brick.getTranslation()
						.y() + brick.getCalibrateVec().y(), brick
						.getTranslation().z() + brick.getCalibrateVec().z());
				if (brick.getTranslateForDrawAfterRotate() != null) {
					parent.translate(
							brick.getTranslateForDrawAfterRotate().x(), brick
									.getTranslateForDrawAfterRotate().y(),
							brick.getTranslateForDrawAfterRotate().z());
				}

				parent.scale(brick.getScaleRatio());
				parent.rotateX(brick.getRotation().x());
				parent.rotateY(brick.getRotation().y());
				// parent.fill(brick.getColor().x(),
				// brick.getColor().y(), brick.getColor().z());
				// brick.getModel().disableMaterial();
				brick.getModel().setFill(
						parent.color(brick.getColor().x(), bricks.get(i)
								.getColor().y(), brick.getColor().z()));
				parent.shape(brick.getModel());
				parent.popMatrix();
			}
		} catch (Exception ex) {

		}
	}

	/**
	 * Draw Box Cover Brick Hovered
	 */
	public void drawBoxCoverBrickHovered() {
		if (!finishLoadingNewGame)
			return;

		if (selectedBrickFlag && selectedBrickIDMulti.size() > 0) {
			for (int i = 0; i < selectedBrickIDMulti.size(); i++) {
				parent.strokeWeight(2);
				parent.stroke(39, 255, 228);
				parent.noFill();
				parent.pushMatrix();
				Brick tempBrick = bricks.get((int) selectedBrickIDMulti.get(i));
				Vec translateVecForBox = new Vec(tempBrick.getSizeBrick().x()
						/ 2 * Util.BRICK_SIZE, tempBrick.getSizeBrick().y() / 2
						* Util.BRICK_SIZE, tempBrick.getSizeBrick().z() / 2
						* Util.BRICK_HEIGHT + 2);
				parent.translate(tempBrick.getTranslation().x()
						+ translateVecForBox.x(),
						bricks.get((int) selectedBrickIDMulti.get(i))
								.getTranslation().y()
								+ translateVecForBox.y(),
						bricks.get((int) selectedBrickIDMulti.get(i))
								.getTranslation().z()
								+ translateVecForBox.z());
				for (int j = 0; j < tempBrick.getBoxCollider().getBoxes()
						.size(); j++) {
					parent.box(tempBrick.getBoxCollider().getBoxes().get(j)
							.getWidth(), tempBrick.getBoxCollider().getBoxes()
							.get(j).getHeight(), tempBrick.getBoxCollider()
							.getBoxes().get(j).getDepth());

				}
				parent.popMatrix();
			}
		}

		if (objectHovered.getIndexNameObject() == 0
				|| objectHovered.getIndexNameObject() == 1) {
			parent.strokeWeight(2);
			parent.stroke(255, 120, 189);
			parent.noFill();
			parent.pushMatrix();
			if (bricks.size() == 0)
				return;
			Brick tempBrick = bricks.get(objectHovered.getIndexObject());
			Vec translateVecForBox = new Vec(tempBrick.getSizeBrick().x() / 2
					* Util.BRICK_SIZE, tempBrick.getSizeBrick().y() / 2
					* Util.BRICK_SIZE, tempBrick.getSizeBrick().z() / 2
					* Util.BRICK_HEIGHT + 3);
			parent.translate(tempBrick.getTranslation().x()
					+ translateVecForBox.x(), tempBrick.getTranslation().y()
					+ translateVecForBox.y(), tempBrick.getTranslation().z()
					+ translateVecForBox.z());
			if (tempBrick.getBoxCollider().getBoxes().size() > 1) {
				Vec maxSize = new Vec();
				for (int i = 0; i < tempBrick.getBoxCollider().getBoxes()
						.size(); i++) {
					Box box = tempBrick.getBoxCollider().getBoxes().get(i);
					if (maxSize.x() < box.getSize().x()) {
						maxSize.setX(box.getSize().x());
					}

					if (maxSize.y() < box.getSize().y()) {
						maxSize.setY(box.getSize().y());
					}

					if (maxSize.z() < box.getSize().z()) {
						maxSize.setZ(box.getSize().z());
					}
				}

				parent.box(maxSize.x() * Util.BRICK_SIZE, maxSize.y()
						* Util.BRICK_SIZE, maxSize.z() * Util.BRICK_HEIGHT
						+ Util.BRICK_DOT_HEIGHT + Util.EXTEND_BOX_SELECTED);
			} else {
				for (int i = 0; i < tempBrick.getBoxCollider().getBoxes()
						.size(); i++) {
					parent.box(tempBrick.getBoxCollider().getBoxes().get(i)
							.getWidth(), tempBrick.getBoxCollider().getBoxes()
							.get(i).getHeight(), tempBrick.getBoxCollider()
							.getBoxes().get(i).getDepth()
							+ Util.EXTEND_BOX_SELECTED);

				}
			}
			parent.popMatrix();
		}
	}

	/**
	 * Change Brick
	 * 
	 * @param i
	 */
	public void changeBrick(int i) {
		curBrick.setModelName(Util.MODEL_NAME_LIST.get(i));
		rotateBrickFlag = false;
		switchBrick = true;
		brickSelectedOld = null;
		selectedBrickID = -1;
		selectedBrickFlag = false;
		selectedBrickIDMulti.clear();
	}

	/**
	 * Set Color For Brick
	 * 
	 * @param brickID
	 * @param color
	 */
	public void setColorForBrick(int brickID, Vec color) {
		if (bricks.size() <= brickID)
			return;
		bricks.get(brickID).setColor(color);
		gameModified = true;
	}

	/**
	 * Check Expand Plane
	 * 
	 * @param pos
	 */
	public void checkExpandPlane(Vec pos) {
		if (disableBrickFollowMouse)
			return;
		// Bottom
		if (pos.y() / Util.BRICK_SIZE > Util.DOWN_HEIGHT) {
			int expandedValue = (Math.round(Math.abs(pos.y()
					- (Util.DOWN_HEIGHT * Util.BRICK_SIZE))
					/ Util.BRICK_SIZE));
			// System.out.println("1: " + expandedValue);
			Util.DOWN_HEIGHT += expandedValue;
			if (Util.DOWN_HEIGHT + Util.UP_HEIGHT > Util.HEIGHT_MAX) {
				Util.DOWN_HEIGHT = Util.HEIGHT_MAX - Util.UP_HEIGHT;
			}
		}

		// Top
		if (pos.y() / Util.BRICK_SIZE < -(Util.UP_HEIGHT)) {
			int expandedValue = (Math.round(Math.abs(pos.y()
					+ (Util.UP_HEIGHT * Util.BRICK_SIZE))
					/ Util.BRICK_SIZE));
			// System.out.println("2: " + expandedValue);
			Util.UP_HEIGHT += expandedValue;
			if (Util.DOWN_HEIGHT + Util.UP_HEIGHT > Util.HEIGHT_MAX) {
				Util.UP_HEIGHT = Util.HEIGHT_MAX - Util.DOWN_HEIGHT;
			}
		}

		// Right
		if (pos.x() / Util.BRICK_SIZE > (Util.RIGHT_WIDTH)) {
			int expandedValue = (Math.round(Math.abs(pos.x()
					- (Util.RIGHT_WIDTH * Util.BRICK_SIZE))
					/ Util.BRICK_SIZE));
			// /System.out.println("3: " + expandedValue);
			Util.RIGHT_WIDTH += expandedValue;
			if (Util.RIGHT_WIDTH + Util.LEFT_WIDTH > Util.WIDTH_MAX) {
				Util.RIGHT_WIDTH = Util.WIDTH_MAX - Util.LEFT_WIDTH;
			}
		}

		// Left
		if (pos.x() / Util.BRICK_SIZE < -(Util.LEFT_WIDTH)) {
			int expandedValue = (Math.round(Math.abs(pos.x()
					+ (Util.LEFT_WIDTH * Util.BRICK_SIZE))
					/ Util.BRICK_SIZE));
			// System.out.println("4: " + expandedValue);
			Util.LEFT_WIDTH += expandedValue;
			if (Util.RIGHT_WIDTH + Util.LEFT_WIDTH > Util.WIDTH_MAX) {
				Util.LEFT_WIDTH = Util.WIDTH_MAX - Util.RIGHT_WIDTH;
			}
		}
		if (Util.LEFT_WIDTH >= Util.LEFT_WIDTH_DEFAULT
				|| Util.RIGHT_WIDTH >= Util.RIGHT_WIDTH_DEFAULT
				|| Util.UP_HEIGHT >= Util.UP_HEIGHT_DEFAULT
				|| Util.DOWN_HEIGHT >= Util.DOWN_HEIGHT_DEFAULT) {
			planeLego.setup();
			setupPlane();
		}
	}

	/**
	 * Is Game Modified
	 * 
	 * @return
	 */
	public boolean isGameModified() {
		return gameModified;
	}

	/**
	 * Set Game Modified
	 * 
	 * @param gameModified
	 */
	public void setGameModified(boolean gameModified) {
		this.gameModified = gameModified;
	}

	/**
	 * Key Release Process
	 */
	public void keyReleaseProcess() {
		if (parent.keyCode == 17) {
			holdControlKey = false;
		}
		if (parent.keyCode == 66) {
			holdSelectKey = false;
		}
	}

	/**
	 * Mouse Drag Process
	 */
	public void mouseDragProcess() {
		mouseDrag = true;
		if (holdSelectKey) {
			disableBrickFollowMouse = true;
		}
		if (holdControlKey) {
			if (objectHovered.getIndexNameObject() == 0
					|| objectHovered.getIndexNameObject() == 1) {
				if (selectedBrickIDMulti
						.indexOf(objectHovered.getIndexObject()) != -1) {
					Brick brickDrag = bricks
							.get(objectHovered.getIndexObject());
					Brick brick;
					try {
						brick = brickFactory.createBrick(brickDrag
								.getModelName());
						brick.setModel(brickDrag.getModel());
						brick.setModelName(brickDrag.getModelName());
						brick.setRotation(Util.newVecFromVec(brickDrag
								.getRotation()));
						brick.setScaleRatio(brickDrag.getScaleRatio());
						brick.setSizeBrick(Util.newVecFromVec(brickDrag
								.getSizeBrick()));
						brick.setTranslation(Util.newVecFromVec(brickDrag
								.getTranslation()));
						brick.setTimesRotation(brickDrag.getTimesRotation() % 2);
						brick.setTranslateForDrawAfterRotate(brickDrag
								.getTranslateForDrawAfterRotate());
						brick.setColor(Util.newVecFromVec(brickDrag.getColor()));
						XmlRotation xmlRotation = brick.getXmlBrick()
								.getRotations().get(brick.getTimesRotation());
						brick.generateBoxCollider(xmlRotation.getBoxColliders());
						// brick.setup(brickDrag);
						curBrick.setModelName(brick.getModelName());
						brickFollowMouse = brick;
					} catch (NoSuchMethodException | SecurityException
							| InstantiationException | IllegalAccessException
							| IllegalArgumentException
							| InvocationTargetException e) {
						e.printStackTrace();
					}
					/*
					 * Add brick to a group
					 */
					moveBrickProcess();
					selectedBrickIDMulti.clear();
					objectHovered.setIndexNameObject(-1);
				}
			}

		}
	}

	/**
	 * Move Brick Process
	 */
	private void moveBrickProcess() {
		Brick brickDrag = bricks.get(objectHovered.getIndexObject());
		if (brickDrag.getTranslation().z() > 10) {
			int firstPosition = -1;
			float minXAxis = 0;
			for (int i = 0; i < selectedBrickIDMulti.size(); i++) {
				Brick selectedBrick = bricks.get((int) selectedBrickIDMulti
						.get(i));
				if (selectedBrick.getTranslation().z() == 0) {
					if (firstPosition == -1) {
						firstPosition = 0;
						minXAxis = selectedBrick.getTranslation().x();
					}

					if (selectedBrick.getTranslation().x() < minXAxis) {
						minXAxis = selectedBrick.getTranslation().x();
						firstPosition = i;
					}
				}
			}

			if (firstPosition != -1) {
				objectHovered.setIndexObject(selectedBrickIDMulti
						.get(firstPosition));
				brickDrag = bricks.get(objectHovered.getIndexObject());
			}
		}
		for (int i = 0; i < selectedBrickIDMulti.size(); i++) {
			if (selectedBrickIDMulti.get(i) == objectHovered.getIndexObject()) {
				continue;
			}
			Brick selectedBrick = bricks.get((int) selectedBrickIDMulti.get(i));
			Vec position = Vec.subtract(brickDrag.getTranslation(),
					selectedBrick.getTranslation());
			if (!position.equals(new Vec(0, 0, 0))) {
				groupBrickStates.getBrickStates().add(selectedBrick);
				groupBrickStates.getBrickPosition().add(position);
				groupBrickStates.getBrickPositionBeforeRotate().add(
						Util.newVecFromVec(position));
			}

		}
		brickFollowMouse = brickDrag;

		for (int i = 0; i < selectedBrickIDMulti.size(); i++) {
			Brick selectedBrick = bricks.get((int) selectedBrickIDMulti.get(i));
			selectedBrick.setDeleteFlag(true);
			removeBrickFromGame((int) selectedBrickIDMulti.get(i));
		}

		ArrayList<Brick> tempBricks = new ArrayList<Brick>();
		for (int i = 0; i < selectedBrickIDMulti.size(); i++) {
			tempBricks.add(bricks.get((int) selectedBrickIDMulti.get(i)));
		}

		for (int i = 0; i < tempBricks.size(); i++) {
			bricks.remove(tempBricks.get(i));
		}

		ArrayList<Vec> brickPosition = new ArrayList<Vec>();
		for (int i = 0; i < tempBricks.size(); i++) {
			Vec temp = Util.newVecFromVec(tempBricks.get(i).getTranslation());
			brickPosition.add(temp);
		}

		undoRedo.InsertInUnDoRedoForMove(tempBricks, groupBrickStates,
				brickPosition);

		gameModified = true;
		objectHovered.reset();
	}

	/**
	 * Cut
	 */
	public void cut() {
		copiedBrickIDMulti = new ArrayList<Brick>();
		for (int i = 0; i < selectedBrickIDMulti.size(); i++) {
			Brick selectedBrick = bricks.get((int) selectedBrickIDMulti.get(i));
			copiedBrickIDMulti.add(selectedBrick);
			removeBrickFromGame((int) selectedBrickIDMulti.get(i));
		}

		ArrayList<Brick> tempBricks = new ArrayList<Brick>();
		for (int i = 0; i < selectedBrickIDMulti.size(); i++) {
			tempBricks.add(bricks.get((int) selectedBrickIDMulti.get(i)));
		}

		for (int i = 0; i < tempBricks.size(); i++) {
			bricks.remove(tempBricks.get(i));
		}
		undoRedo.InsertInUnDoRedoForRemove(tempBricks);

		gameModified = true;
		objectHovered.reset();

		selectedBrickFlag = false;
		selectedBrickID = -1;
		selectedBrickIDMulti.clear();
	}

	/**
	 * Copy
	 */
	public void copy() {
		copiedBrickIDMulti = new ArrayList<Brick>();
		for (int i = 0; i < selectedBrickIDMulti.size(); i++) {
			Brick selectedBrick = bricks.get((int) selectedBrickIDMulti.get(i));
			Brick cloneBrick;
			try {
				cloneBrick = brickFactory.createBrick(selectedBrick
						.getModelName());
				cloneBrick.setModelName(selectedBrick.getModelName());
				cloneBrick.setTranslation(selectedBrick.getTranslation());

				cloneBrick.setTranslateForDrawAfterRotate(selectedBrick
						.getTranslateForDrawAfterRotate());
				cloneBrick.setTimesRotation(selectedBrick.getTimesRotation());
				cloneBrick.setRotation(selectedBrick.getRotation());
				cloneBrick.setSizeBrick(selectedBrick.getSizeBrick());

				cloneBrick.setScaleRatio(Util.OBJECT_SCALE);
				cloneBrick.setModel(brickModelDictionary.get(selectedBrick
						.getModelName()));
				cloneBrick.generateBoxCollider();
				cloneBrick.setColor(curColor);
				copiedBrickIDMulti.add(cloneBrick);
			} catch (NoSuchMethodException | SecurityException
					| InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}

		for (int i = 0; i < selectedBrickIDMulti.size(); i++) {

		}

		selectedBrickFlag = false;
		selectedBrickID = -1;
		selectedBrickIDMulti.clear();
	}

	/**
	 * Paste
	 */
	public void paste() {
		if (copiedBrickIDMulti.size() > 0) {
			Brick brickDrag = copiedBrickIDMulti.get(0);
			for (int i = 1; i < copiedBrickIDMulti.size(); i++) {
				Brick selectedBrick = copiedBrickIDMulti.get(i);
				Vec position = Vec.subtract(brickDrag.getTranslation(),
						selectedBrick.getTranslation());
				if (!position.equals(new Vec(0, 0, 0))) {
					groupBrickStates.getBrickStates().add(selectedBrick);
					groupBrickStates.getBrickPosition().add(position);
				}
			}

			brickFollowMouse = brickDrag;
			curBrick = brickDrag;

			undoRedo.InsertInUnDoRedoForPaste(groupBrickStates);
		}
	}

	/**
	 * Set Color For Brick
	 * 
	 * @param vec
	 */
	public void setColorForBrick(Vec vec) {
		ArrayList<Brick> editBricks = new ArrayList<Brick>();
		ArrayList<Vec> prevColors = new ArrayList<Vec>();
		for (int i = 0; i < selectedBrickIDMulti.size(); i++) {
			prevColors.add(Util.newVecFromVec(bricks.get(
					selectedBrickIDMulti.get(i)).getColor()));
			setColorForBrick(selectedBrickIDMulti.get(i), vec);
			editBricks.add(bricks.get(selectedBrickIDMulti.get(i)));
		}
		undoRedo.InsertInUnDoRedoForChangeColor(editBricks, vec, prevColors);
		setCurColor(vec);

	}

	/**
	 * Mouse Released
	 */
	public void mouseReleased() {
		mouseDrag = false;
		boxSelected.setiStart(false);
		boxSelected.reset();
	}

	public void multiSelectProcess() {
		if (holdControlKey) {
			boxSelected.setStartClick(new Vec(parent.mouseX, parent.mouseY, 0));
		}
	}

	public void multiSelectDragProcess() {
		if (holdControlKey) {
			if (boxSelected.isiStart()) {
				float x = boxSelected.getStartClick().x();
				float y = boxSelected.getStartClick().y();
				float width = parent.mouseX - x;
				float height = parent.mouseY - y;
				boxSelected.setWidth(width);
				boxSelected.setHeight(height);
				Rect rect = new Rect(x, y, width, height);
				selectedBrickIDMulti.clear();
				selectedBrickFlag = false;
				for (int i = 0; i < bricks.size(); i++) {
					Brick brick = bricks.get(i);
					if (rect.Contains(brick)) {
						selectedBrickIDMulti.add(i);
					}
				}

				if (selectedBrickIDMulti.size() > 0) {
					selectedBrickFlag = true;
				}

			} else {
				boxSelected.setStartClick(new Vec(parent.mouseX, parent.mouseY,
						0));
				boxSelected.setiStart(true);
			}
		}
	}

	public void drawSelectedBox() {
		if (boxSelected.isiStart()) {
			if (holdControlKey) {
				PMatrix3D currCameraMatrix = new PMatrix3D(g3.camera);
				parent.hint(PConstants.DISABLE_DEPTH_TEST);
				float cameraZ = (float) ((parent.height / 2.0) / Math
						.tan(PConstants.PI * 60.0 / 360.0));
				parent.perspective(PConstants.PI / 3.0f, Util.CURRENT_SCENE
						.camera().aspectRatio(), cameraZ / 10.0f,
						cameraZ * 10.0f);
				parent.camera();
				// Start drawing 2D
				parent.strokeWeight(2);
				parent.stroke(39, 255, 228);
				parent.noFill();
				parent.pushMatrix();
				parent.rect(boxSelected.getStartClick().x(), boxSelected
						.getStartClick().y(), boxSelected.getWidth(),
						boxSelected.getHeight());
				parent.popMatrix();
				// End drawing 2D
				g3.camera = currCameraMatrix;
				parent.hint(PConstants.ENABLE_DEPTH_TEST);
			}
		}
	}

	public void removeGroupBrick() {
		selectedBrickIDMulti.clear();
		groupBrickStates.reset();
	}
}
