package com.calvinnordstrom.cnpaint.view;

import com.calvinnordstrom.cnpaint.Main;
import com.calvinnordstrom.cnpaint.event.PaneEventHandler;
import com.calvinnordstrom.cnpaint.property.PositionProperty;
import com.calvinnordstrom.cnpaint.property.BoundsProperty;
import com.calvinnordstrom.cnpaint.view.node.ZoomPane;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class EditorPane extends StackPane {
    private final PositionProperty positionProperty = new PositionProperty();
    private final BoundsProperty boundsProperty = new BoundsProperty();
    private final ZoomPane imagePane;
    private Image image;

    public EditorPane() {
        imagePane = new ZoomPane();
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
    }

    public void setImage(Image image) {
        this.image = image;

        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(image.getWidth());
        imageView.setFitHeight(image.getHeight());

        Group group = new Group();
        group.getChildren().add(imagePane);
        getChildren().add(imagePane);
        imagePane.getChildren().addAll(imageView);

        PaneEventHandler paneEventHandler = new PaneEventHandler(imagePane);
        addEventFilter(MouseEvent.MOUSE_PRESSED, paneEventHandler.getOnMousePressed());
        addEventFilter(MouseEvent.MOUSE_DRAGGED, paneEventHandler.getOnMouseDragged());
        addEventFilter(ScrollEvent.SCROLL, paneEventHandler.getOnScroll());

//        imageView.addEventFilter(MouseEvent.MOUSE_MOVED, event -> {
//            positionProperty.setMouseX(event.getX());
//            positionProperty.setMouseY(event.getY());
//        });

        addEventFilter(MouseEvent.MOUSE_MOVED, event -> {
            // Get the mouse position relative to the StackPane
            double mouseX = event.getX();
            double mouseY = event.getY();

            // Get the position of the Node (image) relative to the StackPane
            double nodeX = imageView.getLayoutX() + imagePane.getPanX();
            double nodeY = imageView.getLayoutY() + imagePane.getPanY();

            // Calculate the mouse position relative to the Node (image) with the zoom factor
            double relativeX = (mouseX - nodeX) / imagePane.getScale();
            double relativeY = (mouseY - nodeY) / imagePane.getScale();

            positionProperty.setX(relativeX);
            positionProperty.setY(relativeY);
        });

        boundsProperty.setWidth(image.getWidth());
        boundsProperty.setHeight(image.getHeight());
    }

    public PositionProperty mouseLocationProperty() {
        return positionProperty;
    }

    public BoundsProperty paneBoundsProperty() {
        return boundsProperty;
    }
}
