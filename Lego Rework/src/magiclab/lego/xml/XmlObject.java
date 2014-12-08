package magiclab.lego.xml;

import magiclab.lego.util.Util;
import remixlab.dandelion.geom.Vec;

public class XmlObject {
	Vec position;

	public Vec getPosition() {
		return position;
	}

	public void setPosition(Vec position) {
		this.position = Util.newVecFromVec(position);
	}

}
