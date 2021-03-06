package magiclab.main;

import java.util.ArrayList;

import javafx.application.Platform;
import magic.lego.controller.GameManager;
import magiclab.lego.util.Util;
import processing.core.PApplet;
import processing.event.MouseEvent;
import remixlab.dandelion.geom.Mat;
import remixlab.dandelion.geom.Vec;
import remixlab.proscene.Scene;

@SuppressWarnings("serial")
public class LegoGame extends PApplet {
	Scene scene;
	public GameManager gameManager;
	ArrayList<Vec> tempBox = new ArrayList<Vec>();
	int count = 0;
	float frameRateTotal = 0.0f;
	public boolean testEnable = false;
	public boolean rotateCamera = false;
	public int scaleFactor = 0;

	/**
	 * Setup
	 */
	@Override
	public void setup() {
		Util.LoadModelName();
		Util.LoadCalibrateVec();

		setupScene();
		setupGameManager();
		scene.disableKeyboardAgent();
		// scene.disableMotionAgent();
		Util.CURRENT_SCENE = scene;
		scene.camera().setPosition(new Vec(0, 300, 450));
		// scene.camera().lookAt(new Vec(0, 0, 0));
		scene.camera().setFieldOfView(0.5f);
		scene.camera().setUpVector(new Vec(-1, 1, 0));
		scene.camera().frame().rotate(-0.455f, 0.189f, -0.33f, 0.81f);
		scene.camera().frame().translate(0, 225, -150);
		frameRate(120);
	}

