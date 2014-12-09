package magiclab.lego.gameState;

import magiclab.lego.brick.Brick;
import magiclab.lego.core.GameState;

public class BrickState extends GameState {
	/*
	 * true: stack brick state. false: remove brick state
	 */
	private boolean isStacked;

	private Brick brick;

	public boolean isStacked() {
		return isStacked;
	}

	public void setStacked(boolean isStacked) {
		this.isStacked = isStacked;
	}

	public Brick getBrick() {
		return brick;
	}

	public void setBrick(Brick brick) {
		this.brick = brick;
	}

}
