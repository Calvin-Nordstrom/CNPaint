package com.calvinnordstrom.cnpaint.view;

import com.calvinnordstrom.cnpaint.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public class MainView {
    private final BorderPane mainView;
    final Map<String, MenuItem> menuItems;
    final Map<String, Button> buttons;

    public MainView() {
        mainView = new BorderPane();
        menuItems = new HashMap<>();
        buttons = new HashMap<>();

        initTop();
        initLeft();
        initCenter();
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
        mainView.setTop(menuBar);
    }

    private void initLeft() {
        VBox toolControls = new VBox();
        toolControls.setMinWidth(200);
        toolControls.setStyle("-fx-background-color: rgb(64, 64, 64);");
        toolControls.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
                BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE,
                CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));
        mainView.setLeft(toolControls);
    }

    private void initCenter() {
        Image image = new Image(String.valueOf(Main.class.getResource("IMG_2878.PNG")));
        Image image1 = new Image(String.valueOf(Main.class.getResource("IMG_3698.JPG")));
        EditorPane editorPane = new EditorPane(image);
        mainView.setCenter(editorPane);
    }

    private void initBottom() {
        EditorInfoPane editorInfoPane = new EditorInfoPane(this);
        mainView.setBottom(editorInfoPane);
    }

    public Parent getView() {
        return mainView;
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

    /* ****************************************************************
     *                        Utility Methods                         *
     *****************************************************************/

    void addMenuItemToMenu(Menu parent, MenuItem child, String key) {
        parent.getItems().add(child);
        menuItems.put(key, child);
    }
}
