package magiclab.lego.gameState;

import java.util.ArrayList;

import magiclab.lego.brick.Brick;
import magiclab.lego.core.Box;
import magiclab.lego.core.GameState;
import magiclab.lego.util.Util;
import remixlab.dandelion.geom.Vec;

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

	private ArrayList<Vec> connectedPoint;
	private int timesRotation = 0;
	private Vec connectPoint;

	public Vec getConnectPoint() {
		return connectPoint;
	}

	public void setConnectPoint(Vec connectPoint) {
		this.connectPoint = connectPoint;
	}

	/**
	 * Group Brick State
	 */
	public GroupBrickState() {
		brickPosition = new ArrayList<Vec>();
		brickPositionBeforeRotate = new ArrayList<Vec>();
		brickStates = new ArrayList<Brick>();
		timesRotation = 0;
		connectedPoint = new ArrayList<Vec>();
	}

	public ArrayList<Vec> getConnectedPoint() {
		return connectedPoint;
	}

	public void setConnectedPoint(ArrayList<Vec> connectedPoint) {
		this.connectedPoint = connectedPoint;
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
	public void setBrickPosition(ArrayList<Vec> _brickPosition) {
		for (int i = 0; i < brickPosition.size(); i++) {
			brickPosition.set(i, Util.newVecFromVec(_brickPosition.get(i)));
		}		
	}

	public int getTimesRotation() {
		return timesRotation;
	}

	public void setTimesRotation(int timesRotation) {
		this.timesRotation = timesRotation;
	}

	public void increaseTimesRotate() {
		if (timesRotation == 3) {
			timesRotation = 0;
		} else {
			timesRotation++;
		}
	}

	public void decreaseTimesRotate() {
		if (timesRotation == 0) {
			timesRotation = 3;
		} else {
			timesRotation--;
		}
	}
	/**
	 * Reset
	 */
	public void reset() {
		brickPosition.clear();
		brickStates.clear();
		brickPositionBeforeRotate.clear();
		timesRotation = 0;
	}

	public void generateBoxCollider() {
		for (int i = 0; i < brickStates.size(); i++) {
			brickStates.get(i).generateBoxCollider();

			for (int j = 0; j < brickStates.get(i).getBoxCollider().getBoxes()
					.size(); j++) {
				Box box = brickStates.get(i).getBoxCollider().getBoxes().get(j);
				box.setPosition(Vec.subtract(connectPoint, brickPosition.get(i)));
			}

		}
	}
	
	public void generateConnectedPoint() {
		for (int i = 0; i < brickStates.size(); i++) {
			
		}
	}
}
