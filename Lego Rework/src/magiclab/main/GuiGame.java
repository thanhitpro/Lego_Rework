package magiclab.main;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
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
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
	private static VBox statusBar;
	private static JFXPanel javafxStatusBar;
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
	public static String FPS = "Playing game";
	public static Text statusText = new Text("");
	public static String appName = "Processing 2 언어기반 3차원 가상현실 블록조립 프로그램 ver 1.0";
	public static String tooltipCss = "-fx-background-color:rgba(255,255,255,1); "
			+ "-fx-text-fill:#000000; "
			+ "-fx-border-color: #808080;"
			+ "-fx-border-width: 2;"
			+ "-fx-border-radius:2;"
			+ "-fx-padding: 3 5";
	public static Button setSizeBtn = new Button();
	public static Label sizeLabel = new Label("Plane Size: ");
	public static Label spaceLabel = new Label(" X ");
	public static TextField widthText = new TextField();
	public static TextField heightText = new TextField();
	public static String[] categories = { "Brick", "Round", "Roof", "Plate",
			"Tile" };
	public static MenuItem menuSaveAs = new MenuItem("Save As");
	public static MenuItem menuSave = new MenuItem("Save");
	public static MenuItem menuUndo = new MenuItem("Undo");
	public static MenuItem menuCut = new MenuItem("Cut");
	public static MenuItem menuCopy = new MenuItem("Copy");
	public static MenuItem menuPaste = new MenuItem("Paste");
	public static MenuItem menuRedo = new MenuItem("Redo");
	public static MenuItem selectAll = new MenuItem("Select All");
	public static Button saveBtn = new Button();
	public static Button undoBtn = new Button();
	public static Button redoBtn = new Button();

	/**
	 * Gui Game
	 */
	public GuiGame() {
		super("Untitled" + " - "
				+ "Processing 2 언어기반 3차원 가상현실 블록조립 프로그램 ver 1.0");
		javafxPanel = new JFXPanel();
		javafxMenu = new JFXPanel();
		javafxToolbar = new JFXPanel();
		javafxStatusBar = new JFXPanel();
		vboxContainter = new VBox();
		javafxTop = new JFXPanel();
		statusBar = new VBox();

		setLayout(new BorderLayout());
		setSize(1224, 768);
		legoGame = new LegoGame();
		add(legoGame);
		add(javafxPanel, BorderLayout.WEST);

		add(javafxTop, BorderLayout.NORTH);
		add(javafxStatusBar, BorderLayout.SOUTH);
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

		javafxTop.setScene(new Scene(vboxContainter, 160, 75));
		Scene scene = createScene();
		javafxPanel.setScene(scene);
		statusBar.setStyle("-fx-background-color: gainsboro");

		statusBar.getChildren().add(statusText);
		javafxStatusBar.setScene(new Scene(statusBar, 100, 20));

	}

	private static Scene createToolbarScene() {
		Group root = new Group();
		Scene scene = new Scene(root, Color.ALICEBLUE);

		Button newBtn = new Button();
		Tooltip tooltipNewBtn = new Tooltip();
		tooltipNewBtn.setText("New");
		tooltipNewBtn.styleProperty().set(tooltipCss);
		newBtn.setTooltip(tooltipNewBtn);
		newBtn.setOnMouseMoved(new EventHandler<javafx.scene.input.MouseEvent>() {
			@Override
			public void handle(javafx.scene.input.MouseEvent mouseEvent) {
				newBtn.setStyle("-fx-cursor: hand;");
				tooltipNewBtn.show(newBtn, mouseEvent.getSceneX()
						+ newBtn.getScene().getWindow().getX() + 10,
						mouseEvent.getSceneY()
								+ newBtn.getScene().getWindow().getY() + 10);
			}
		});

		newBtn.setOnMouseExited(new EventHandler<javafx.scene.input.MouseEvent>() {
			@Override
			public void handle(javafx.scene.input.MouseEvent t) {
				newBtn.setStyle("-fx-cursor: default");
				tooltipNewBtn.hide();
			}
		});

		Button openBtn = new Button();
		Tooltip tooltipOpenBtn = new Tooltip();
		tooltipOpenBtn.setText("Open");
		tooltipOpenBtn.styleProperty().set(tooltipCss);
		openBtn.setTooltip(tooltipOpenBtn);
		openBtn.setOnMouseMoved(new EventHandler<javafx.scene.input.MouseEvent>() {
			@Override
			public void handle(javafx.scene.input.MouseEvent mouseEvent) {
				openBtn.setStyle("-fx-cursor: hand");
				tooltipOpenBtn.show(openBtn, mouseEvent.getSceneX()
						+ openBtn.getScene().getWindow().getX() + 10,
						mouseEvent.getSceneY()
								+ openBtn.getScene().getWindow().getY() + 10);
			}
		});

		openBtn.setOnMouseExited(new EventHandler<javafx.scene.input.MouseEvent>() {
			@Override
			public void handle(javafx.scene.input.MouseEvent t) {
				openBtn.setStyle("-fx-cursor: default");
				tooltipOpenBtn.hide();
			}
		});

		Tooltip tooltipSaveBtn = new Tooltip();
		tooltipSaveBtn.setText("Save");
		tooltipSaveBtn.styleProperty().set(tooltipCss);
		saveBtn.setTooltip(tooltipSaveBtn);
		saveBtn.setOnMouseMoved(new EventHandler<javafx.scene.input.MouseEvent>() {
			@Override
			public void handle(javafx.scene.input.MouseEvent mouseEvent) {
				saveBtn.setStyle("-fx-cursor: hand");
				tooltipSaveBtn.show(saveBtn, mouseEvent.getSceneX()
						+ saveBtn.getScene().getWindow().getX() + 10,
						mouseEvent.getSceneY()
								+ saveBtn.getScene().getWindow().getY() + 10);
			}
		});

		saveBtn.setOnMouseExited(new EventHandler<javafx.scene.input.MouseEvent>() {
			@Override
			public void handle(javafx.scene.input.MouseEvent t) {
				saveBtn.setStyle("-fx-cursor: default");
				tooltipSaveBtn.hide();
			}
		});

		Tooltip tooltipUndoBtn = new Tooltip();
		tooltipUndoBtn.setText("Undo");
		tooltipUndoBtn.styleProperty().set(tooltipCss);
		undoBtn.setTooltip(tooltipUndoBtn);
		undoBtn.setOnMouseMoved(new EventHandler<javafx.scene.input.MouseEvent>() {
			@Override
			public void handle(javafx.scene.input.MouseEvent mouseEvent) {
				undoBtn.setStyle("-fx-cursor: hand");
				tooltipUndoBtn.show(undoBtn, mouseEvent.getSceneX()
						+ undoBtn.getScene().getWindow().getX() + 10,
						mouseEvent.getSceneY()
								+ undoBtn.getScene().getWindow().getY() + 10);
			}
		});

		undoBtn.setOnMouseExited(new EventHandler<javafx.scene.input.MouseEvent>() {
			@Override
			public void handle(javafx.scene.input.MouseEvent t) {
				undoBtn.setStyle("-fx-cursor: default");
				tooltipUndoBtn.hide();
			}
		});

		Tooltip tooltipRedoBtn = new Tooltip();
		tooltipRedoBtn.setText("Redo");
		tooltipRedoBtn.styleProperty().set(tooltipCss);
		redoBtn.setTooltip(tooltipRedoBtn);
		redoBtn.setOnMouseMoved(new EventHandler<javafx.scene.input.MouseEvent>() {
			@Override
			public void handle(javafx.scene.input.MouseEvent mouseEvent) {
				redoBtn.setStyle("-fx-cursor: hand");
				tooltipRedoBtn.show(redoBtn, mouseEvent.getSceneX()
						+ redoBtn.getScene().getWindow().getX() + 10,
						mouseEvent.getSceneY()
								+ redoBtn.getScene().getWindow().getY() + 10);
			}
		});

		redoBtn.setOnMouseExited(new EventHandler<javafx.scene.input.MouseEvent>() {
			@Override
			public void handle(javafx.scene.input.MouseEvent t) {
				redoBtn.setStyle("-fx-cursor: default");
				tooltipRedoBtn.hide();
			}
		});

		Tooltip tooltipChangeSizeBtn = new Tooltip();
		tooltipChangeSizeBtn.setText("Change Size");
		tooltipChangeSizeBtn.styleProperty().set(tooltipCss);
		setSizeBtn.setTooltip(tooltipChangeSizeBtn);
		setSizeBtn
				.setOnMouseMoved(new EventHandler<javafx.scene.input.MouseEvent>() {
					@Override
					public void handle(javafx.scene.input.MouseEvent mouseEvent) {
						setSizeBtn.setStyle("-fx-cursor: hand");
						tooltipChangeSizeBtn.show(setSizeBtn,
								mouseEvent.getSceneX()
										+ setSizeBtn.getScene().getWindow()
												.getX() + 10,
								mouseEvent.getSceneY()
										+ setSizeBtn.getScene().getWindow()
												.getY() + 10);
					}
				});

		setSizeBtn
				.setOnMouseExited(new EventHandler<javafx.scene.input.MouseEvent>() {
					@Override
					public void handle(javafx.scene.input.MouseEvent t) {
						setSizeBtn.setStyle("-fx-cursor: default");
						tooltipChangeSizeBtn.hide();
					}
				});

		// Set the icon/graphic for the ToolBar Buttons.
		newBtn.setGraphic(new ImageView("icon_new.png"));
		newBtn.setPadding(Insets.EMPTY);

		newBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				newGame();
			}
		});

		newBtn.setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>() {

			@Override
			public void handle(javafx.scene.input.MouseEvent mouseEvent) {
				newBtn.setGraphic(new ImageView("icon_new_pressed.png"));
			}

		});

		newBtn.setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {

			@Override
			public void handle(javafx.scene.input.MouseEvent mouseEvent) {
				newBtn.setGraphic(new ImageView("icon_new.png"));
			}

		});

		openBtn.setGraphic(new ImageView("icon_open.png"));
		openBtn.setPadding(Insets.EMPTY);
		openBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				openFile();
			}
		});

		openBtn.setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>() {

			@Override
			public void handle(javafx.scene.input.MouseEvent mouseEvent) {
				openBtn.setGraphic(new ImageView("icon_open_pressed.png"));
			}

		});

		openBtn.setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {

			@Override
			public void handle(javafx.scene.input.MouseEvent mouseEvent) {
				openBtn.setGraphic(new ImageView("icon_open.png"));
			}

		});

		saveBtn.setGraphic(new ImageView("icon_save.png"));
		saveBtn.setPadding(Insets.EMPTY);
		saveBtn.setDisable(true);
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				saveGame();
			}
		});
		saveBtn.setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>() {

			@Override
			public void handle(javafx.scene.input.MouseEvent mouseEvent) {
				saveBtn.setGraphic(new ImageView("icon_save_pressed.png"));
			}

		});

		saveBtn.setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {

			@Override
			public void handle(javafx.scene.input.MouseEvent mouseEvent) {
				saveBtn.setGraphic(new ImageView("icon_save.png"));
			}

		});

		undoBtn.setGraphic(new ImageView("icon_undo.png"));
		undoBtn.setPadding(Insets.EMPTY);
		undoBtn.setDisable(true);
		undoBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				legoGame.undo();
			}
		});
		undoBtn.setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>() {

			@Override
			public void handle(javafx.scene.input.MouseEvent mouseEvent) {
				undoBtn.setGraphic(new ImageView("icon_undo_pressed.png"));
			}

		});

		undoBtn.setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {

			@Override
			public void handle(javafx.scene.input.MouseEvent mouseEvent) {
				undoBtn.setGraphic(new ImageView("icon_undo.png"));
			}

		});

		redoBtn.setGraphic(new ImageView("icon_redo.png"));
		redoBtn.setPadding(Insets.EMPTY);
		redoBtn.setDisable(true);
		redoBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				legoGame.redo();
			}
		});

		redoBtn.setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>() {

			@Override
			public void handle(javafx.scene.input.MouseEvent mouseEvent) {
				redoBtn.setGraphic(new ImageView("icon_redo_pressed.png"));
			}

		});

		redoBtn.setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {

			@Override
			public void handle(javafx.scene.input.MouseEvent mouseEvent) {
				redoBtn.setGraphic(new ImageView("icon_redo.png"));
			}

		});

		setSizeBtn.setGraphic(new ImageView("icon_plane_extent.png"));
		setSizeBtn.setPadding(Insets.EMPTY);
		setSizeBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (widthText.getText() != "" && heightText.getText() != "") {
					try {
						int width = Integer.valueOf(widthText.getText());
						int height = Integer.valueOf(heightText.getText());
						if (width >= Util.PLANE_WIDTH_DEFAULT
								&& height >= Util.PLANE_HEIGHT_DEFALUT) {
							Util.LEFT_WIDTH_DEFAULT = Util.LEFT_WIDTH = Util.RIGHT_WIDTH_DEFAULT = Util.RIGHT_WIDTH = width / 2;
							Util.UP_HEIGHT_DEFAULT = Util.UP_HEIGHT = Util.DOWN_HEIGHT_DEFAULT = Util.DOWN_HEIGHT = height / 2;
							legoGame.gameManager.getPlaneLego().setup();
							legoGame.gameManager.setupPlane();
						}
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null,
								"Please enter a valid size!", "Warning",
								JOptionPane.ERROR_MESSAGE, null);
					}
				}

			}
		});

		setSizeBtn
				.setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>() {

					@Override
					public void handle(javafx.scene.input.MouseEvent mouseEvent) {
						setSizeBtn.setGraphic(new ImageView(
								"icon_plane_extent_pressed.png"));
					}

				});

		setSizeBtn
				.setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {

					@Override
					public void handle(javafx.scene.input.MouseEvent mouseEvent) {
						setSizeBtn.setGraphic(new ImageView(
								"icon_plane_extent.png"));
					}

				});

		widthText.onInputMethodTextChangedProperty();
		widthText.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				if (newValue.length() == 0)
					return;
				if (newValue.charAt(newValue.length() - 1) < 48
						|| newValue.charAt(newValue.length() - 1) > 57) {
					if (oldValue != null)
						widthText.setText(oldValue);
				}
			}

		});
		widthText.setPrefSize(42, 25);
		heightText.onInputMethodTextChangedProperty();
		heightText.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				if (newValue.length() == 0)
					return;
				if (newValue.charAt(newValue.length() - 1) < 48
						|| newValue.charAt(newValue.length() - 1) > 57) {
					if (oldValue != null)
						heightText.setText(oldValue);
				}
			}

		});
		heightText.setPrefSize(42, 25);

		// Add the Buttons to the ToolBar.
		toolBar = new ToolBar();
		toolBar.setStyle("-fx-background-color: #aeb5ba, linear-gradient(to bottom, #2b8b4b 0%, #196934 100%);");
		int value = 604;
		setSizeBtn.setTranslateX(exampleFrame.getWidth() - value);
		heightText.setTranslateX(exampleFrame.getWidth() - value);
		spaceLabel.setStyle("-fx-text-fill: #ffffff;");
		spaceLabel.setTranslateX(exampleFrame.getWidth() - value);
		widthText.setTranslateX(exampleFrame.getWidth() - value);
		sizeLabel.setStyle("-fx-text-fill: #ffffff;");
		sizeLabel.setTranslateX(exampleFrame.getWidth() - value);
		newBtn.setStyle("-fx-background-color:none; ");
		openBtn.setStyle("-fx-background-color:none; ");
		saveBtn.setStyle("-fx-background-color:none; ");
		undoBtn.setStyle("-fx-background-color:none; ");
		redoBtn.setStyle("-fx-background-color:none; ");
		setSizeBtn.setStyle("-fx-background-color:none; ");		
		
		toolBar.getItems().addAll(newBtn, openBtn, saveBtn, undoBtn, redoBtn,
				sizeLabel, widthText, spaceLabel, heightText, setSizeBtn);

		root.getChildren().addAll(toolBar);
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

		menuSave.setDisable(true);
		menuSave.setAccelerator(new KeyCodeCombination(KeyCode.S,
				KeyCombination.CONTROL_DOWN));
		menuSave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				saveGame();
			}
		});

		menuSaveAs.setDisable(true);
		menuSaveAs.setAccelerator(new KeyCodeCombination(KeyCode.S,
				KeyCombination.CONTROL_DOWN, KeyCombination.ALT_DOWN));
		menuSaveAs.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				saveGameAs();
			}
		});

		SeparatorMenuItem firstSeparatorMenuItem = new SeparatorMenuItem();
		SeparatorMenuItem secondSeparatorMenuItem = new SeparatorMenuItem();

		Menu menuExport = new Menu("Export");
		MenuItem exportObj = new MenuItem("Wavefront(.obj)");
		menuExport.getItems().add(exportObj);
		MenuItem exportStl = new MenuItem("StereoLithography(.stl)");
		menuExport.getItems().add(exportStl);
		menuExport.setAccelerator(new KeyCodeCombination(KeyCode.E,
				KeyCombination.CONTROL_DOWN));
		menuExport.setDisable(true);

		MenuItem menuExit = new MenuItem("Quit");
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
		menuFile.getItems().add(menuSaveAs);
		menuFile.getItems().add(firstSeparatorMenuItem);
		menuFile.getItems().add(menuExport);
		menuFile.getItems().add(secondSeparatorMenuItem);
		menuFile.getItems().add(menuExit);

		final Menu menuEdit = new Menu("Edit");
		menuEdit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
			}
		});

		menuUndo.setDisable(true);
		menuUndo.setAccelerator(new KeyCodeCombination(KeyCode.Z,
				KeyCombination.CONTROL_DOWN));
		menuUndo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				legoGame.undo();
			}
		});

		menuCut.setDisable(true);
		menuCut.setAccelerator(new KeyCodeCombination(KeyCode.X,
				KeyCombination.CONTROL_DOWN));
		menuCut.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				legoGame.cut();
			}
		});

		menuCopy.setDisable(true);
		menuCopy.setAccelerator(new KeyCodeCombination(KeyCode.C,
				KeyCombination.CONTROL_DOWN));
		menuCopy.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				legoGame.copy();
			}
		});

		menuPaste.setDisable(true);
		menuPaste.setAccelerator(new KeyCodeCombination(KeyCode.V,
				KeyCombination.CONTROL_DOWN));
		menuPaste.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				legoGame.paste();
			}
		});

		menuRedo.setDisable(true);
		menuRedo.setAccelerator(new KeyCodeCombination(KeyCode.Z,
				KeyCombination.CONTROL_DOWN, KeyCombination.ALT_DOWN));
		menuRedo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				legoGame.redo();
			}
		});

		selectAll.setDisable(true);
		selectAll.setAccelerator(new KeyCodeCombination(KeyCode.A,
				KeyCombination.CONTROL_DOWN));
		selectAll.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				legoGame.selectAll();
			}
		});

		MenuItem group = new MenuItem("Group");
		group.setDisable(true);

		menuEdit.getItems().add(menuUndo);
		menuEdit.getItems().add(menuRedo);
		menuEdit.getItems().add(new SeparatorMenuItem());
		menuEdit.getItems().add(menuCopy);
		menuEdit.getItems().add(menuCut);
		menuEdit.getItems().add(menuPaste);
		menuEdit.getItems().add(new SeparatorMenuItem());
		menuEdit.getItems().add(selectAll);
		menuEdit.getItems().add(new SeparatorMenuItem());
		menuEdit.getItems().add(group);

		final Menu menuHelp = new Menu("Help");
		MenuItem menuHelpItem = new MenuItem("Help");
		MenuItem menuAbout = new MenuItem("About");
		MenuItem menuPrivacyPolicy = new MenuItem("Privacy Policy");
		menuHelp.getItems().add(menuHelpItem);
		menuHelp.getItems().add(menuAbout);
		menuHelp.getItems().add(menuPrivacyPolicy);

		final Menu menuTest = new Menu("Test");
		MenuItem menuNormal = new MenuItem("Normal");
		menuNormal.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				legoGame.testEnable = false;
				Util.LEFT_WIDTH = Util.RIGHT_WIDTH = Util.DOWN_HEIGHT = Util.UP_HEIGHT = 10;
				Util.LEFT_WIDTH_DEFAULT = Util.RIGHT_WIDTH_DEFAULT = Util.DOWN_HEIGHT_DEFAULT = Util.UP_HEIGHT_DEFAULT = 10;
				legoGame.gameManager.setTestCase(0);
				legoGame.gameManager.setDisableBrickFollowMouse(false);

			}
		});
		MenuItem menuTest1 = new MenuItem("100 000 tris");
		menuTest1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				legoGame.testEnable = true;
				Util.LEFT_WIDTH = Util.RIGHT_WIDTH = Util.DOWN_HEIGHT = Util.UP_HEIGHT = 20;
				Util.LEFT_WIDTH_DEFAULT = Util.RIGHT_WIDTH_DEFAULT = Util.DOWN_HEIGHT_DEFAULT = Util.UP_HEIGHT_DEFAULT = 20;
				legoGame.gameManager.setTestCase(1316);
				legoGame.gameManager.setDisableBrickFollowMouse(true);
				legoGame.scene.camera().setPosition(new Vec(0, 6800, 4000));

			}
		});
		MenuItem menuTest2 = new MenuItem("300 000 tris");
		menuTest2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				legoGame.testEnable = true;
				Util.LEFT_WIDTH_DEFAULT = Util.RIGHT_WIDTH = Util.DOWN_HEIGHT = Util.UP_HEIGHT = 20;
				Util.LEFT_WIDTH_DEFAULT = Util.RIGHT_WIDTH_DEFAULT = Util.DOWN_HEIGHT_DEFAULT = Util.UP_HEIGHT_DEFAULT = 20;
				legoGame.gameManager.setTestCase(3948);
				legoGame.gameManager.setDisableBrickFollowMouse(true);
				legoGame.scene.camera().setPosition(new Vec(0, 6800, 4000));
			}
		});
		MenuItem menuTest3 = new MenuItem("500 000 tris");
		menuTest3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				legoGame.testEnable = true;
				Util.LEFT_WIDTH = Util.RIGHT_WIDTH = Util.DOWN_HEIGHT = Util.UP_HEIGHT = 20;
				Util.LEFT_WIDTH_DEFAULT = Util.RIGHT_WIDTH_DEFAULT = Util.DOWN_HEIGHT_DEFAULT = Util.UP_HEIGHT_DEFAULT = 20;
				legoGame.gameManager.setTestCase(6579);
				legoGame.gameManager.setDisableBrickFollowMouse(true);
				legoGame.scene.camera().setPosition(new Vec(0, 6800, 4000));
			}
		});

		MenuItem menuTest4 = new MenuItem("1 000 000 tris");
		menuTest4.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				legoGame.testEnable = true;
				Util.LEFT_WIDTH = Util.RIGHT_WIDTH = Util.DOWN_HEIGHT = Util.UP_HEIGHT = 20;
				Util.LEFT_WIDTH_DEFAULT = Util.RIGHT_WIDTH_DEFAULT = Util.DOWN_HEIGHT_DEFAULT = Util.UP_HEIGHT_DEFAULT = 20;
				legoGame.gameManager.setTestCase(13158);
				legoGame.gameManager.setDisableBrickFollowMouse(true);
				legoGame.scene.camera().setPosition(new Vec(0, 6800, 4000));
			}
		});

		menuTest.getItems().add(menuNormal);
		menuTest.getItems().add(menuTest1);
		menuTest.getItems().add(menuTest2);
		menuTest.getItems().add(menuTest3);
		menuTest.getItems().add(menuTest4);

		menuBar = new MenuBar();
		menuBar.getMenus().add(menuFile);
		menuBar.getMenus().add(menuEdit);
		menuBar.getMenus().add(menuHelp);
		menuBar.getMenus().add(menuTest);

		root.getChildren().add(menuBar);

		return (scene);
	}

	public static void saveGameAs() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save as");
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

		if (filePath != null) {
			exampleFrame.setTitle(fileName + " -  "
					+ "Processing 2 언어기반 3차원 가상현실 블록조립 프로그램 ver 1.0");
			legoGame.saveGame(filePath);
			legoGame.gameManager.setGameModified(false);
		}
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
		exampleFrame.setTitle(fileName + " -  "
				+ "Processing 2 언어기반 3차원 가상현실 블록조립 프로그램 ver 1.0");
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
			exampleFrame.setTitle(fileName + " -  "
					+ "Processing 2 언어기반 3차원 가상현실 블록조립 프로그램 ver 1.0");
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
			exampleFrame.setTitle(fileName + " -  "
					+ "Processing 2 언어기반 3차원 가상현실 블록조립 프로그램 ver 1.0");
			legoGame.saveGame(filePath);
			legoGame.gameManager.setGameModified(false);
		}
	}

	private static Scene createScene() {

		Group root = new Group();
		// Label labelFollowMouse = new Label("Brick_0x0");
		// root.getChildren().add(labelFollowMouse);

		Scene scene = new Scene(root, Color.valueOf("ffffff"));
		scene.getStylesheets().add("style.css");
		BorderPane borderPane = new BorderPane();
		borderPane.setLayoutX(-6);
		borderPane.setLayoutY(-6);
		borderPane.setStyle("-fx-background-color:rgba(255,255,255,1);");

		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		Tab bricksTab = new Tab();
		bricksTab.setText("   Bricks   ");

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(gridPane);
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		scrollPane.setStyle("-fx-background-color:transparent;");
		gridPane.setAlignment(Pos.TOP_LEFT);
		gridPane.getColumnConstraints().add(new ColumnConstraints(5));
		gridPane.getColumnConstraints().add(new ColumnConstraints(60));
		gridPane.getColumnConstraints().add(new ColumnConstraints(60));

		gridPane.getRowConstraints().add(new RowConstraints(5));
		gridPane.getRowConstraints().add(new RowConstraints(60));
		gridPane.getRowConstraints().add(new RowConstraints(60));
		gridPane.getRowConstraints().add(new RowConstraints(60));
		gridPane.getRowConstraints().add(new RowConstraints(60));
		gridPane.getRowConstraints().add(new RowConstraints(60));
		gridPane.getRowConstraints().add(new RowConstraints(60));
		gridPane.getRowConstraints().add(new RowConstraints(60));

		addIconToPanel();
		BorderPane tempBorderPane = new BorderPane();
		ToolBar searchToolbar = new ToolBar();
		ImageView searchView = new ImageView("searchbar.png");
		searchView.setFitWidth(206);
		searchView.setFitHeight(24);
		searchView.setTranslateX(7);
		searchToolbar.getItems().add(searchView);
		searchToolbar.setPadding(new Insets(10, 10, 10, 7));
		searchToolbar.setStyle("-fx-background-color:rgba(255,255,255,1);");

		tempBorderPane.setTop(searchToolbar);
		tempBorderPane.setCenter(scrollPane);
		Pane pane = new Pane();
		pane.setPrefWidth(10);
		tempBorderPane.setRight(pane);
		bricksTab.setContent(tempBorderPane);

		Tab templatesTab = new Tab();
		templatesTab.setDisable(true);
		templatesTab.setText("Actuators");
		HBox hboxTemplates = new HBox();
		templatesTab.setContent(hboxTemplates);

		Tab groupsTab = new Tab();
		groupsTab.setDisable(true);
		groupsTab.setText("  Groups  ");
		ScrollPane scrollGroupPane = new ScrollPane();
		scrollGroupPane.setContent(gridPane);
		gridGroupPane.setAlignment(Pos.TOP_LEFT);
		gridGroupPane.setPrefWidth(200);
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
		borderPane.setPrefWidth(230);

		ToolBar toolBar = new ToolBar();
		Button btnColor = new Button();
		btnColor.setTranslateX(6);
		btnColor.setGraphic(new ImageView("icon_color.png"));
		btnColor.setPadding(Insets.EMPTY);

		btnColor.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				java.awt.Color c = JColorChooser.showDialog(null,
						"Choose a Color", null);
				if (c != null)
					legoGame.setColor(new Vec(c.getRed(), c.getGreen(), c
							.getBlue()));
				// colorPicker.show();

			}
		});

		btnColor.setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>() {

			@Override
			public void handle(javafx.scene.input.MouseEvent mouseEvent) {
				btnColor.setGraphic(new ImageView("icon_color_pressed.png"));
			}

		});

		btnColor.setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {

			@Override
			public void handle(javafx.scene.input.MouseEvent mouseEvent) {
				btnColor.setGraphic(new ImageView("icon_color.png"));
			}

		});

		Button btnExpandAll = new Button();
		btnExpandAll.setTranslateX(6);
		btnExpandAll.setGraphic(new ImageView("icon_collapse_plus.png"));
		btnExpandAll.setPadding(Insets.EMPTY);
		btnExpandAll.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (Util.collapsed) {
					Util.collapsed = false;
					btnExpandAll.setGraphic(new ImageView(
							"icon_collapse_plus.png"));
					collapsedIcon.clear();
					addIconToPanel();
				} else {
					Util.collapsed = true;
					btnExpandAll.setGraphic(new ImageView(
							"icon_collapse_minus.png"));
					collapsedIcon.clear();
					for (int i = 0; i < categories.length; i++) {
						collapsedIcon.add(categories[i]);
					}
					addIconToPanel();
				}
			}
		});

		btnExpandAll
				.setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>() {

					@Override
					public void handle(javafx.scene.input.MouseEvent mouseEvent) {
						if (Util.collapsed) {
							btnExpandAll.setGraphic(new ImageView(
									"icon_collapse_pressed_plus.png"));
						} else {
							btnExpandAll.setGraphic(new ImageView(
									"icon_collapse_pressed_minus.png"));
						}
					}
				});

		btnExpandAll
				.setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {

					@Override
					public void handle(javafx.scene.input.MouseEvent mouseEvent) {
						if (Util.collapsed) {
							btnExpandAll.setGraphic(new ImageView(
									"icon_collapse_plus.png"));
						} else {
							btnExpandAll.setGraphic(new ImageView(
									"icon_collapse_minus.png"));
						}
					}

				});

		toolBar.getItems().addAll(btnColor, btnExpandAll);

		toolBar.setTranslateY(6);
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

				Tooltip tooltip = new Tooltip();
				tooltip.setText(modelName);
				tooltip.styleProperty().set(tooltipCss);
				button.setTooltip(tooltip);
				button.setOnMouseMoved(new EventHandler<javafx.scene.input.MouseEvent>() {
					@Override
					public void handle(javafx.scene.input.MouseEvent mouseEvent) {
						button.setStyle("-fx-cursor: hand");
						tooltip.show(button, mouseEvent.getSceneX()
								+ button.getScene().getWindow().getX() + 10,
								mouseEvent.getSceneY()
										+ button.getScene().getWindow().getY()
										+ 10);
					}
				});

				button.setOnMouseExited(new EventHandler<javafx.scene.input.MouseEvent>() {
					@Override
					public void handle(javafx.scene.input.MouseEvent t) {
						button.setStyle("-fx-cursor: default");
						tooltip.hide();
					}
				});

			} else {
				String modelBrickCatagory = modelName.substring(1);
				if (collapsedIcon.indexOf(modelBrickCatagory) != -1) {
					button.setGraphic(new ImageView(modelName + "_plus.png"));
				} else {
					button.setGraphic(new ImageView(modelName + "_minus.png"));
				}

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

		exampleFrame.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {

			}

			@Override
			public void componentResized(ComponentEvent e) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						int value = 464;
						setSizeBtn.setTranslateX(exampleFrame.getWidth()
								- value);
						heightText.setTranslateX(exampleFrame.getWidth()
								- value);
						spaceLabel.setTranslateX(exampleFrame.getWidth()
								- value);
						widthText.setTranslateX(exampleFrame.getWidth() - value);
						sizeLabel.setTranslateX(exampleFrame.getWidth() - value);
					}
				});
			}

			@Override
			public void componentMoved(ComponentEvent e) {

			}

			@Override
			public void componentHidden(ComponentEvent e) {

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

	public static void enableUndo(boolean value) {
		menuUndo.setDisable(value);
		undoBtn.setDisable(value);
	}

	public static void enableRedo(boolean value) {
		menuRedo.setDisable(value);
		redoBtn.setDisable(value);
	}

	public static void enableSave(boolean value) {
		menuSave.setDisable(value);
		saveBtn.setDisable(value);

		menuSaveAs.setDisable(value);
		selectAll.setDisable(value);
	}

	public static void enableCopyCut(boolean value) {
		menuCopy.setDisable(value);
		menuCut.setDisable(value);
	}

	public static void enablePaste(boolean value) {
		menuPaste.setDisable(value);
	}
}
