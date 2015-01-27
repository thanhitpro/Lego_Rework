package magiclab.lego.core;

public class ObjectSelected {
	/**
	 * indexDot
	 */
	private int indexDot;
	/**
	 * indexObject
	 */
	private int indexObject;
	/**
	 * 0. square top brick 1. brick 2. plane
	 */
	/**
	 * indexNameObject
	 */
	private int indexNameObject;
	/**
	 * Object Selected
	 */
	public ObjectSelected() {
		indexNameObject = -1;
		indexObject = -1;
	}
	/**
	 * Get Index Object
	 * @return
	 */
	public int getIndexObject() {
		return indexObject;
	}
	/**
	 * Set Index Object
	 * @param indexObject
	 */
	public void setIndexObject(int indexObject) {
		this.indexObject = indexObject;
	}
	/**
	 * Get Index Name Object
	 * @return
	 */
	public int getIndexNameObject() {
		return indexNameObject;
	}
	/**
	 * Set Index Name Object
	 * @param indexNameObject
	 */
	public void setIndexNameObject(int indexNameObject) {
		this.indexNameObject = indexNameObject;
	}
	/**
	 * Get Index Dot
	 * @return
	 */
	public int getIndexDot() {
		return indexDot;
	}
	/**
	 * Set Index Dot
	 * @param indexDot
	 */
	public void setIndexDot(int indexDot) {
		this.indexDot = indexDot;
	}
	/**
	 * Reset
	 */
	public void reset() {
		indexDot = -1;
		indexNameObject = -1;
		indexObject = -1;
	}

}
