package magiclab.lego.core;

import java.util.ArrayList;

import magiclab.lego.brick.Brick;
import magiclab.lego.util.Util;
import remixlab.dandelion.geom.Vec;

public class ChangeColorCommand implements ICommand {
	/**
	 * Add Bricks
	 */
	private ArrayList<Brick> addBricks;
	/**
	 * NewColor
	 */
	private Vec newColor;
	/**
	 * PrevColor
	 */
	private ArrayList<Vec> prevColor;
	/**
	 * Change Color Command
	 * @param _addBricks
	 * @param _newColor
	 * @param _prevColors
	 */
	public ChangeColorCommand(ArrayList<Brick> _addBricks, Vec _newColor, ArrayList<Vec> _prevColors) {
		addBricks = _addBricks;
		newColor = Util.newVecFromVec(_newColor);
		prevColor = _prevColors;
	}
	/**
	 * Execute
	 */
	@Override
	public void Execute() {
		for (int i = 0; i < addBricks.size(); i++) {
			addBricks.get(i).setColor(newColor);
		}
	}
	/**
	 * UnExecute
	 */
	@Override
	public void UnExecute() {
		for (int i = 0; i < addBricks.size(); i++) {
			addBricks.get(i).setColor(prevColor.get(i));
		}
	}

}
