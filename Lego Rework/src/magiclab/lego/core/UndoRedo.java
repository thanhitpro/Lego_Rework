package magiclab.lego.core;

import java.util.ArrayList;
import java.util.Stack;

import magiclab.lego.brick.Brick;
import magiclab.lego.gameState.GroupBrickState;
import remixlab.dandelion.geom.Vec;

public class UndoRedo {
	private Stack<ICommand> _Undocommands = new Stack<ICommand>();
	private Stack<ICommand> _Redocommands = new Stack<ICommand>();

	private ArrayList<Brick> bricks;

	public ArrayList<Brick> getBricks() {
		return bricks;
	}

	public void setBricks(ArrayList<Brick> _bricks) {
		this.bricks = _bricks;
	}

	public void undo(int levels) {
		for (int i = 1; i <= levels; i++) {
			if (_Undocommands.size() != 0) {
				ICommand command = _Undocommands.pop();
				command.UnExecute();
				_Redocommands.push(command);

				if (!_Undocommands.isEmpty()) {
					ICommand commandPrev = _Undocommands.pop();
					if (commandPrev.getClass() != MoveCommand.class) {
						_Undocommands.push(commandPrev);
					} else {
						commandPrev.UnExecute();
					}
				}
			}
		}
	}

	public void redo(int levels) {
		for (int i = 1; i <= levels; i++) {
			if (_Redocommands.size() != 0) {
				ICommand command = _Redocommands.pop();
				command.Execute();
				_Undocommands.push(command);
			}
		}
	}

	public void InsertInUnDoRedoForAdd(ArrayList<Brick> addBricks) {
		ICommand cmd = new AddCommand(addBricks, bricks);
		_Undocommands.push(cmd);
		_Redocommands.clear();
	}

	public void InsertInUnDoRedoForRemove(ArrayList<Brick> addBricks) {
		ICommand cmd = new RemoveCommand(addBricks, bricks);
		_Undocommands.push(cmd);
		_Redocommands.clear();
	}

	public void InsertInUnDoRedoForChangeColor(ArrayList<Brick> editBricks,
			Vec newColor, ArrayList<Vec> prevColors) {
		ICommand cmd = new ChangeColorCommand(editBricks, newColor, prevColors);
		_Undocommands.push(cmd);
		_Redocommands.clear();
	}

	public void InsertInUnDoRedoForPaste(GroupBrickState groupBrickStates) {
		ICommand cmd = new PasteCommand(groupBrickStates);
		_Undocommands.push(cmd);
		_Redocommands.clear();
	}

	public void InsertInUnDoRedoForMove(ArrayList<Brick> addBricks,
			GroupBrickState groupBrickStates, ArrayList<Vec> brickPosition) {
		ICommand cmd = new MoveCommand(addBricks, bricks, groupBrickStates,
				brickPosition);
		_Undocommands.push(cmd);
		_Redocommands.clear();
	}
}
