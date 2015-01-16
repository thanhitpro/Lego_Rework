package magiclab.lego.core;

import java.util.ArrayList;

import remixlab.dandelion.geom.Vec;
import magiclab.lego.brick.Brick;
import magiclab.lego.gameState.GroupBrickState;

public class MoveCommand implements ICommand {
	private ArrayList<Brick> bricks;
	private ArrayList<Brick> addBricks;
	private GroupBrickState copiedBrickIDMulti;
	private ArrayList<Vec> brickPosition;

	public MoveCommand(ArrayList<Brick> _addBricks, ArrayList<Brick> _bricks, GroupBrickState groupBrickStates, ArrayList<Vec> _brickPosition) {
		addBricks = _addBricks;
		bricks = _bricks;
		copiedBrickIDMulti = groupBrickStates;
		brickPosition = _brickPosition;
	}

	@Override
	public void Execute() {
		for (int i = 0; i < addBricks.size(); i++) {
			Brick temp = addBricks.get(i);
			bricks.remove(temp);
		}
	}

	@Override
	public void UnExecute() {
		for (int i = 0; i < addBricks.size(); i++) {
			Brick temp = addBricks.get(i);
			temp.setTranslation(brickPosition.get(i));
			bricks.add(temp);
		}
		copiedBrickIDMulti.getBrickStates().clear();
	}
}