	/**
	 * Draw
	 */
	@Override
	public void draw() {

		if (testEnable && rotateCamera) {
			rotateY(frameCount * 0.01f);
			rotateX(frameCount * 0.01f);
		}
		if (!gameManager.isFinishLoadingNewGame()) {
			return;
		}
		noStroke();
		setupDisplay();
		drawScene();
		// drawBox();
		frameRateTotal += frameRate;
		count++;
		Util.FPS = String.valueOf(frameRate) + "/"
				+ String.valueOf(frameRateTotal / count);
		// System.out.println(Util.FPS);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				GuiGame.statusText.setText(Util.FPS);
			}
		});

	}

	/*
	 * private void drawBox() { for (int i = 0; i < tempBox.size(); i++) {
	 * pushMatrix(); translate(tempBox.get(i).x(), tempBox.get( tempBox.get(i)
	 * .z()); box(1); popMatrix(); } }
	 */
	/**
	 * Draw Scene
	 */
	private void drawScene() {
		gameManager.drawPlane();
		gameManager.drawBrickFollowMouse();
		gameManager.drawBrickOnPlane();
		gameManager.drawBoxCoverBrickHovered();
		gameManager.drawSelectedBox();
		fill(255);
	}

	/**
	 * Key Pressed
	 */
	@Override
	public void keyPressed() {
		// projection(new Vec(0, 0, 0));
		// projection(new Vec(20, 0, 0));
		// System.out.println("p "+ scene.camera().position());
		// System.out.println("r "+ scene.camera().frame().rotation());
		// System.out.println("t "+ scene.camera().frame().translation());
		gameManager.keyPressedProcess();
		// System.out.println(frameRate);
		if (testEnable && keyCode == 83) {
			rotateCamera = true;
		}

		if (testEnable && keyCode == 69) {
			rotateCamera = false;
		}
	}

	/**
	 * Key Released
	 */
	@Override
	public void keyReleased() {
		// TODO Auto-generated method stub
		super.keyReleased();
		gameManager.keyReleaseProcess();
	}

	/**
	 * Setup Display
	 */
	private void setupDisplay() {
		background(165);
		scene.setAxesVisualHint(Util.DRAW_AXES);
		lights();
		// ambientLight(102, 102, 102);
		// spotLight(51, 102, 126, 50, 50, 400, 0, 0, -1, PI/16, 600);
		directionalLight(102, 102, 102, 0, 0, -1);
		lightSpecular(204, 204, 204);
		directionalLight(102, 102, 102, 0, 1, -1);
		lightSpecular(102, 102, 102);
		// ambient(255, 255, 255);
		// ambientLight(40, 20, 40);
		// lightSpecular(255, 215, 215);
		// directionalLight(185, 195, 255, -1, 1.25f, -1);
		// shininess(255);
		scene.setPickingVisualHint(false);
	}

	/**
	 * Setup Game Manager
	 */
	private void setupGameManager() {
		gameManager = new GameManager(this);
		gameManager.setup();
		gameManager.setupPlane();
	}

	/**
	 * Setup Scene
	 */
	private void setupScene() {
		size(1024, 780, OPENGL);
		scene = new Scene(this);
		scene.camera().setZClippingCoefficient(100);
		scene.showAll();
		scene.setGridVisualHint(false);
		// scene.camera().setType(Type.ORTHOGRAPHIC);

	}

	/**
	 * Mouse Moved
	 */
	@Override
	public void mouseMoved() {
		super.mouseMoved();

		if (!testEnable) {
			objectPicking();
			gameManager.mouseHoverProcess();
		}

		// System.out.println("Mouse: " + mouseX + " " + mouseY);
	}

	/**
	 * Mouse Dragged
	 */
	@Override
	public void mouseDragged() {
		// TODO Auto-generated method stub
		super.mouseDragged();
		if (!testEnable) {
			if (mouseButton == RIGHT) {
				gameManager.mouseDragProcess();
				objectPicking();
				gameManager.mouseHoverProcess();
			}
			if (mouseButton == LEFT) {
				gameManager.multiSelectDragProcess();
			}
		}
	}

	/**
	 * Mouse Released
	 */
	@Override
	public void mouseReleased(MouseEvent event) {
		// TODO Auto-generated method stub
		super.mouseReleased(event);
		if (!testEnable) {
			gameManager.mouseReleased();
		}
	}

	/**
	 * Mouse Clicked
	 */
	@Override
	public void mouseClicked(MouseEvent event) {
		super.mouseClicked(event);
		if (!testEnable) {
			if (gameManager.getObjectSelected().getIndexNameObject() != 1) {
				gameManager.placeBrick();
			} else {
				gameManager.selectBrick();
			}

			gameManager.multiSelectProcess();
		}
	}

	/**
	 * Projection
	 * 
	 * @param vec
	 */
	public void projection(Vec vec) {
		float fovX = scene.camera().fieldOfView();
		float fovY = scene.camera().horizontalFieldOfView();
		float aspect = scene.camera().aspectRatio();
		Mat M_view = scene.camera().getView();
		float near = scene.camera().zNear();
		Mat temp = new Mat();
		temp.set(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		temp.setM00((float) (Math.cos(fovY / 2) / aspect));
		temp.setM11((float) (Math.cos(fovY / 2)));
		temp.setM22((float) (aspect / (aspect - near)));
		temp.setM23((float) (near * aspect / (aspect - near)));
		temp.setM32(-1);

		Mat multMat = Mat.multiply(temp, M_view);

		float x = multMat.m00() * vec.x() + multMat.m01() * vec.y()
				+ multMat.m02() * vec.z() + multMat.m03();
		float y = multMat.m10() * vec.x() + multMat.m11() * vec.y()
				+ multMat.m12() * vec.z() + multMat.m13();
		float z = multMat.m20() * vec.x() + multMat.m21() * vec.y()
				+ multMat.m22() * vec.z() + multMat.m23();

		// float z = (float) (-(Math.cos(fovY / 2)));

		// float x = (float) (-(Math.cos(fovX / 2) / aspect) * vec.x() / z);
		// float y = (float) (-(Math.cos(fovY / 2)) * vec.y() / z);
		// float x = vec.x() * aspect + vec.z() * fovX;
		// float y = vec.y() * aspect + vec.z() * fovY;

		System.out.println(x + ", " + y + ", " + z);
		// System.out.println(M_view);
	}

	/**
	 * Object Picking
	 */
	public void objectPicking() {
		Vec CS_Direc = new Vec();
		CS_Direc.setX(((((2.0f * mouseX) / width) - 1) / scene.projection()
				.m00()));
		CS_Direc.setY(-1.0f
				* ((((2.0f * mouseY) / height) - 1) / scene.projection().m11()));
		CS_Direc.setZ(-1.0f);

		Vec WS_Start = new Vec();
		Mat M_view_Inv = scene.camera().getView();
		M_view_Inv.invert();
		WS_Start.setX(M_view_Inv.m03());
		WS_Start.setY(M_view_Inv.m13());
		WS_Start.setZ(M_view_Inv.m23());

		Vec WS_Direc = new Vec();
		WS_Direc.setX(CS_Direc.x() * M_view_Inv.m00() + CS_Direc.y()
				* M_view_Inv.m01() + CS_Direc.z() * M_view_Inv.m02());
		WS_Direc.setY(CS_Direc.x() * M_view_Inv.m10() + CS_Direc.y()
				* M_view_Inv.m11() + CS_Direc.z() * M_view_Inv.m12());
		WS_Direc.setZ(CS_Direc.x() * M_view_Inv.m20() + CS_Direc.y()
				* M_view_Inv.m21() + CS_Direc.z() * M_view_Inv.m22());

		float angle = Vec.angleBetween(WS_Direc, new Vec(0, 0, 1));
		if (angle < 1.6)
			return;
		int i = 0;
		tempBox = new ArrayList<Vec>();
		while (true) {
			Vec pos = new Vec();
			pos = Vec.add(WS_Start,
					Vec.multiply(WS_Direc, i + scene.camera().zNear()));
			tempBox.add(pos);
			int flagCheckCollision = gameManager.checkCollisionPointAndBox(pos);
			if (flagCheckCollision != -1) {
				break;
			} else {
				if (pos.z() < 0) {
					// gameManager.checkExpandPlane(pos);
					// System.out.println("1: " + pos);
					break;
				}
			}

			i++;
			if (i > 5000)
				break;
		}

		Vec planePoint = new Vec();

		int rayDistance = (int) (-WS_Start.z() / WS_Direc.z());
		planePoint.setX(WS_Start.x() + rayDistance * WS_Direc.x());
		planePoint.setY(WS_Start.y() + rayDistance * WS_Direc.y());
		planePoint.setZ(0);

		gameManager.checkExpandPlane(planePoint);

	}

	/**
	 * Undo
	 */
	public void undo() {
		gameManager.undo();
	}

	/**
	 * Redo
	 */
	public void redo() {
		gameManager.redo();
	}

	/**
	 * New Game
	 */
	public void newGame() {
		gameManager.resetGame();
		gameManager.removeGroupBrick();
	}

	/**
	 * Load Game
	 * 
	 * @param path
	 */
	public void loadGame(String path) {
		gameManager.getMenuController().setFileName(path);
		gameManager.getMenuController().loadGame();
		gameManager.removeGroupBrick();
	}

	/**
	 * Save Game
	 * 
	 * @param path
	 */
	public void saveGame(String path) {
		gameManager.getMenuController().setFileName(path);
		gameManager.getMenuController().saveGame();
	}

	/**
	 * Select Brick
	 * 
	 * @param i
	 */
	public void selectBrick(int i) {
		gameManager.changeBrick(i);
		gameManager.removeGroupBrick();
	}

	/**
	 * Set Color
	 * 
	 * @param vec
	 */
	public void setColor(Vec vec) {
		gameManager.setColorForBrick(vec);
	}

	/**
	 * Cut
	 */
	public void cut() {
		gameManager.cut();
	}

	/**
	 * Copy
	 */
	public void copy() {
		gameManager.copy();
	}

	/**
	 * Paste
	 */
	public void paste() {
		gameManager.paste();
	}
}
