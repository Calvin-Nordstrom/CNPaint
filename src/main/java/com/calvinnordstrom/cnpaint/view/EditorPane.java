package com.calvinnordstrom.cnpaint.view;

import com.calvinnordstrom.cnpaint.Main;
import com.calvinnordstrom.cnpaint.event.PaneEventHandler;
import com.calvinnordstrom.cnpaint.property.ImageBounds;
import com.calvinnordstrom.cnpaint.property.MousePosition;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class EditorPane extends StackPane {
    private final ZoomPane zoomPane;
    private Image image;

    public EditorPane() {
        zoomPane = new ZoomPane();
        setImage(new Image(String.valueOf(Main.class.getResource("default.png"))));

        init();
    }

    private void init() {
        setStyle("-fx-background-color: rgb(80, 80, 80);");
//        Image cursor = new Image(String.valueOf(Main.class.getResource("cursor/editor_cursor.png")));
//        setCursor(new ImageCursor(cursor, cursor.getWidth() / 2, cursor.getHeight() / 2));
        setCursor(Cursor.CROSSHAIR);

        Rectangle clip = new Rectangle();
        widthProperty().addListener(((_, _, newValue) -> clip.setWidth((Double) newValue)));
        heightProperty().addListener(((_, _, newValue) -> clip.setHeight((Double) newValue)));
        setClip(clip);

        Group group = new Group();
        group.getChildren().add(zoomPane);
        getChildren().add(zoomPane);

        PaneEventHandler paneEventHandler = new PaneEventHandler(zoomPane);
        addEventFilter(MouseEvent.MOUSE_PRESSED, paneEventHandler.getOnMousePressed());
        addEventFilter(MouseEvent.MOUSE_DRAGGED, paneEventHandler.getOnMouseDragged());
        addEventFilter(ScrollEvent.SCROLL, paneEventHandler.getOnScroll());
    }

    public void setImage(Image image) {
        this.image = image;
        ImageBounds.setWidth(image.getWidth());
        ImageBounds.setHeight(image.getHeight());

        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(image.getWidth());
        imageView.setFitHeight(image.getHeight());
        zoomPane.getChildren().addAll(imageView);

        addEventFilter(MouseEvent.MOUSE_MOVED, event -> {
            double relativeX = imageView.sceneToLocal(event.getSceneX(), event.getSceneY()).getX();
            double relativeY = imageView.sceneToLocal(event.getSceneX(), event.getSceneY()).getY();
            MousePosition.setX(relativeX);
            MousePosition.setY(relativeY);
        });
    }
}
