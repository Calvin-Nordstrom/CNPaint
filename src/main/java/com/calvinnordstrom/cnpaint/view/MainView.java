package com.calvinnordstrom.cnpaint.view;

import com.calvinnordstrom.cnpaint.Main;
import com.calvinnordstrom.cnpaint.event.PaneEventHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

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
        initImageEditor();
    }

    private void initMenuBar() {
        MenuBar menuBar = new MenuBar();
        mainView.setTop(menuBar);

        Menu fileMenu = new Menu("File");
        addMenuItemToMenu(fileMenu, new MenuItem("Close"), "close");
        menuBar.getMenus().add(fileMenu);

        Menu editMenu = new Menu("Edit");
        menuBar.getMenus().add(editMenu);

        Menu viewMenu = new Menu("View");
        menuBar.getMenus().add(viewMenu);
    }

    private void initImageEditor() {
        VBox toolControls = new VBox();
        toolControls.setMinWidth(200);
        toolControls.toFront();
        mainView.setLeft(toolControls);

        StackPane imagePane = new StackPane();
        imagePane.setStyle("-fx-background-color: black;");
        imagePane.setCursor(Cursor.CROSSHAIR);
        mainView.setCenter(imagePane);

        Rectangle clip = new Rectangle();
        imagePane.widthProperty().addListener(((observable, oldValue, newValue) -> {
            clip.setWidth((Double) newValue);
        }));
        imagePane.heightProperty().addListener(((observable, oldValue, newValue) -> {
            clip.setHeight((Double) newValue);
        }));
        imagePane.setClip(clip);

        ZoomPane zoomPane = new ZoomPane();

        ImageView imageView = new ImageView();
        Image image = new Image(String.valueOf(Main.class.getResource("IMG_2878.PNG")));
        imageView.setImage(image);
        imageView.setFitWidth(image.getWidth());
        imageView.setFitHeight(image.getHeight());

        Group group = new Group();
        group.getChildren().add(zoomPane);
        imagePane.getChildren().add(zoomPane);
        zoomPane.getChildren().addAll(imageView);

        PaneEventHandler paneEventHandler = new PaneEventHandler(zoomPane);
        imagePane.addEventFilter(MouseEvent.MOUSE_PRESSED, paneEventHandler.getOnMousePressed());
        imagePane.addEventFilter(MouseEvent.MOUSE_DRAGGED, paneEventHandler.getOnMouseDragged());
        imagePane.addEventFilter(ScrollEvent.SCROLL, paneEventHandler.getOnScroll());

//        zoomPane.layoutBoundsProperty().addListener((observable, oldBounds, newBounds) -> {
//            double width = newBounds.getWidth();
//            double height = newBounds.getHeight();
//            System.out.println("Pane Width: " + width + ", Pane Height: " + height);
//        });
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
