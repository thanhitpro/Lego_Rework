package magiclab.lego.core;

import java.util.ArrayList;

import magiclab.lego.brick.Brick;

public class AddCommand implements ICommand {

	private ArrayList<Brick> bricks;
	private ArrayList<Brick> addBricks;
	
	public AddCommand(ArrayList<Brick> _addBricks, ArrayList<Brick> _bricks) {
		addBricks = _addBricks;
		bricks = _bricks;
	}

	@Override
	public void Execute() {
		for (int i = 0; i < addBricks.size(); i++) {
			bricks.add(addBricks.get(i));
			addBricks.get(i).generateInitData();
		}
	}

	@Override
	public void UnExecute() {
		// TODO Auto-generated method stub
		for (int i = 0; i < addBricks.size(); i++) {
			bricks.remove(addBricks.get(i));
		}
	}

}
