package magiclab.main;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import magiclab.lego.util.Util;
import remixlab.dandelion.geom.Vec;

public class GuiGame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static JFXPanel javafxPanel;
	private static JFXPanel javafxMenu;
	private static JFXPanel javafxToolbar;
	private static VBox vboxContainter;
	private static JFXPanel javafxTop;
	private static MenuBar menuBar; // Creates our main menu to hold our
									// Sub-Menus.
	private static ToolBar toolBar; // Creates our tool-bar to hold the buttons.
	private static LegoGame legoGame;
	private static GuiGame exampleFrame = new GuiGame();
	private static String filePath = null;
	private static String fileName = "Untitled";
	private static GridPane gridPane = new GridPane();
	private static GridPane gridGroupPane = new GridPane();
	private static ArrayList<String> collapsedIcon = new ArrayList<String>();

	public GuiGame() {
		super("Untitled" + " - Lego");
		javafxPanel = new JFXPanel();
		javafxMenu = new JFXPanel();
		javafxToolbar = new JFXPanel();
		vboxContainter = new VBox();
		javafxTop = new JFXPanel();

		setLayout(new BorderLayout());
		setSize(1224, 768);
		legoGame = new LegoGame();
		add(legoGame);
		add(javafxPanel, BorderLayout.WEST);

		add(javafxTop, BorderLayout.NORTH);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				collapsedIcon = new ArrayList<String>();
				collapsedIcon.add("Brick");
				collapsedIcon.add("Round");
				collapsedIcon.add("Roof");
				collapsedIcon.add("Plate");
				collapsedIcon.add("Tile");
				initFX();
			}
		});

		// setResizable(false);
		legoGame.init();
	}

	private static void initFX() {
		// This method is invoked on the JavaFX thread
		Scene menuScene = createMenuScene();
		Scene toolbarScene = createToolbarScene();
		javafxMenu.setScene(menuScene);
		javafxToolbar.setScene(toolbarScene);
		vboxContainter.getChildren().add(menuBar);
		vboxContainter.getChildren().add(toolBar);
		javafxTop.setScene(new Scene(vboxContainter, 200, 90));
		Scene scene = createScene();
		javafxPanel.setScene(scene);

	}

	private static Scene createToolbarScene() {
		Group root = new Group();
		Scene scene = new Scene(root, Color.ALICEBLUE);

		Button newBtn = new Button();
		Button openBtn = new Button();
		Button saveBtn = new Button();
		Button undoBtn = new Button();
		Button redoBtn = new Button();

		Text sizeLabel = new Text("Size: ");
		Text spaceLabel = new Text(" x ");
		TextField widthText = new TextField();
		TextField heightText = new TextField();
		Button setSizeBtn = new Button();

		// Set the icon/graphic for the ToolBar Buttons.
		newBtn.setGraphic(new ImageView("new.png"));
		newBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				newGame();
			}
		});

		openBtn.setGraphic(new ImageView("open.png"));
		openBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				openFile();
			}
		});

		saveBtn.setGraphic(new ImageView("save.png"));
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				saveGame();
			}
		});
		undoBtn.setGraphic(new ImageView("undo.png"));
		undoBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				legoGame.undo();
			}
		});
		redoBtn.setGraphic(new ImageView("redo.png"));
		redoBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				legoGame.redo();
			}
		});

		setSizeBtn.setGraphic(new ImageView("expand.png"));
		setSizeBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (widthText.getText() != "" && heightText.getText() != "") {
					int width = Integer.valueOf(widthText.getText());
					int height = Integer.valueOf(heightText.getText());
					if (width > Util.PLANE_WIDTH_DEFAULT
							&& height >= Util.PLANE_HEIGHT_DEFALUT) {

					}
				}

			}
		});
		widthText.setPrefSize(42, 25);
		heightText.setPrefSize(42, 25);

		// Add the Buttons to the ToolBar.
		toolBar = new ToolBar();
		toolBar.getItems().addAll(newBtn, openBtn, saveBtn, undoBtn, redoBtn,
				sizeLabel, widthText, spaceLabel, heightText, setSizeBtn);
		root.getChildren().add(toolBar);
		return (scene);
	}

	public void setsize() {
		this.setSize(1224, 768);
	}

	private static Scene createMenuScene() {
		Group root = new Group();
		Scene scene = new Scene(root, Color.valueOf("f5f5f5"));

		final Menu menuFile = new Menu("File");
		MenuItem menuNew = new MenuItem("New");
		menuNew.setAccelerator(new KeyCodeCombination(KeyCode.N,
				KeyCombination.CONTROL_DOWN));
		menuNew.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				newGame();
			}
		});
		MenuItem menuOpen = new MenuItem("Open");
		menuOpen.setAccelerator(new KeyCodeCombination(KeyCode.O,
				KeyCombination.CONTROL_DOWN));
		menuOpen.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				openFile();
			}
		});
		MenuItem menuSave = new MenuItem("Save");
		menuSave.setAccelerator(new KeyCodeCombination(KeyCode.S,
				KeyCombination.CONTROL_DOWN));
		menuSave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				saveGame();
			}
		});
		MenuItem menuExit = new MenuItem("Exit");
		menuExit.setAccelerator(new KeyCodeCombination(KeyCode.Q,
				KeyCombination.CONTROL_DOWN));
		menuExit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				closeGame();
			}
		});
		menuFile.getItems().add(menuNew);
		menuFile.getItems().add(menuOpen);
		menuFile.getItems().add(menuSave);
		menuFile.getItems().add(menuExit);

		final Menu menuEdit = new Menu("Edit");
		menuEdit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
			}
		});
		MenuItem menuUndo = new MenuItem("Undo");
		menuUndo.setAccelerator(new KeyCodeCombination(KeyCode.Z,
				KeyCombination.CONTROL_DOWN));
		menuUndo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				legoGame.undo();
			}
		});
		MenuItem menuCut = new MenuItem("Cut");
		menuCut.setAccelerator(new KeyCodeCombination(KeyCode.X,
				KeyCombination.CONTROL_DOWN));
		menuCut.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				legoGame.cut();
			}
		});
		MenuItem menuCopy = new MenuItem("Copy");
		menuCopy.setAccelerator(new KeyCodeCombination(KeyCode.C,
				KeyCombination.CONTROL_DOWN));
		menuCopy.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				legoGame.copy();
			}
		});
		MenuItem menuPaste = new MenuItem("Paste");
		menuPaste.setAccelerator(new KeyCodeCombination(KeyCode.V,
				KeyCombination.CONTROL_DOWN));
		menuPaste.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				legoGame.paste();
			}
		});
		MenuItem menuRedo = new MenuItem("Redo");
		menuRedo.setAccelerator(new KeyCodeCombination(KeyCode.Y,
				KeyCombination.CONTROL_DOWN));
		menuRedo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				legoGame.redo();
			}
		});

		menuEdit.getItems().add(menuCut);
		menuEdit.getItems().add(menuCopy);
		menuEdit.getItems().add(menuPaste);
		menuEdit.getItems().add(menuUndo);
		menuEdit.getItems().add(menuRedo);

		final Menu menuHelp = new Menu("Help");
		MenuItem menuHelpItem = new MenuItem("Help");
		MenuItem menuAbout = new MenuItem("About");
		MenuItem menuPrivacyPolicy = new MenuItem("Privacy Policy");
		menuHelp.getItems().add(menuHelpItem);
		menuHelp.getItems().add(menuAbout);
		menuHelp.getItems().add(menuPrivacyPolicy);

		menuBar = new MenuBar();
		menuBar.getMenus().add(menuFile);
		menuBar.getMenus().add(menuEdit);
		menuBar.getMenus().add(menuHelp);

		root.getChildren().add(menuBar);

		return (scene);
	}

	public static void closeGame() {
		if (legoGame.gameManager.getBricks().size() > 0
				&& legoGame.gameManager.isGameModified()) {
			int checkSave = JOptionPane.showConfirmDialog(null,
					"Do you want to save changes to '" + fileName + "'?");
			if (checkSave == 0) {
				saveGame();
			}
			if (checkSave == 2) {
				return;
			}
		}

		System.exit(0);
	}

	public static void newGame() {
		if (legoGame.gameManager.getBricks().size() > 0
				&& legoGame.gameManager.isGameModified()) {
			int checkSave = JOptionPane.showConfirmDialog(null,
					"Do you want to save changes to " + fileName + "?");
			if (checkSave == 0) {
				saveGame();
			}

			if (checkSave == 2)
				return;
		}
		legoGame.newGame();
		fileName = "Untitled";
		filePath = null;
		exampleFrame.setTitle(fileName + " - Lego");
		legoGame.gameManager.setGameModified(false);
	}

	public static void openFile() {
		if (legoGame.gameManager.isGameModified()) {
			int checkSave = JOptionPane.showConfirmDialog(null,
					"Do you want to save changes to " + fileName + "?");
			if (checkSave == 0) {
				saveGame();
			}
		}
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Load game");
		fileChooser.setInitialDirectory(new File("save"));
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("All File", "*.*"),
				new FileChooser.ExtensionFilter("xml", "*.xml"));
		File file = fileChooser.showOpenDialog(null);
		if (file != null) {
			fileName = file.getName();
			filePath = file.getPath();
			legoGame.loadGame(filePath);
			exampleFrame.setTitle(fileName + " - Lego");
			legoGame.gameManager.setGameModified(false);
		}
	}

	public static void saveGame() {
		if (filePath == null) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save game");
			fileChooser.setInitialDirectory(new File("save"));
			fileChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("xml", "*.xml"),
					new FileChooser.ExtensionFilter("All File", "*.*"));
			File file = fileChooser.showSaveDialog(null);
			if (file != null) {
				filePath = file.getPath();
				fileName = file.getName();
			} else {
				return;
			}
		}
		if (filePath != null) {
			exampleFrame.setTitle(fileName + " - Lego");
			legoGame.saveGame(filePath);
			legoGame.gameManager.setGameModified(false);
		}
	}

	private static Scene createScene() {

		Group root = new Group();
		// Label labelFollowMouse = new Label("Brick_0x0");
		// root.getChildren().add(labelFollowMouse);

		Scene scene = new Scene(root, Color.valueOf("f5f5f5"));
		BorderPane borderPane = new BorderPane();

		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		Tab bricksTab = new Tab();
		bricksTab.setText("Bricks");

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(gridPane);
		gridPane.setAlignment(Pos.TOP_LEFT);
		gridPane.setPrefWidth(300);
		gridPane.getColumnConstraints().add(new ColumnConstraints(10));
		gridPane.getColumnConstraints().add(new ColumnConstraints(85));
		gridPane.getRowConstraints().add(new RowConstraints(10));
		gridPane.getRowConstraints().add(new RowConstraints(85));
		gridPane.getRowConstraints().add(new RowConstraints(85));
		gridPane.getRowConstraints().add(new RowConstraints(85));
		gridPane.getRowConstraints().add(new RowConstraints(85));
		gridPane.getRowConstraints().add(new RowConstraints(85));
		gridPane.getRowConstraints().add(new RowConstraints(85));
		gridPane.getRowConstraints().add(new RowConstraints(85));

		addIconToPanel();

		bricksTab.setContent(scrollPane);

		Tab templatesTab = new Tab();
		templatesTab.setText("Templates");
		HBox hboxTemplates = new HBox();
		templatesTab.setContent(hboxTemplates);

		Tab groupsTab = new Tab();
		groupsTab.setText("Groups");
		ScrollPane scrollGroupPane = new ScrollPane();
		scrollGroupPane.setContent(gridPane);
		gridGroupPane.setAlignment(Pos.TOP_LEFT);
		gridGroupPane.setPrefWidth(300);
		gridGroupPane.getColumnConstraints().add(new ColumnConstraints(10));
		gridGroupPane.getColumnConstraints().add(new ColumnConstraints(85));
		gridGroupPane.getRowConstraints().add(new RowConstraints(10));
		gridGroupPane.getRowConstraints().add(new RowConstraints(85));
		gridGroupPane.getRowConstraints().add(new RowConstraints(85));
		gridGroupPane.getRowConstraints().add(new RowConstraints(85));
		gridGroupPane.getRowConstraints().add(new RowConstraints(85));
		gridGroupPane.getRowConstraints().add(new RowConstraints(85));
		gridGroupPane.getRowConstraints().add(new RowConstraints(85));
		gridGroupPane.getRowConstraints().add(new RowConstraints(85));

		addIconToGroupPanel();
		
		groupsTab.setContent(scrollGroupPane);
		

		tabPane.getTabs().add(bricksTab);
		tabPane.getTabs().add(templatesTab);
		tabPane.getTabs().add(groupsTab);

		borderPane.setCenter(tabPane);

		ToolBar toolBar = new ToolBar();
		Button btnColor = new Button();
		btnColor.setGraphic(new ImageView("color_picker.png"));
		btnColor.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				java.awt.Color c = JColorChooser.showDialog(null,
						"Choose a Color", null);
				if (c != null)
					legoGame.setColor(new Vec(c.getRed(), c.getGreen(), c
							.getBlue()));

			}
		});
		btnColor.setPrefSize(48, 48);
		btnColor.setPadding(new Insets(-10));
		toolBar.getItems().add(btnColor);

		borderPane.setBottom(toolBar);

		borderPane.prefHeightProperty().bind(scene.heightProperty());
		borderPane.prefWidthProperty().add(200);
		root.getChildren().add(borderPane);
		/*
		 * Listening tab Panel
		 */
		tabPane.getSelectionModel().selectedIndexProperty()
				.addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> arg0,
							Number arg1, Number arg2) {
						if (arg2 == (Number) 0) {

						}
					}
				});
		return (scene);
	}

	private static void addIconToGroupPanel() {
		gridGroupPane.getChildren().clear();
		
	}

	private static void addIconToPanel() {
		gridPane.getChildren().clear();
		int indexGrid = 0;
		for (int i = 0; i < Util.MODEL_NAME_LIST.size(); i++) {
			int indexValue = i;
			String modelName = Util.MODEL_NAME_LIST.get(indexValue);
			Button button = new Button();
			button.setGraphic(new ImageView(modelName + ".png"));
			button.setBackground(null);
			if (modelName.charAt(0) != '_') {
				String modelBrickCatagory = modelName.substring(0,
						modelName.indexOf('_'));
				if (collapsedIcon.indexOf(modelBrickCatagory) != -1) {
					continue;
				}
				button.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						legoGame.selectBrick(indexValue);
					}
				});
			} else {
				button.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						if (collapsedIcon.indexOf(modelName.substring(1)) != -1) {
							collapsedIcon.remove(modelName.substring(1));
						} else {
							collapsedIcon.add(modelName.substring(1));
						}
						addIconToPanel();
					}
				});
			}

			GridPane.setRowIndex(button, 1 + indexGrid / 3);
			GridPane.setColumnIndex(button, 1 + indexGrid % 3);
			gridPane.getChildren().add(button);
			indexGrid++;
		}
	}

	public static void main(String args[]) throws InterruptedException {
		exampleFrame
				.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		exampleFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				closeGame();
			}
		});
		exampleFrame.setVisible(true);
		exampleFrame.setState(Frame.ICONIFIED);
		javafxPanel.setFocusable(false);
		javafxMenu.setFocusable(true);
		javafxTop.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				legoGame.key = arg0.getKeyChar();
				legoGame.gameManager.keyReleaseProcess();
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				legoGame.key = arg0.getKeyChar();

				legoGame.gameManager.keyPressedProcess();
			}
		});

		Thread.sleep(1500);

		exampleFrame.setState(Frame.NORMAL);
		exampleFrame.setLocationRelativeTo(null);
	}

}
