package magiclab.lego.core;

import magiclab.lego.util.Util;
import remixlab.dandelion.geom.Vec;

public class Ray {
	/**
	 * Origin
	 */
	private Vec origin;
	/**
	 * Direction
	 */
	private Vec direction;
	/**
	 * Get Origin
	 * @return
	 */
	public Vec getOrigin() {
		return origin;
	}
	/**
	 * Set Origin
	 * @param origin
	 */
	public void setOrigin(Vec origin) {
		this.origin = Util.newVecFromVec(origin);
	}
	/**
	 * Get Direction
	 * @return
	 */
	public Vec getDirection() {
		return direction;
	}
	/**
	 * Set Direction
	 * @param direction
	 */
	public void setDirection(Vec direction) {
		this.direction = Util.newVecFromVec(direction);
	}
	/**
	 * Ray
	 * @param origin
	 * @param direction
	 */
	public Ray(Vec origin, Vec direction) {
		super();
		this.origin = origin;
		this.direction = direction;
	}

}
