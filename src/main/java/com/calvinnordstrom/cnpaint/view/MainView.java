package com.calvinnordstrom.cnpaint.view;

import com.calvinnordstrom.cnpaint.Main;
import com.calvinnordstrom.cnpaint.property.PositionProperty;
import com.calvinnordstrom.cnpaint.property.BoundsProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public class MainView {
    private final BorderPane mainView;
    private final Map<String, MenuItem> menuItems;
    private final Map<String, Pane> panes;

    public MainView() {
        mainView = new BorderPane();
        menuItems = new HashMap<>();
        panes = new HashMap<>();

        initMenuBar();
        initEditor();
    }

    private void initMenuBar() {
        MenuBar menuBar = new MenuBar();
//        menuBar.setStyle("-fx-background-color: rgb(64, 64, 64);");
//        menuBar.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
//                BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE,
//                CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));
        mainView.setTop(menuBar);

        Menu fileMenu = new Menu("File");
        addMenuItemToMenu(fileMenu, new MenuItem("Close"), "close");
        menuBar.getMenus().add(fileMenu);

        Menu editMenu = new Menu("Edit");
        menuBar.getMenus().add(editMenu);

        Menu viewMenu = new Menu("View");
        menuBar.getMenus().add(viewMenu);
    }

    private void initEditor() {
        VBox toolControls = new VBox();
        toolControls.setMinWidth(200);
        toolControls.setStyle("-fx-background-color: rgb(64, 64, 64);");
        toolControls.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
                BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE,
                CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));
        mainView.setLeft(toolControls);

        Image image = new Image(String.valueOf(Main.class.getResource("IMG_2878.PNG")));
        EditorPane editorPane = new EditorPane();
        editorPane.setImage(image);
        mainView.setCenter(editorPane);

        PositionProperty positionProperty = editorPane.mouseLocationProperty();
        BoundsProperty boundsProperty = editorPane.paneBoundsProperty();
        EditorInfoPane editorInfoPane = new EditorInfoPane(positionProperty, boundsProperty);
        mainView.setBottom(editorInfoPane);
    }

    public void addMenuItemListener(String key, EventHandler<ActionEvent> handler) {
        if (menuItems.containsKey(key)) {
            menuItems.get(key).setOnAction(handler);
        }
    }

    public void addScrollPaneListener(String key, EventHandler<ScrollEvent> handler) {
        if (panes.containsKey(key)) {
            panes.get(key).setOnScroll(handler);
        }
    }

    public Parent getView() {
        return mainView;
    }

    /* ****************************************************************
     *                        Utility Methods                         *
     *                                                                *
     *  Methods for adding Nodes to the UI and their data structures  *
     *                                                                *
     *****************************************************************/

    private void addMenuItemToMenu(Menu parent, MenuItem child, String key) {
        parent.getItems().add(child);
        menuItems.put(key, child);
    }

    private void addPaneToScrollPane(ScrollPane parent, Pane child, String key) {
        parent.setContent(child);
        panes.put(key, child);
    }
}
