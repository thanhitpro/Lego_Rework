package magiclab.lego.xml;

import magiclab.lego.util.Util;
import remixlab.dandelion.geom.Vec;

public class XmlBoxCollider extends XmlObject {
	private Vec size;

	public Vec getSize() {
		return size;
	}

	public void setSize(Vec size) {
		this.size = Util.newVecFromVec(size);
	}

}
