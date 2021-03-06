package cmdAnimator;

import java.awt.Point;
import java.io.File;
import java.net.URL;

import cmdAnimator.GameCanvasActions.CanvasImage;
import cmdAnimator.GameCanvasActions.CanvasText;
import cmdAnimator.GameCanvasActions.CommandParser;
import cmdAnimator.GameCanvasActions.GameAnimator;
import cmdAnimator.GameUI.GameCanvas;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class GameGui extends Group {
	private static final int HELP_PANEL_WIDTH = 200;
	private static final int LIBRARY_WIDTH = 180;
	private TextArea outputField;
	private Label libraryTitle;
	private MenuBar libraryMenu;
	private Label framesLibrary;
	private Label currentPointOnCanvas;
	private VBox imageLibrary;
	private FlowPane canvasPane;
	private GameCanvas stage;
	private TextField userInputField;
	private Button enterButton;
	private Label helpTitle;
	private TextArea helpCommands;
	private final String outputFieldText = "---output---";
	private Button addImageButton;
	private Button runCommandFileButton;
	private Button changeTextStyleButton;
	public static boolean buttonExecuteCalled = false;
	private final int ICON_SIZE = 25;
	private ScrollPane sp;

	public GameGui() {
		outputField = new TextArea(outputFieldText);
		libraryTitle = new Label();
		helpTitle = new Label();
		stage = new GameCanvas(null);
		userInputField = new TextField("");
		enterButton = new Button("Enter");
		helpCommands = new TextArea();
		canvasPane = new FlowPane();
		libraryMenu = new MenuBar();
		framesLibrary = new Label("Current Frame: 0");
		currentPointOnCanvas = new Label("Point: " + "(0,0)");
		imageLibrary = new VBox();
		addImageButton = new Button("Add image");
		runCommandFileButton = new Button("Run Command file");
		changeTextStyleButton = new Button("Change Text Style");
		sp = new ScrollPane();

		setupIDs();
		setupStage();
		setupLibrary();
		setupHelp();

		BorderPane pane = new BorderPane();
		pane.setId("mainPane");
		VBox outputAreas = new VBox();
		HBox userinputArea = new HBox();
		VBox LibraryArea = new VBox();
		VBox helpArea = new VBox();

		sp.setContent(imageLibrary);
		canvasPane.getChildren().add(stage);
		userinputArea.getChildren().addAll(userInputField, enterButton);
		outputAreas.getChildren().addAll(canvasPane, outputField, userinputArea);
		outputAreas.setId("outputArea");

		LibraryArea.getChildren().addAll(libraryTitle, framesLibrary, currentPointOnCanvas, sp, addImageButton,
				changeTextStyleButton, runCommandFileButton);
		LibraryArea.setId("libraryArea");
		helpArea.getChildren().addAll(helpTitle, helpCommands);

		pane.setCenter(outputAreas);
		pane.setLeft(helpArea);
		pane.setRight(LibraryArea);

		this.getChildren().add(pane);
	}

	// set up id of all objects on the screen so that they can be accessed by
	// css files
	private void setupIDs() {
		canvasPane.setId("canvasContainer");
		outputField.setId("outputField");
		;
		libraryTitle.setId("libraryTitle");
		framesLibrary.setId("frameLibrary");
		currentPointOnCanvas.setId("canvasPointLocationLabel");
		helpTitle.setId("helpTitle");
		libraryMenu.setId("libraryMenu");
		stage.setId("stage");
		userInputField.setId("commandLine");
		enterButton.setId("enterButton");
		helpCommands.setId("helpCommandArea");
		// helpScroller.setId("helpScoller");
		addImageButton.setId("imageButton");
		runCommandFileButton.setId("runCommandButton");
		changeTextStyleButton.setId("textButton");
		helpCommands.setEditable(false);

	}

	private void setupStage() {
		canvasPane.setPrefSize(300, 300);
		enterButton.setPrefSize(55, 35);

		userInputField.setPrefSize(555, 35);
		userInputField.setMinHeight(35);
		userInputField.setMinWidth(555);
		userInputField.setMaxHeight(35);
		userInputField.setMaxWidth(555);

		outputField.setPrefSize(610, 130);
		outputField.setMinHeight(130);
		outputField.setMinWidth(610);
		outputField.setMaxHeight(130);
		outputField.setMaxWidth(610);
		outputField.setEditable(false);

		// add tooltip to output field
		Tooltip tip = new Tooltip("Output of your previous commands");
		outputField.setTooltip(tip);
	}

	private void setupHelp() {
		helpTitle.setText("Commands");
		helpTitle.setPrefSize(HELP_PANEL_WIDTH, 50);
		helpCommands.setWrapText(true);
		helpCommands.setMinHeight(425);
		helpCommands.setMaxHeight(425);
		helpCommands.setMinWidth(HELP_PANEL_WIDTH);
		helpCommands.setMaxWidth(HELP_PANEL_WIDTH);
		helpCommands.setEditable(false);

		helpCommands.setText("This is where help file will be placed");
	}

	private void setupLibrary() {
		addImageButton.setPrefSize(LIBRARY_WIDTH, 10);
		runCommandFileButton.setPrefSize(LIBRARY_WIDTH, 10);
		changeTextStyleButton.setPrefSize(LIBRARY_WIDTH, 10);
		setUpActionForTextStyleButton();
		libraryTitle.setText("Library");
		libraryTitle.setPrefSize(LIBRARY_WIDTH, 50);
		sp.setPrefSize(LIBRARY_WIDTH, 315);
		sp.setMinWidth(LIBRARY_WIDTH);
		sp.setMaxWidth(LIBRARY_WIDTH);
		sp.setMaxHeight(315);
		sp.setMinHeight(315);

		// libraryMenu.getMenus().add(imageLibrary);

	}

	private void setUpActionForTextStyleButton() {
		changeTextStyleButton.setOnAction(new EventHandler() {

			@Override
			public void handle(Event event) {
				Prompts.promptUserToChangeTextStyle("color");
			}

		});
	}

	public void setDisabledForEnterButton(boolean b) {
		enterButton.setDisable(b);

	}

	public void setCurrentFrameLabel(String newLabel) {
		framesLibrary.setText(newLabel);
	}

	public void setCurrentPointLabel(String point) {
		currentPointOnCanvas.setText("Point: " + point);
	}

	public void setScreen(GameCanvas gameCanvas) {
		if (gameCanvas != null) {
			stage.setImagesToAdd(gameCanvas.getImagesToAdd());
			stage.setTextToWrite(gameCanvas.getTextToWrite());
			stage.setBackgroundImage(gameCanvas.getBackgroundImage());

		} else {
			stage.clearCanvas();
		}
		stage.updatePane();

	}

	public GameCanvas getScreen() {
		return stage;
	}

	public TextArea getOutputScreen() {
		return outputField;
	}

	public Button getEnterButton() {
		return enterButton;
	}

	public TextField getCommandLine() {
		return userInputField;
	}

	public void addUserInputToOutPutFieldAndClearUserInput() {
		appendTextToOutputScreen(userInputField.getText());
		userInputField.setText("");
	}

	public String getOutputFieldDefaultText() {
		return outputFieldText;
	}

	public void appendTextToOutputScreen(String string) {
		outputField.appendText("\n" + string);
	}

	public void addTextToCanvas(CanvasText newText) {
		stage.addText(newText);
	}

	public boolean addImageToCanvas(CanvasImage canvasImage) {
		boolean imageSafelyAdded = stage.addImage(canvasImage);

		if (imageSafelyAdded && !buttonExecuteCalled) {
			addNewImageToImageLibrary(canvasImage);
		}
		buttonExecuteCalled = false;
		return stage.addImage(canvasImage);
	}

	private void addNewImageToImageLibrary(CanvasImage canvasImage) {

		Image image = new Image(new File(canvasImage.getImageFilename()).toURI().toString(), ICON_SIZE, ICON_SIZE,
				false, false);
		AddImageButton newImage = new AddImageButton(canvasImage.getImageFilename());
		if (!isImageAlreadyAddedToImageLibrary(canvasImage.getImageFilename())) {
			ImageView icon = new ImageView(image);
			newImage.setGraphic(icon);
			newImage.setPrefSize(LIBRARY_WIDTH, ICON_SIZE + 10);
			newImage.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					String point = Prompts.promptUserForPoint();
					GUI.getInstance().buttonExecuteCalled = true;
					if (point != null && point.equalsIgnoreCase("background")) {
						GuiCommands.executeBackgroundImageAdditionCommand(GUI.getInstance(),
								canvasImage.getImageFilename());
					} else
						GuiCommands.executeImageAdditionCommand(GUI.getInstance(), canvasImage.getImageFilename(),
								point, Prompts.imageHeightPrompt(), Prompts.imageWidthPrompt());

				}

			});
			imageLibrary.getChildren().add(newImage);
		}
	}

	private boolean isImageAlreadyAddedToImageLibrary(String image) {
		int i = 0;
		while (imageLibrary.getChildren() != null && i < imageLibrary.getChildren().size()) {
			if (((AddImageButton) imageLibrary.getChildren().get(i)).getImagePath().equals(image)) {
				return true;
			}
			i++;
		}

		return false;
	}

	public VBox getImageLibrary() {
		return imageLibrary;
	}

	public boolean addBackgroundToCanvas(CanvasImage canvasImage) {
		boolean imageSafelyAdded = stage.setBackgroundImage(canvasImage);
		if (GameAnimator.getInstance().getCurrentFrame() != null && GameAnimator.getInstance().isViewingFrame())
			GameAnimator.getInstance().getCurrentFrame().setBackgroundImage(canvasImage);
		if (imageSafelyAdded && !buttonExecuteCalled) {
			addNewImageToImageLibrary(canvasImage);
		}
		buttonExecuteCalled = false;

		return imageSafelyAdded;
	}

	public void clearStage() {
		if (GameAnimator.getInstance().getNumberOfCurrentFrame() > 0 && GameAnimator.getInstance().isViewingFrame())
			GameAnimator.getInstance().getCurrentFrame().clearCanvas();
		stage.clearCanvas();
	}

	public String getCommandLineText() {
		return userInputField.getText();
	}
	
	public void setCommandLineText(String string){
		userInputField.setText(string);
	}

	public TextArea getHelpCommands() {
		return helpCommands;
	}

	public Button getAddImageButton() {
		return addImageButton;
	}

	public Button getRunCommandFileButton() {
		return runCommandFileButton;
	}

	public int getHelpPanelWidth() {
		return HELP_PANEL_WIDTH;
	}
}
