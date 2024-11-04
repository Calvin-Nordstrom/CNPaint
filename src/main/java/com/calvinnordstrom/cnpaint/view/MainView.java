package com.calvinnordstrom.cnpaint.view;

import com.calvinnordstrom.cnpaint.Main;
import com.calvinnordstrom.cnpaint.tool.ToolManager;
import com.calvinnordstrom.cnpaint.tool.ToolType;
import com.calvinnordstrom.cnpaint.util.ImageUtils;
import com.calvinnordstrom.cnpaint.util.ServiceLocator;
import com.calvinnordstrom.cnpaint.view.node.EditorTab;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MainView extends BorderPane {
    private final ToolsPane toolsPane = new ToolsPane();
    private final TabPane tabPane = new TabPane();

    public MainView() {
        initTop();
        initLeft();
        initCenter();
        initRight();
        initBottom();
    }

    private void initTop() {
        MenuPane menuPane = new MenuPane();

        ToolManager tm = ToolManager.getInstance();
        ComboBox<ToolType> comboBox = new ComboBox<>();
        for (ToolType toolType : tm.getToolKeys()) {
            comboBox.getItems().add(toolType);
        }
        comboBox.getSelectionModel().selectFirst();
        comboBox.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            tm.setTool(newValue);
        });

        HBox toolControls = new HBox(tm.getToolControls(tm.getTool()));
        HBox.setHgrow(toolControls, Priority.ALWAYS);
        tm.toolProperty().addListener((_, _, newValue) -> {
            comboBox.getSelectionModel().select(tm.getToolType(newValue));
            toolControls.getChildren().setAll(tm.getToolControls(newValue));
        });

        HBox controlsPane = new HBox(5, comboBox, toolControls);
        controlsPane.getStyleClass().add("tool-controls");

        setTop(new VBox(menuPane, controlsPane));
    }

    private void initLeft() {
        setLeft(toolsPane);
    }

    private void initCenter() {
        Image image1 = new Image(String.valueOf(Main.class.getResource("IMG_2878.PNG")));
        Image image2 = new Image(String.valueOf(Main.class.getResource("IMG_3698.JPG")));

        EditorPane editor1 = new EditorPane(ImageUtils.toWritableImage(image1));
        EditorPane editor2 = new EditorPane(ImageUtils.toWritableImage(image2));

        EditorTab one = new EditorTab("IMG_2878.PNG", editor1);
        EditorTab two = new EditorTab("IMG_3698.JPG", editor2);
        tabPane.getTabs().addAll(one, two);

        tabPane.getSelectionModel().selectedItemProperty().addListener(((_, _, newValue) -> {
            EditorPane editorPane = (EditorPane) newValue.getContent();
            setBottom(ServiceLocator.getInstance().getEditorControlsPane(editorPane));
        }));

        setCenter(tabPane);
    }

    private void initRight() {
        ControlsPane controls = new ControlsPane();
        setRight(controls);
    }

    private void initBottom() {
        EditorPane editorPane = (EditorPane) tabPane.getSelectionModel()
                .selectedItemProperty().get().getContent();
        setBottom(ServiceLocator.getInstance().getEditorControlsPane(editorPane));
    }

    public void drawImage() {
        EditorPane editorPane = (EditorPane) tabPane.getSelectionModel()
                .selectedItemProperty().get().getContent();
        editorPane.drawImage();
    }

    public WritableImage getCurrentImage() {
        EditorPane editorPane = (EditorPane) tabPane.getSelectionModel()
                .selectedItemProperty().get().getContent();
        return editorPane.getImage();
    }
}
