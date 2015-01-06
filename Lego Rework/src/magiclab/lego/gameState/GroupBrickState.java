package magiclab.lego.gameState;

import java.util.ArrayList;

import remixlab.dandelion.geom.Vec;
import magiclab.lego.brick.Brick;
import magiclab.lego.core.GameState;

public class GroupBrickState extends GameState {
	private ArrayList<Brick> brickStates;
	private ArrayList<Vec> brickPosition;
	private ArrayList<Vec> brickPositionBeforeRotate;

	public GroupBrickState() {
		brickPosition = new ArrayList<Vec>();
		brickPositionBeforeRotate = new ArrayList<Vec>();
		brickStates = new ArrayList<Brick>();
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

	public void setBrickPosition(ArrayList<Vec> brickPosition) {
		this.brickPosition = brickPosition;
	}

	public void reset() {
		brickPosition.clear();
		brickStates.clear();
		brickPositionBeforeRotate.clear();
	}

}
