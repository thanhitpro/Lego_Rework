package magiclab.lego.core;

public class ObjectSelected {
	private int indexDot;
	private int indexObject;
	/**
	 * 0. square top brick 1. brick 2. plane
	 */
	private int indexNameObject;

	public ObjectSelected() {
		indexNameObject = -1;
		indexObject = -1;
	}

	public int getIndexObject() {
		return indexObject;
	}

	public void setIndexObject(int indexObject) {
		this.indexObject = indexObject;
	}

	public int getIndexNameObject() {
		return indexNameObject;
	}

	public void setIndexNameObject(int indexNameObject) {
		this.indexNameObject = indexNameObject;
	}

	public int getIndexDot() {
		return indexDot;
	}

	public void setIndexDot(int indexDot) {
		this.indexDot = indexDot;
	}

	public void reset() {
		indexDot = -1;
		indexNameObject = -1;
		indexObject = -1;
	}

}
