package magiclab.lego.util;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import magiclab.lego.core.Square;
import magiclab.lego.xml.XmlBrick;
import processing.core.PImage;
import processing.core.PShape;
import remixlab.dandelion.geom.Vec;
import remixlab.proscene.Scene;

public class Util {
	public static final float BRICK_SIZE = 20;
	public static final float BRICK_HEIGHT = 24;
	public static final float BRICK_DOT_HEIGHT = 4;
	public static String FPS = "60.005";
	public static int LEFT_WIDTH = 10;
	public static int RIGHT_WIDTH = 10;
	public static int UP_HEIGHT = 10;
	public static int DOWN_HEIGHT = 10;
	public static int LEFT_WIDTH_DEFAULT = 10;
	public static int RIGHT_WIDTH_DEFAULT = 10;
	public static int UP_HEIGHT_DEFAULT = 10;
	public static int DOWN_HEIGHT_DEFAULT = 10;
	public static int LEFT_WIDTH_FIX = 10;
	public static int RIGHT_WIDTH_FIX = 10;
	public static int UP_HEIGHT_FIX = 10;
	public static int DOWN_HEIGHT_FIX = 10;
	public static String BRICK_2x1 = "brick_2x1";
	public static int PLANE_WIDTH = 20;
	public static int PLANE_HEIGHT = 20;
	public static int PLANE_WIDTH_DEFAULT = 20;
	public static int PLANE_HEIGHT_DEFALUT = 20;
	public static int MODEL_SCALE = 2;
	public static int OBJECT_SCALE = 5;

	public static ArrayList<String> MODEL_NAME_LIST;
	public static Vec DEFAULT_ROTATE = new Vec(3.14159265359f / 2, 0, 0);
	public static float ROTATE_ANGLE_ADDED = 3.14159265359f / 2;
	public static String DEFAULT_BRICK_NAME = "Brick_1x2";
	public static Scene CURRENT_SCENE = null;
	public static ArrayList<Vec> CALIBRATE_VEC_MODEL;
	public static ArrayList<Vec> EXTRA_POSITION_VEC;
	public static double PI = Math.PI;
	public static String KEY_SWITCH_BRICK = "0123456789";
	public static boolean DRAW_AXES = false;
	public static String FONT_FILE_NAME_DEFAULT = "SegoeUI-Light-48.vlw";
	public static int FONT_SIZE_DEFAULT = 48;
	public static Dictionary<String, XmlBrick> XML_BRICK_DICTIONARY = new Hashtable<String, XmlBrick>();
	public static int EXTEND_BOX_SELECTED = 1;
	public static ArrayList<Square> LIST_SQUARE_ON_TOP_BRICKS = new ArrayList<Square>();
	// public static String PLUS_PATH_RESOURCE = "";
	// public static String PLUS_PATH_CLASS_NAME = "";
	public static String PLUS_PATH_RESOURCE = "res\\";
	// public static String PLUS_PATH_RESOURCE = "res/";
	public static String PLUS_PATH_CLASS_NAME = "magiclab.lego.brick.object.";
	public static String MODEL_NAME_FILE = Util.PLUS_PATH_RESOURCE
			+ "ModelNameList.txt";
	public static String CALIBRATE_VEC_FILE = Util.PLUS_PATH_RESOURCE
			+ "CalibrateVecList.txt";
	public static String EXTRA_POSITION_VEC_FILE = "ExtraPostionVec.txt";
	public static PImage tempImage;
	public static ArrayList<PShape> expandedPlanePShape = new ArrayList<PShape>();
	public static ArrayList<Square> expandedPlanePShapePosition = new ArrayList<Square>();
	public static boolean FINISH_THREAD = true;
	public static boolean LISTEN_CHANGE_POSITION = false;
	public static boolean ROTATE_A_CIRCLE = false;
	public static boolean ROTATE_A_CIRCLE_2 = false;;

	public static void LoadModelName() {
		MODEL_NAME_LIST = new ArrayList<String>();
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(new File(MODEL_NAME_FILE)));
		} catch (FileNotFoundException e) {
			PrintWriter writer;
			try {
				writer = new PrintWriter("the-file-name.txt", "UTF-8");
				writer.println(MODEL_NAME_FILE);
				writer.println(e.getMessage());
				writer.close();
			} catch (FileNotFoundException | UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			System.out.println("File not found exception: " + MODEL_NAME_FILE);
			return;
		}
		String line = null;
		try {
			while ((line = in.readLine()) != null) {
				MODEL_NAME_LIST.add(line);
			}
		} catch (IOException e) {
			System.out.println("Error when reading file: " + line);
			return;
		}

		try {
			in.close();
		} catch (IOException e) {
			System.out.println("Error when closing file");
			return;
		}
	}

	@SuppressWarnings("resource")
	public static void LoadCalibrateVec() {
		CALIBRATE_VEC_MODEL = new ArrayList<Vec>();
		BufferedReader in = null;
		try {
			in = new BufferedReader(
					new FileReader(new File(CALIBRATE_VEC_FILE)));
		} catch (FileNotFoundException e) {
			System.out.println("File not found exception: "
					+ CALIBRATE_VEC_FILE);
			return;
		}
		String line = null;
		try {
			while ((line = in.readLine()) != null) {
				String[] vec = line.split(" ");
				Vec temp = new Vec(Float.parseFloat(vec[0]),
						Float.parseFloat(vec[1]), Float.parseFloat(vec[2]));
				CALIBRATE_VEC_MODEL.add(temp);

			}
		} catch (NumberFormatException e) {
			System.out.println("Cannot parse value: " + line);
			return;
		} catch (IOException e) {
			System.out.println("Error when reading file: " + line);
			return;
		}
		try {
			in.close();
		} catch (IOException e) {
			System.out.println("Error when closing file");
			return;
		}
	}

	public static Vec rotateAroundAPoint(float angle, Vec origin, Vec myPoint) {
		angle = (float) ((angle) * (Math.PI / 180)); // Convert to radians
		float rotatedX = (float) (Math.cos(angle) * (myPoint.x() - origin.x())
				- Math.sin(angle) * (myPoint.y() - origin.y()) + origin.x());
		float rotatedY = (float) (Math.sin(angle) * (myPoint.x() - origin.x())
				+ Math.cos(angle) * (myPoint.y() - origin.y()) + origin.y());

		return new Vec(rotatedX, rotatedY, myPoint.z());
	}

	public static Vec newVecFromVec(Vec vec) {
		if (vec == null)
			return null;
		return new Vec(vec.x(), vec.y(), vec.z());
	}

	public static Vec normalXPos = new Vec(1, 0, 0);
	public static Vec normalYPos = new Vec(0, 1, 0);
	public static Vec normalZPos = new Vec(0, 0, 1);
	public static Vec normalXNeg = new Vec(-1, 0, 0);
	public static Vec normalYNeg = new Vec(0, -1, 0);
	public static Vec normalZNeg = new Vec(0, 0, -1);

	public static Point GetScreenspaceCoords(Vec iPoint) {
		float[] windowCoordinate = new float[3];
		Util.CURRENT_SCENE.camera().project(iPoint.x(), iPoint.y(), iPoint.z(),
				windowCoordinate);
		Point point = new Point((int) windowCoordinate[0],
				(int) windowCoordinate[1]);
		return point;
	}
}
