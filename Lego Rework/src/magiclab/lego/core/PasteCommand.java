package magiclab.lego.core;

import magiclab.lego.gameState.GroupBrickState;

public class PasteCommand implements ICommand {
	private GroupBrickState copiedBrickIDMulti;
	
	public PasteCommand(GroupBrickState groupBrickStates) {
		copiedBrickIDMulti = groupBrickStates;
	}

	@Override
	public void Execute() {
		
	}

	@Override
	public void UnExecute() {
		copiedBrickIDMulti.getBrickStates().clear();
	}

}
