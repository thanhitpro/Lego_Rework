package magiclab.lego.core;

import java.util.ArrayList;
import java.util.Stack;

import magiclab.lego.brick.Brick;
import magiclab.lego.gameState.GroupBrickState;
import remixlab.dandelion.geom.Vec;

public class UndoRedo {
	/**
	 * _Undocommands
	 */
	private Stack<ICommand> _Undocommands = new Stack<ICommand>();
	/**
	 * _Redocommands
	 */
	private Stack<ICommand> _Redocommands = new Stack<ICommand>();
	/**
	 * Bricks
	 */
	private ArrayList<Brick> bricks;
	/**
	 * Get Bricks
	 * @return
	 */
	public ArrayList<Brick> getBricks() {
		return bricks;
	}
	/**
	 * Set Bricks
	 * @param _bricks
	 */
	public void setBricks(ArrayList<Brick> _bricks) {
		this.bricks = _bricks;
	}
	/**
	 * Undo
	 * @param levels
	 */
	public void undo(int levels) {
		for (int i = 1; i <= levels; i++) {
			if (_Undocommands.size() != 0) {
				ICommand command = _Undocommands.pop();
				command.UnExecute();
				_Redocommands.push(command);
			}
		}
	}
	/**
	 * Redo
	 * @param levels
	 */
	public void redo(int levels) {
		for (int i = 1; i <= levels; i++) {
			if (_Redocommands.size() != 0) {
				ICommand command = _Redocommands.pop();
				command.Execute();
				_Undocommands.push(command);
			}
		}
	}
	/**
	 * Insert In UnDo Redo For Add
	 * @param addBricks
	 */
	public void InsertInUnDoRedoForAdd(ArrayList<Brick> addBricks) {
		ICommand cmd = new AddCommand(addBricks, bricks);
		_Undocommands.push(cmd);
		_Redocommands.clear();
	}
	/**
	 * Insert In UnDo Redo For Remove
	 * @param addBricks
	 */
	public void InsertInUnDoRedoForRemove(ArrayList<Brick> addBricks) {
		ICommand cmd = new RemoveCommand(addBricks, bricks);
		_Undocommands.push(cmd);
		_Redocommands.clear();
	}
	/**
	 * Insert In UnDo Redo For Change Color
	 * @param editBricks
	 * @param newColor
	 * @param prevColors
	 */
	public void InsertInUnDoRedoForChangeColor(ArrayList<Brick> editBricks, Vec newColor, ArrayList<Vec> prevColors) {
		ICommand cmd = new ChangeColorCommand(editBricks, newColor, prevColors);
		_Undocommands.push(cmd);
		_Redocommands.clear();
	}
	/**
	 * Insert In UnDo Redo For Paste
	 * @param groupBrickStates
	 */
	public void InsertInUnDoRedoForPaste(GroupBrickState groupBrickStates) {
		ICommand cmd = new PasteCommand(groupBrickStates);
		_Undocommands.push(cmd);
		_Redocommands.clear();
	}
	/**
	 * Insert In UnDo Redo For Move
	 * @param addBricks
	 * @param groupBrickStates
	 * @param brickPosition
	 */
	public void InsertInUnDoRedoForMove(ArrayList<Brick> addBricks, GroupBrickState groupBrickStates, ArrayList<Vec> brickPosition) {
		ICommand cmd = new MoveCommand(addBricks, bricks, groupBrickStates, brickPosition);
		_Undocommands.push(cmd);
		_Redocommands.clear();
	}
}
