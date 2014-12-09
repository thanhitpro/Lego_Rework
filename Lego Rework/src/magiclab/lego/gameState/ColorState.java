package magiclab.lego.gameState;

import remixlab.dandelion.geom.Vec;
import magiclab.lego.core.GameState;

public class ColorState extends GameState {
	/*
	 * color
	 */
	private Vec curColor;
	private int brickID;

	public Vec getCurColor() {
		return curColor;
	}

	public void setCurColor(Vec curColor) {
		this.curColor = curColor;
	}

	public int getBrickID() {
		return brickID;
	}

	public void setBrickID(int brickID) {
		this.brickID = brickID;
	}

}
