package magiclab.lego.gameState;

import java.util.ArrayList;

import remixlab.dandelion.geom.Vec;
import magiclab.lego.brick.Brick;
import magiclab.lego.core.GameState;

public class GroupBrickState extends GameState {
	/**
	 * Brick States
	 */
	private ArrayList<Brick> brickStates;
	/**
	 * Brick Position
	 */
	private ArrayList<Vec> brickPosition;
	/**
	 * Brick Position Before Rotate
	 */
	private ArrayList<Vec> brickPositionBeforeRotate;
	/**
	 * Group Brick State
	 */
	public GroupBrickState() {
		brickPosition = new ArrayList<Vec>();
		brickPositionBeforeRotate = new ArrayList<Vec>();
		brickStates = new ArrayList<Brick>();
	}
	/**
	 * Get Brick Position Before Rotate
	 * @return
	 */
	public ArrayList<Vec> getBrickPositionBeforeRotate() {
		return brickPositionBeforeRotate;
	}
	/**
	 * Set Brick Position Before Rotate
	 * @param brickPositionBeforeRotate
	 */
	public void setBrickPositionBeforeRotate(
			ArrayList<Vec> brickPositionBeforeRotate) {
		this.brickPositionBeforeRotate = brickPositionBeforeRotate;
	}
	/**
	 * Get Brick States
	 * @return
	 */
	public ArrayList<Brick> getBrickStates() {
		return brickStates;
	}
	/**
	 * Set Brick States
	 * @param brickStates
	 */
	public void setBrickStates(ArrayList<Brick> brickStates) {
		this.brickStates = brickStates;
	}
	/**
	 * Get Brick Position
	 * @return
	 */
	public ArrayList<Vec> getBrickPosition() {
		return brickPosition;
	}
	/**
	 * Set Brick Position
	 * @param brickPosition
	 */
	public void setBrickPosition(ArrayList<Vec> brickPosition) {
		this.brickPosition = brickPosition;
	}
	/**
	 * Reset
	 */
	public void reset() {
		brickPosition.clear();
		brickStates.clear();
		brickPositionBeforeRotate.clear();
	}

}
