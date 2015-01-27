package magiclab.lego.gameState;

import magiclab.lego.brick.Brick;
import magiclab.lego.core.GameState;

public class BrickState extends GameState {
	/*
	 * true: stack brick state. false: remove brick state
	 */
	/**
	 * Is Stacked
	 */
	private boolean isStacked;
	/**
	 * Brick
	 */
	private Brick brick;
	/**
	 * Is Stacked
	 * @return
	 */
	public boolean isStacked() {
		return isStacked;
	}
	/**
	 * Set Stacked
	 * @param isStacked
	 */
	public void setStacked(boolean isStacked) {
		this.isStacked = isStacked;
	}
	/**
	 * Get Brick
	 * @return
	 */
	public Brick getBrick() {
		return brick;
	}
	/**
	 * Set Brick
	 * @param brick
	 */
	public void setBrick(Brick brick) {
		this.brick = brick;
	}

}
