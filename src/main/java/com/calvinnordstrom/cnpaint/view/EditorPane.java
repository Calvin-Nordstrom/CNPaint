package com.calvinnordstrom.cnpaint.view;

import com.calvinnordstrom.cnpaint.Main;
import com.calvinnordstrom.cnpaint.property.ImageBounds;
import com.calvinnordstrom.cnpaint.property.ImageScale;
import com.calvinnordstrom.cnpaint.property.MousePosition;
import com.calvinnordstrom.cnpaint.util.DragContext;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class EditorPane extends BorderPane {
    private final StackPane stackPane;
    private final Canvas canvas;
    private final ImageBounds imageBounds;
    private final ImageScale imageScale;
    private Image image;

    public EditorPane() {
        this(new Image(String.valueOf(Main.class.getResource("default.png"))));
    }

    public EditorPane(Image image) {
        stackPane = new StackPane();
        canvas = new Canvas();
        imageBounds = new ImageBounds();
        imageScale = new ImageScale();

        getStyleClass().add("editor-pane");

        setImage(image);

        init();
        initEventHandlers();
    }

    private void init() {
        setCenter(stackPane);
//        Image cursor = new Image(String.valueOf(Main.class.getResource("cursor/editor_cursor.png")));
//        setCursor(new ImageCursor(cursor, cursor.getWidth() / 2, cursor.getHeight() / 2));
        stackPane.setMinSize(0, 0);

        canvas.getGraphicsContext2D().setImageSmoothing(false);
        canvas.widthProperty().bind(stackPane.widthProperty());
        canvas.heightProperty().bind(stackPane.heightProperty());
        stackPane.getChildren().addAll(canvas);
    }

    private void initEventHandlers() {
        DragContext dragContext = new DragContext();

        stackPane.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (!event.isMiddleButtonDown()) return;
            dragContext.x = event.getX();
            dragContext.y = event.getY();
            dragContext.dx = imageBounds.getX();
            dragContext.dy = imageBounds.getY();
        });
        stackPane.addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> {
            if (!event.isMiddleButtonDown()) return;
            imageBounds.setX(dragContext.dx + event.getX() - dragContext.x);
            imageBounds.setY(dragContext.dy + event.getY() - dragContext.y);
            drawImage(imageBounds.getX(), imageBounds.getY());
        });
        stackPane.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (!event.isControlDown()) return;
            double oldScale = imageScale.getScale() / 100;

            if (event.getDeltaY() > 0) {
                imageScale.upscale();
            } else {
                imageScale.downscale();
            }

            double f = (imageScale.getScale() / 100) / oldScale;
            double mouseX = event.getX();
            double mouseY = event.getY();
            imageBounds.setX(mouseX - (mouseX - imageBounds.getX()) * f);
            imageBounds.setY(mouseY - (mouseY - imageBounds.getY()) * f);
            drawImage(imageBounds.getX(), imageBounds.getY());
        });
        stackPane.addEventFilter(MouseEvent.MOUSE_MOVED, event -> {
            MousePosition.setX((event.getX() - imageBounds.getX()) / (imageScale.getScale() / 100));
            MousePosition.setY((event.getY() - imageBounds.getY()) / (imageScale.getScale() / 100));
        });
        stackPane.widthProperty().addListener((_, _, _) -> {
            scaleToImage();
            centerImage();
        });
        stackPane.heightProperty().addListener((_, _, _) -> {
            scaleToImage();
            centerImage();
        });
    }

    public void drawImage(double x, double y) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        clearCanvas();
        double width = image.getWidth() * imageScale.getScale() / 100;
        double height = image.getHeight() * imageScale.getScale() / 100;
        gc.drawImage(image, x, y, width, height);
        imageBounds.setX(x);
        imageBounds.setY(y);
    }

    private void clearCanvas() {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void centerImage() {
        imageScale.setScale(imageScale.getDefaultEditorScale());
        double canvasCenterX = canvas.getWidth() / 2;
        double canvasCenterY = canvas.getHeight() / 2;
        double imageWidth = image.getWidth() * imageScale.getScale() / 100;
        double imageHeight = image.getHeight() * imageScale.getScale() / 100;
        double x = canvasCenterX - imageWidth / 2;
        double y = canvasCenterY - imageHeight / 2;
        drawImage(x, y);
    }

    public void scaleToImage() {
        double widthRatio = image.getWidth() / canvas.getWidth();
        double heightRatio = image.getHeight() / canvas.getHeight();
        double ratio = Math.max(widthRatio, heightRatio);
        imageScale.setDefaultEditorScale(75 / ratio);
    }

    public void upscale() {
        double oldScale = imageScale.getScale() / 100;
        imageScale.upscale();
        Point2D center = getImageCenter(oldScale);
        drawImage(center.getX(), center.getY());
    }

    public void downscale() {
        double oldScale = imageScale.getScale() / 100;
        imageScale.downscale();
        Point2D center = getImageCenter(oldScale);
        drawImage(center.getX(), center.getY());
    }

    private Point2D getImageCenter(double oldScale) {
        double imageCenterX = imageBounds.getX() + (image.getWidth() * oldScale) / 2;
        double imageCenterY = imageBounds.getY() + (image.getHeight() * oldScale) / 2;
        double newImageWidth = image.getWidth() * imageScale.getScale() / 100;
        double newImageHeight = image.getHeight() * imageScale.getScale() / 100;
        double x = imageCenterX - newImageWidth / 2;
        double y = imageCenterY - newImageHeight / 2;
        return new Point2D(x, y);
    }

    public ChangeListener<Number> getChangeListener() {
        return (ObservableValue<? extends Number> _, Number _, Number newValue) -> {
            double oldScale = imageScale.getScale() / 100;
            imageScale.setScale(ImageScale.fromPercent((double) newValue));
            Point2D center = getImageCenter(oldScale);
            drawImage(center.getX(), center.getY());
        };
    }

    public ImageBounds getImageBounds() {
        return imageBounds;
    }

    public ImageScale getImageScale() {
        return imageScale;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
        imageBounds.setWidth(image.getWidth());
        imageBounds.setHeight(image.getHeight());
    }
}
