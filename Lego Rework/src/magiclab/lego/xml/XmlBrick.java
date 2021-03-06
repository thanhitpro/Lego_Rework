package magiclab.lego.xml;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import magiclab.lego.util.Util;

import org.w3c.dom.Document;

import remixlab.dandelion.geom.Vec;

public class XmlBrick {
	/**
	 * Name of this brick
	 */
	String name;
	/**
	 * Default size of this brick
	 */
	Vec defaultSizeBrick;
	/**
	 * Calibrate Vector
	 */
	Vec calibrateVec;
	/**
	 * Number of time rotation
	 */
	int numberOfTimeRotation;
	/**
	 * Finish loading flag
	 */
	boolean finishLoading = false;
	/**
	 * List rotation define of this brick
	 */
	ArrayList<XmlRotation> rotations;
	/**
	 * Get Name
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * Set Name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Get Default Size Brick
	 * @return
	 */
	public Vec getDefaultSizeBrick() {
		return defaultSizeBrick;
	}
	/**
	 * Set Default Size Brick
	 * @param defaultSizeBrick
	 */
	public void setDefaultSizeBrick(Vec defaultSizeBrick) {
		this.defaultSizeBrick = Util.newVecFromVec(defaultSizeBrick);
	}
	/**
	 * Get Calibrate Vec
	 * @return
	 */
	public Vec getCalibrateVec() {
		return calibrateVec;
	}
	/**
	 * Set Calibrate Vec
	 * @param calibrateVec
	 */
	public void setCalibrateVec(Vec calibrateVec) {
		this.calibrateVec = Util.newVecFromVec(calibrateVec);
	}
	/**
	 * Get Number Of Time Rotation
	 * @return
	 */
	public int getNumberOfTimeRotation() {
		return numberOfTimeRotation;
	}
	/**
	 * Set Number Of Time Rotation
	 * @param numberOfTimeRotation
	 */
	public void setNumberOfTimeRotation(int numberOfTimeRotation) {
		this.numberOfTimeRotation = numberOfTimeRotation;
	}
	/**
	 * Get Rotations
	 * @return
	 */
	public ArrayList<XmlRotation> getRotations() {
		return rotations;
	}
	/**
	 * Set Rotations
	 * @param rotations
	 */
	public void setRotations(ArrayList<XmlRotation> rotations) {
		this.rotations = rotations;
	}
	/**
	 * Read Xml
	 * @param xmlFileName
	 * @param id
	 */
	public void readXml(String xmlFileName, int id) {
		try {
			File xmlFile = new File(xmlFileName);
			XPath xPath = XPathFactory.newInstance().newXPath();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			/**
			 * Get name of brick
			 */
			String pre = "/brick/model";
			String post = "/name";
			String expression = pre + post;
			name = xPath.compile(expression).evaluate(doc);

			/**
			 * Get default size of brick;
			 */
			post = "/defaultsizebrick/@";
			expression = pre + post;
			float x = getFloatValue(xPath, doc, expression + "x");
			float y = getFloatValue(xPath, doc, expression + "y");
			float z = getFloatValue(xPath, doc, expression + "z");
			defaultSizeBrick = new Vec(x, y, z);

			/**
			 * Get calibrate vector of brick
			 */
			post = "/calibratevec/@";
			expression = pre + post;
			x = getFloatValue(xPath, doc, expression + "x");
			y = getFloatValue(xPath, doc, expression + "y");
			z = getFloatValue(xPath, doc, expression + "z");
			calibrateVec = new Vec(x, y, z);

			/**
			 * Get number of rotations of brick
			 */
			post = "/rotations/@numberofrotation";
			expression = pre + post;
			rotations = new ArrayList<XmlRotation>();
			numberOfTimeRotation = getIntValue(xPath, doc, expression);

			for (int i = 0; i < numberOfTimeRotation; i++) {
				XmlRotation xmlRotation = new XmlRotation();

				/**
				 * Get size of brick
				 */
				post = "/rotations/rotation[@id = '" + i + "']/sizebrick/@";
				expression = pre + post;
				x = getFloatValue(xPath, doc, expression + "x");
				y = getFloatValue(xPath, doc, expression + "y");
				z = getFloatValue(xPath, doc, expression + "z");
				xmlRotation.setSizeBrick(new Vec(x, y, z));

				/**
				 * Get translate for draw of brick
				 */
				post = "/rotations/rotation[@id = '" + i
						+ "']/translatefordraw/@";
				expression = pre + post;
				x = getFloatValue(xPath, doc, expression + "x");
				y = getFloatValue(xPath, doc, expression + "y");
				z = getFloatValue(xPath, doc, expression + "z");
				xmlRotation.setTranslateForDraw(new Vec(x, y, z));

				/**
				 * Get number of dots of brick
				 */
				post = "/rotations/rotation[@id = '" + i
						+ "']/dots/@numberofdot";
				expression = pre + post;
				int numberOfDot = getIntValue(xPath, doc, expression);
				xmlRotation.setNumberOfDot(numberOfDot);

				for (int j = 0; j < numberOfDot; j++) {
					/**
					 * Get position of dot
					 */
					post = "/rotations/rotation[@id = '" + i
							+ "']/dots/dot[@id='" + j + "']/position/@";
					expression = pre + post;
					x = getFloatValue(xPath, doc, expression + "x");
					y = getFloatValue(xPath, doc, expression + "y");
					z = getFloatValue(xPath, doc, expression + "z");
					XmlDot dot = new XmlDot();
					dot.setPosition(new Vec(x, y, z));
					xmlRotation.getDots().add(dot);
				}
				
				if (xmlFileName.contains("Invert")) {
					/**
					 * Get number of bottom dots of brick
					 */
					post = "/rotations/rotation[@id = '" + i
							+ "']/botdots/@numberofbotdots";
					expression = pre + post;
					int numberOfBotDot = getIntValue(xPath, doc, expression);
					xmlRotation.setNumberOfBotDot(numberOfBotDot);

					for (int j = 0; j < numberOfBotDot; j++) {
						/**
						 * Get position of dot
						 */
						post = "/rotations/rotation[@id = '" + i
								+ "']/botdots/dot[@id='" + j + "']/position/@";
						expression = pre + post;
						x = getFloatValue(xPath, doc, expression + "x");
						y = getFloatValue(xPath, doc, expression + "y");
						z = getFloatValue(xPath, doc, expression + "z");
						XmlDot dot = new XmlDot();
						dot.setPosition(new Vec(x, y, z));
						xmlRotation.getBotdots().add(dot);
					}
				}
				
				/**
				 * Get number of boxes collision
				 */
				post = "/rotations/rotation[@id = '" + i
						+ "']/boxcolliders/@numberofboxcolider";
				expression = pre + post;
				int numberOfBoxColider = getIntValue(xPath, doc, expression);
				xmlRotation.setNumberOfBoxColider(numberOfBoxColider);

				for (int j = 0; j < numberOfBoxColider; j++) {
					/**
					 * Get position of box collision
					 */
					post = "/rotations/rotation[@id = '" + i
							+ "']/boxcolliders/box[@id='" + j + "']/position/@";
					expression = pre + post;
					x = getFloatValue(xPath, doc, expression + "x");
					y = getFloatValue(xPath, doc, expression + "y");
					z = getFloatValue(xPath, doc, expression + "z");
					XmlBoxCollider boxCollider = new XmlBoxCollider();
					boxCollider.setPosition(new Vec(x, y, z));

					post = "/rotations/rotation[@id = '" + i
							+ "']/boxcolliders/box[@id='" + j + "']/size/@";
					expression = pre + post;
					x = getFloatValue(xPath, doc, expression + "width");
					y = getFloatValue(xPath, doc, expression + "height");
					z = getFloatValue(xPath, doc, expression + "depth");
					boxCollider.setSize(new Vec(x, y, z));
					xmlRotation.getBoxColliders().add(boxCollider);
				}
				rotations.add(xmlRotation);
			}

			finishLoading = true;
		} catch (Exception ex) {
			System.out.println("Error");
			finishLoading = false;
		}
		System.out.println("Finish");

	}
	/**
	 * Is Finish Loading
	 * @return
	 */
	public boolean isFinishLoading() {
		return finishLoading;
	}
	/**
	 * Set Finish Loading
	 */
	public void setFinishLoading(boolean finishLoading) {
		this.finishLoading = finishLoading;
	}
	/**
	 * Get Float Value
	 * @param xPath
	 * @param doc
	 * @param xPathString
	 * @return
	 * @throws XPathExpressionException
	 */
	private Float getFloatValue(XPath xPath, Document doc, String xPathString)
			throws XPathExpressionException {
		return Float.valueOf(xPath.compile(xPathString).evaluate(doc));
	}
	/**
	 * Get Int Value
	 * @param xPath
	 * @param doc
	 * @param xPathString
	 * @return
	 * @throws XPathExpressionException
	 */
	private Integer getIntValue(XPath xPath, Document doc, String xPathString)
			throws XPathExpressionException {
		try {
			return Integer.valueOf(xPath.compile(xPathString).evaluate(doc));
		} catch (Exception ex) {
			return 0;
		}
	}
}
