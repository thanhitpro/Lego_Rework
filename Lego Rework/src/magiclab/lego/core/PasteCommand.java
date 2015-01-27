package magiclab.lego.core;

import magiclab.lego.gameState.GroupBrickState;

public class PasteCommand implements ICommand {
	/**
	 * Copied Brick ID Multi
	 */
	private GroupBrickState copiedBrickIDMulti;
	/**
	 * Paste Command
	 * @param groupBrickStates
	 */
	public PasteCommand(GroupBrickState groupBrickStates) {
		copiedBrickIDMulti = groupBrickStates;
	}
	/**
	 * Execute
	 */
	@Override
	public void Execute() {
		
	}
	/**
	 * UnExecute
	 */
	@Override
	public void UnExecute() {
		copiedBrickIDMulti.getBrickStates().clear();
	}

}
