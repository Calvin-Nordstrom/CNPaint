package com.calvinnordstrom.cnpaint.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class MainView {
    private final VBox mainView;
    private final Map<String, MenuItem> menuItems;
    private final Map<String, Pane> panes;

    public MainView() {
        mainView = new VBox();
        menuItems = new HashMap<>();
        panes = new HashMap<>();

        initMenuBar();
        initImageEditor();
    }

    private void initMenuBar() {
        MenuBar menuBar = new MenuBar();
        mainView.getChildren().add(menuBar);

        Menu fileMenu = new Menu("File");
        addMenuItemToMenu(fileMenu, new MenuItem("Close"), "close");
        menuBar.getMenus().add(fileMenu);

        Menu editMenu = new Menu("Edit");
        menuBar.getMenus().add(editMenu);

        Menu viewMenu = new Menu("View");
        menuBar.getMenus().add(viewMenu);
    }

    private void initImageEditor() {
        VBox vBox = new VBox();
        mainView.getChildren().add(vBox);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHvalue(0.5);
        scrollPane.setVvalue(0.5);
        scrollPane.setPannable(true);
        vBox.getChildren().add(scrollPane);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(3200);
        anchorPane.setPrefHeight(1600);
        anchorPane.setStyle("-fx-background-color: grey;");
        anchorPane.setOnMouseDragged(event -> {
            if (event.isSecondaryButtonDown() || event.isPrimaryButtonDown()) {
                event.consume();
            }
        });
        addPaneToScrollPane(scrollPane, anchorPane, "scrollPane");

        AnchorPane anchorPane1 = new AnchorPane();
        anchorPane1.setPrefWidth(1280);
        anchorPane1.setPrefHeight(720);
        anchorPane1.setStyle("-fx-background-color: white;");
        anchorPane1.translateXProperty()
                .bind(anchorPane.widthProperty().subtract(anchorPane1.widthProperty())
                        .divide(2));
        anchorPane1.translateYProperty()
                .bind(anchorPane.heightProperty().subtract(anchorPane1.heightProperty())
                        .divide(2));
        anchorPane.getChildren().add(anchorPane1);
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

    /*
     *  Utility methods for adding Nodes to the UI and their data structures
     */

    private void addMenuItemToMenu(Menu parent, MenuItem child, String key) {
        parent.getItems().add(child);
        menuItems.put(key, child);
    }

    private void addPaneToScrollPane(ScrollPane parent, Pane child, String key) {
        parent.setContent(child);
        panes.put(key, child);
    }
}
