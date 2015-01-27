package magiclab.lego.core;

import java.util.ArrayList;

import magiclab.lego.brick.Brick;

public class AddCommand implements ICommand {
	/**
	 * Bricks
	 */
	private ArrayList<Brick> bricks;
	/**
	 * Add Bricks
	 */
	private ArrayList<Brick> addBricks;
	/**
	 * Add Command
	 * @param _addBricks
	 * @param _bricks
	 */
	public AddCommand(ArrayList<Brick> _addBricks, ArrayList<Brick> _bricks) {
		addBricks = _addBricks;
		bricks = _bricks;
	}
	/**
	 * Execute
	 */
	@Override
	public void Execute() {
		for (int i = 0; i < addBricks.size(); i++) {
			bricks.add(addBricks.get(i));
		}
	}
	/**
	 * Un Execute
	 */
	@Override
	public void UnExecute() {
		// TODO Auto-generated method stub
		for (int i = 0; i < addBricks.size(); i++) {
			bricks.remove(addBricks.get(i));
		}
	}

}
