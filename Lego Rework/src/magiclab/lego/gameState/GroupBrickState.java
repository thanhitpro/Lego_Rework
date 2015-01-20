package magiclab.lego.gameState;

import java.util.ArrayList;

import magiclab.lego.brick.Brick;
import magiclab.lego.core.Box;
import magiclab.lego.core.GameState;
import magiclab.lego.util.Util;
import remixlab.dandelion.geom.Vec;

public class GroupBrickState extends GameState {
	private ArrayList<Brick> brickStates;
	private ArrayList<Vec> brickPosition;
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

	public ArrayList<Vec> getBrickPositionBeforeRotate() {
		return brickPositionBeforeRotate;
	}

	public void setBrickPositionBeforeRotate(
			ArrayList<Vec> brickPositionBeforeRotate) {
		this.brickPositionBeforeRotate = brickPositionBeforeRotate;
	}

	public ArrayList<Brick> getBrickStates() {
		return brickStates;
	}

	public void setBrickStates(ArrayList<Brick> brickStates) {
		this.brickStates = brickStates;
	}

	public ArrayList<Vec> getBrickPosition() {
		return brickPosition;
	}

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
