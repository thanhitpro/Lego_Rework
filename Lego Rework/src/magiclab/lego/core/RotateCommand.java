package magiclab.lego.core;

import java.util.ArrayList;

import magiclab.lego.brick.Brick;

public class RotateCommand implements ICommand {
	private Brick brickFollowMouse;
	private ArrayList<Brick> bricks;
	private int rotation;
	
	public RotateCommand(Brick _brickFollowMouse, ArrayList<Brick> _bricks, int _rotation) {
		brickFollowMouse = _brickFollowMouse;
		bricks = _bricks;
		rotation = _rotation;
	}
	
	@Override
	public void Execute() {
		// Rotate Left
		if (rotation == -1) {
			
		}
		
		if (rotation == 1) {
			
		}
		
		// Rotate right
	}

	@Override
	public void UnExecute() {
		// TODO Auto-generated method stub
		
	}

}
