package com.calvinnordstrom.cnpaint.view;

import com.calvinnordstrom.cnpaint.Main;
import com.calvinnordstrom.cnpaint.view.node.EditorTab;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

public class MainView extends BorderPane {
    private final ToolsPane toolsPane;
    private final TabPane tabPane;
    private final ControlsPane controlsPane;

    public MainView() {
        toolsPane = new ToolsPane();
        tabPane = new TabPane();
        controlsPane = new ControlsPane();

        initTop();
        initLeft();
        initCenter();
        initRight();
        initBottom();
    }

    private void initTop() {
        Menu fileMenu = new Menu("File");
        MenuItem close = new MenuItem("Close");
        ServiceLocator.register("close", close);
        fileMenu.getItems().add(close);

        Menu editMenu = new Menu("Edit");

        Menu viewMenu = new Menu("View");

        MenuBar menuBar = new MenuBar(fileMenu, editMenu, viewMenu);
        setTop(menuBar);
    }

    private void initLeft() {
        setLeft(toolsPane);
    }

    private void initCenter() {
        Image image1 = new Image(String.valueOf(Main.class.getResource("IMG_2878.PNG")));
        Image image2 = new Image(String.valueOf(Main.class.getResource("IMG_3698.JPG")));
        Image image3 = new Image(String.valueOf(Main.class.getResource("test.png")));
        Image image4 = new Image(String.valueOf(Main.class.getResource("default.png")));

        EditorPane editor1 = new EditorPane(image1);
        EditorPane editor2 = new EditorPane(image2);

        EditorTab one = new EditorTab("IMG_2878.PNG", editor1);
        EditorTab two = new EditorTab("IMG_3698.JPG", editor2);
        tabPane.getTabs().addAll(one, two);

        tabPane.getSelectionModel().selectedItemProperty().addListener(((_, _, newValue) -> {
            EditorPane editorPane = (EditorPane) newValue.getContent();
            setBottom(ServiceLocator.getEditorControlsPane(editorPane));
        }));

        setCenter(tabPane);
    }

    private void initRight() {
        setRight(new ControlsPane());
    }

    private void initBottom() {
        EditorPane editorPane = (EditorPane) tabPane.getSelectionModel().selectedItemProperty().get().getContent();

        setBottom(ServiceLocator.getEditorControlsPane(editorPane));
    }
}
