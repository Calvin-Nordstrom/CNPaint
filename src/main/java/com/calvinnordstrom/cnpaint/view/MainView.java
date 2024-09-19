package com.calvinnordstrom.cnpaint.view;

import com.calvinnordstrom.cnpaint.Main;
import com.calvinnordstrom.cnpaint.view.node.EditorTab;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public class MainView extends BorderPane {
    final Map<String, MenuItem> menuItems;
    final Map<String, Button> buttons;
    final Map<String, Slider> sliders;

    private final TabPane tabPane;
    private final ToolsPane toolsPane;

    public MainView() {
        menuItems = new HashMap<>();
        buttons = new HashMap<>();
        sliders = new HashMap<>();

        tabPane = new TabPane();
        toolsPane = new ToolsPane();

        initTop();
        initLeft();
        initCenter();
        initRight();
        initBottom();
    }

    private void initTop() {
        Menu fileMenu = new Menu("File");
        addMenuItemToMenu(fileMenu, new MenuItem("Close"), "close");

        Menu editMenu = new Menu("Edit");

        Menu viewMenu = new Menu("View");

        MenuBar menuBar = new MenuBar(fileMenu, editMenu, viewMenu);
//        menuBar.setStyle("-fx-background-color: rgb(64, 64, 64);");
//        menuBar.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
//                BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE,
//                CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));
        setTop(menuBar);
    }

    private void initLeft() {
        VBox vBox = new VBox();
        vBox.setStyle("-fx-background-color: rgb(64, 64, 64); -fx-padding: 3px;");
        vBox.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
                BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE,
                CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));
        vBox.setMinWidth(50);
        setLeft(vBox);
    }

    private void initCenter() {
        Image image1 = new Image(String.valueOf(Main.class.getResource("IMG_2878.PNG")));
        Image image2 = new Image(String.valueOf(Main.class.getResource("IMG_3698.JPG")));
        Image image3 = new Image(String.valueOf(Main.class.getResource("test.png")));
        Image image4 = new Image(String.valueOf(Main.class.getResource("default.png")));

        EditorPane editor1 = new EditorPane(image1);
        EditorPane editor2 = new EditorPane(image2);

        EditorTab one = new EditorTab("image1", editor1);
        EditorTab two = new EditorTab("image2", editor2);
        tabPane.getTabs().addAll(one, two);

        tabPane.getSelectionModel().selectedItemProperty().addListener(((_, _, newValue) -> {
            EditorTab tab = (EditorTab) newValue;
            setBottom(tab.getEditorControls());
        }));

        setCenter(tabPane);
    }

    private void initRight() {
        VBox vBox = new VBox();
        vBox.setStyle("-fx-background-color: rgb(64, 64, 64); -fx-padding: 3px;");
        vBox.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
                BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));
        vBox.setMinWidth(400);
        setRight(vBox);
    }

    private void initBottom() {
        EditorTab tab = (EditorTab) tabPane.getSelectionModel().selectedItemProperty().get();
        setBottom(tab.getEditorControls());
    }

    /* ****************************************************************
     *                    Event Listener Methods                      *
     *****************************************************************/

    public void addMenuItemListener(String key, EventHandler<ActionEvent> handler) {
        if (menuItems.containsKey(key)) {
            menuItems.get(key).setOnAction(handler);
        }
    }

    public void addButtonListener(String key, EventHandler<MouseEvent> handler) {
        if (buttons.containsKey(key)) {
            buttons.get(key).setOnMouseClicked(handler);
        }
    }

    public void addSliderListener(String key, ChangeListener<Number> listener) {
        if (sliders.containsKey(key)) {
            sliders.get(key).valueProperty().addListener(listener);
        }
    }

    /* ****************************************************************
     *                        Utility Methods                         *
     *****************************************************************/

    void addMenuItemToMenu(Menu parent, MenuItem child, String key) {
        parent.getItems().add(child);
        menuItems.put(key, child);
    }
}
