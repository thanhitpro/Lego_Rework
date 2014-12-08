package magic.lego.controller;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import magiclab.lego.brick.Brick;
import magiclab.lego.util.Util;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import remixlab.dandelion.geom.Vec;

public class MenuController {
	GameManager gameManager;
	private String fileName;

	public MenuController(GameManager gameManager) {
		super();
		this.gameManager = gameManager;
	}

	public MenuController() {
		super();
	}

	public GameManager getGameManager() {
		return gameManager;
	}

	public void setGameManager(GameManager gameManager) {
		this.gameManager = gameManager;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void loadGame() {
		try {
			File xmlFile = new File(fileName);
			XPath xPath = XPathFactory.newInstance().newXPath();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			int size = Integer.valueOf(xPath.compile("/Bricks/@size").evaluate(
					doc));
			for (int i = 0; i < size; i++) {
				String modelName = xPath.compile(
						"/Bricks/Brick[@id = " + i + "]/@modelName").evaluate(
						doc);
				if (modelName == "")
					continue;
				Brick brick = gameManager.getBrickFactory().createBrick(
						modelName);
				brick.setModel(gameManager.getBrickModelDictionary().get(
						modelName));

				String colorValue[] = xPath
						.compile("/Bricks/Brick[@id = " + i + "]/Color/@value")
						.evaluate(doc).split(" ");
				Vec color = new Vec(Float.valueOf(colorValue[0]),
						Float.valueOf(colorValue[1]),
						Float.valueOf(colorValue[2]));
				brick.setColor(color);

				String positionValue[] = xPath
						.compile(
								"/Bricks/Brick[@id = " + i
										+ "]/Position/@value").evaluate(doc)
						.split(" ");
				Vec translate = new Vec(Float.valueOf(positionValue[0]),
						Float.valueOf(positionValue[1]),
						Float.valueOf(positionValue[2]));
				brick.setTranslation(translate);
				// brick.setOriginalTranslate(translate);

				String translateForDrawPositionValue[] = xPath
						.compile(
								"/Bricks/Brick[@id = " + i
										+ "]/TranslateForDraw/@value")
						.evaluate(doc).split(" ");
				Vec translateForDrawPosition = new Vec(
						Float.valueOf(translateForDrawPositionValue[0]),
						Float.valueOf(translateForDrawPositionValue[1]),
						Float.valueOf(translateForDrawPositionValue[2]));
				brick.setTranslateForDrawAfterRotate(translateForDrawPosition);

				int timesRotate = Integer.valueOf(xPath.compile(
						"/Bricks/Brick[@id = " + i + "]/@timeRotation")
						.evaluate(doc));
				brick.setTimesRotation(timesRotate);

				String rotationValue[] = xPath
						.compile(
								"/Bricks/Brick[@id = " + i
										+ "]/Rotation/@value").evaluate(doc)
						.split(" ");
				Vec rotate = new Vec(Float.valueOf(rotationValue[0]),
						Float.valueOf(rotationValue[1]),
						Float.valueOf(rotationValue[2]));
				brick.setRotation(rotate);
				brick.setScaleRatio(Util.OBJECT_SCALE);
				brick.generateInitData();
				brick.setId(gameManager.getBricks().size());
				// gameManager.updateInteractiveFrameCollection(brick);
				gameManager.getBricks().add(brick);
			}
			// gameManager.setGeneratedTempIF(false);
			// gameManager.setResetTempIFList(true);
			gameManager.setSwitchBrick(false);
			// gameManager.generateInteractiveFrameForSpecialCase2(null,
			// Util.CURRENT_SCENE);
			System.out.println(size);
			gameManager.setBrickSelectedOld(null);
		} catch (Exception ex) {
			System.out.println("Error");
		}

	}

	public void saveGame() {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			Element rootElement = doc.createElement("Bricks");
			doc.appendChild(rootElement);

			Attr size = doc.createAttribute("size");
			size.setValue(String.valueOf(gameManager.getBricks().size()));
			rootElement.setAttributeNode(size);

			for (int i = 0; i < gameManager.getBricks().size(); i++) {
				Brick brickGame = gameManager.getBricks().get(i);
				Element brick = doc.createElement("Brick");
				rootElement.appendChild(brick);

				Attr attrID = doc.createAttribute("id");
				attrID.setValue(String.valueOf(i));
				brick.setAttributeNode(attrID);

				Attr attrModelName = doc.createAttribute("modelName");
				attrModelName.setValue(brickGame.getModelName());
				brick.setAttributeNode(attrModelName);

				Element color = doc.createElement("Color");
				brick.appendChild(color);

				Attr attrColor = doc.createAttribute("value");
				attrColor.setValue(String.valueOf(brickGame.getColor().x())
						+ " "
						+ String.valueOf(brickGame.getColor().y() + " "
								+ String.valueOf(brickGame.getColor().z())));
				color.setAttributeNode(attrColor);

				Attr attrTimeRotation = doc.createAttribute("timeRotation");
				attrTimeRotation.setValue(String.valueOf(brickGame
						.getTimesRotation()));
				brick.setAttributeNode(attrTimeRotation);

				Element position = doc.createElement("Position");
				brick.appendChild(position);

				Attr attrPosition = doc.createAttribute("value");
				attrPosition
						.setValue(String
								.valueOf(brickGame.getTranslation().x())
								+ " "
								+ String.valueOf(brickGame.getTranslation().y()
										+ " "
										+ String.valueOf(brickGame
												.getTranslation().z())));
				position.setAttributeNode(attrPosition);

				Element rotation = doc.createElement("Rotation");
				brick.appendChild(rotation);

				Attr attrRotation = doc.createAttribute("value");
				attrRotation.setValue(String.valueOf(brickGame.getRotation()
						.x())
						+ " "
						+ String.valueOf(brickGame.getRotation().y() + " "
								+ String.valueOf(brickGame.getRotation().z())));
				rotation.setAttributeNode(attrRotation);

				Element translateForDraw = doc
						.createElement("TranslateForDraw");
				brick.appendChild(translateForDraw);

				Attr attrTranslateForDraw = doc.createAttribute("value");
				attrTranslateForDraw
						.setValue(String.valueOf(brickGame
								.getTranslateForDrawAfterRotate().x())
								+ " "
								+ String.valueOf(brickGame
										.getTranslateForDrawAfterRotate().y()
										+ " "
										+ String.valueOf(brickGame
												.getTranslateForDrawAfterRotate()
												.z())));
				translateForDraw.setAttributeNode(attrTranslateForDraw);
			}

			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(fileName));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);
			System.out.println("File saved!");
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
}
