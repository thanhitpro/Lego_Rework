package magiclab.lego.brick;

import java.lang.reflect.InvocationTargetException;
import java.util.Dictionary;
import java.util.Hashtable;

import magiclab.lego.util.Util;

public class BrickFactory {
	/**
	 * m_Register Bricks
	 */
	@SuppressWarnings("rawtypes")
	private Dictionary<String, Class> m_RegisterBricks = new Hashtable<String, Class>();
	/**
	 * Register Brick
	 * @param brickName
	 * @param brickClass
	 */
	@SuppressWarnings("rawtypes")
	public void registerBrick(String brickName, Class brickClass) {
		m_RegisterBricks.put(brickName, brickClass);
	}
	/**
	 * CreateBrick
	 * @param brickName
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public Brick createBrick(String brickName) throws NoSuchMethodException,
			SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		/*
		 * @SuppressWarnings("unchecked") Class<Brick> brickClass =
		 * (Class<Brick>) m_RegisterBricks .get("magiclab.lego.brick.object." +
		 * brickName); Constructor<Brick> brickConstructor =
		 * brickClass.getConstructor(); Brick brick =
		 * brickConstructor.newInstance();
		 */
		String className = Util.PLUS_PATH_CLASS_NAME + brickName;
		Object xyz = null;
		try {
			xyz = Class.forName(className).newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return (Brick) xyz;
	}
}
