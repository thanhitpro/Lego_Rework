package magiclab.lego.gameState;

import remixlab.dandelion.geom.Vec;
import magiclab.lego.core.GameState;

public class ColorState extends GameState {
	/*
	 * color
	 */
	/**
	 * Cur Color
	 */
	private Vec curColor;
	/**
	 * Brick ID
	 */
	private int brickID;
	/**
	 * Get Cur Color
	 * @return
	 */
	public Vec getCurColor() {
		return curColor;
	}
	/**
	 * Set Cur Color
	 * @param curColor
	 */
	public void setCurColor(Vec curColor) {
		this.curColor = curColor;
	}
	/**
	 * Get Brick ID
	 * @return
	 */
	public int getBrickID() {
		return brickID;
	}
	/**
	 * Set Brick ID
	 * @param brickID
	 */
	public void setBrickID(int brickID) {
		this.brickID = brickID;
	}

}
