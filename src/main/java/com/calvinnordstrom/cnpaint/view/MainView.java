package com.calvinnordstrom.cnpaint.view;

import com.calvinnordstrom.cnpaint.Main;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.util.HashMap;
import java.util.Map;

public class MainView {
    final Map<String, MenuItem> menuItems;
    final Map<String, Button> buttons;
    final Map<String, Slider> sliders;
    private final BorderPane mainView;
    private final EditorPane editorPane;
    private final EditorControlsPane editorControlsPane;

    public MainView() {
        menuItems = new HashMap<>();
        buttons = new HashMap<>();
        sliders = new HashMap<>();

        mainView = new BorderPane();
        editorPane = new EditorPane();
        editorControlsPane = new EditorControlsPane(this);

        initTop();
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

    private void initCenter() {
        mainView.setCenter(editorPane);

        Image image1 = new Image(String.valueOf(Main.class.getResource("IMG_2878.PNG")));
        Image image2 = new Image(String.valueOf(Main.class.getResource("IMG_3698.JPG")));
        Image image3 = new Image(String.valueOf(Main.class.getResource("test.png")));
        Image image4 = new Image(String.valueOf(Main.class.getResource("default.png")));
        editorPane.setImage(image1);
    }

    private void initBottom() {
        mainView.setBottom(editorControlsPane);

        addButtonListener("downscaleButton", _ -> editorPane.downscale());
        addSliderListener("scaleSlider", editorPane.getChangeListener());
        addButtonListener("upscaleButton", _ -> editorPane.upscale());
        addButtonListener("centerImage", _ -> editorPane.centerImage());
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
