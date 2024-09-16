package com.calvinnordstrom.cnpaint.view;

import com.calvinnordstrom.cnpaint.Main;
import com.calvinnordstrom.cnpaint.property.ImageBounds;
import com.calvinnordstrom.cnpaint.property.ImageScale;
import com.calvinnordstrom.cnpaint.property.MousePosition;
import com.calvinnordstrom.cnpaint.util.DragContext;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;

public class EditorPane extends StackPane {
    private final Canvas canvas;
    private Image image;
    private double imageX = 0;
    private double imageY = 0;

    public EditorPane() {
        this(new Image(String.valueOf(Main.class.getResource("default.png"))));
    }

    public EditorPane(Image image) {
        canvas = new Canvas();

        setImage(image);

        init();
        initEventHandlers();
    }

    private void init() {
        setStyle("-fx-background-color: rgb(80, 80, 80);");
//        Image cursor = new Image(String.valueOf(Main.class.getResource("cursor/editor_cursor.png")));
//        setCursor(new ImageCursor(cursor, cursor.getWidth() / 2, cursor.getHeight() / 2));
        setCursor(Cursor.CROSSHAIR);
        setMinSize(0, 0);

        canvas.getGraphicsContext2D().setImageSmoothing(false);
        canvas.widthProperty().bind(widthProperty());
        canvas.heightProperty().bind(heightProperty());
        getChildren().addAll(canvas);
    }

    private void initEventHandlers() {
        DragContext dragContext = new DragContext();

        addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (!event.isMiddleButtonDown()) return;
            dragContext.x = event.getX();
            dragContext.y = event.getY();
            dragContext.dx = imageX;
            dragContext.dy = imageY;
        });
        addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> {
            if (!event.isMiddleButtonDown()) return;
            imageX = dragContext.dx + event.getX() - dragContext.x;
            imageY = dragContext.dy + event.getY() - dragContext.y;
            drawImage(imageX, imageY);
        });
        addEventFilter(ScrollEvent.SCROLL, event -> {
            if (!event.isControlDown()) return;
            double oldScale = ImageScale.getScale() / 100;

            if (event.getDeltaY() > 0) {
                ImageScale.upscale();
            } else {
                ImageScale.downscale();
            }

            double f = (ImageScale.getScale() / 100) / oldScale;
            double mouseX = event.getX();
            double mouseY = event.getY();
            imageX = mouseX - (mouseX - imageX) * f;
            imageY = mouseY - (mouseY - imageY) * f;
            drawImage(imageX, imageY);
        });
        addEventFilter(MouseEvent.MOUSE_MOVED, event -> {
            MousePosition.setX((event.getX() - imageX) / (ImageScale.getScale() / 100));
            MousePosition.setY((event.getY() - imageY) / (ImageScale.getScale() / 100));
        });
        widthProperty().addListener((_, _, _) -> {
            scaleToImage();
            centerImage();
        });
        heightProperty().addListener((_, _, _) -> {
            scaleToImage();
            centerImage();
        });
    }

    public void drawImage(double x, double y) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        clearCanvas();
        double width = image.getWidth() * ImageScale.getScale() / 100;
        double height = image.getHeight() * ImageScale.getScale() / 100;
        gc.drawImage(image, x, y, width, height);
        imageX = x;
        imageY = y;
    }

    private void clearCanvas() {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void centerImage() {
        ImageScale.setScale(ImageScale.getDefaultEditorScale());
        double canvasCenterX = canvas.getWidth() / 2;
        double canvasCenterY = canvas.getHeight() / 2;
        double imageWidth = image.getWidth() * ImageScale.getScale() / 100;
        double imageHeight = image.getHeight() * ImageScale.getScale() / 100;
        double x = canvasCenterX - imageWidth / 2;
        double y = canvasCenterY - imageHeight / 2;
        drawImage(x, y);
    }

    public void scaleToImage() {
        double widthRatio = image.getWidth() / canvas.getWidth();
        double heightRatio = image.getHeight() / canvas.getHeight();
        double ratio = Math.max(widthRatio, heightRatio);
        ImageScale.setDefaultEditorScale(75 / ratio);
    }

    public void upscale() {
        double oldScale = ImageScale.getScale() / 100;
        ImageScale.upscale();
        Point2D center = getImageCenter(oldScale);
        drawImage(center.getX(), center.getY());
    }

    public void downscale() {
        double oldScale = ImageScale.getScale() / 100;
        ImageScale.downscale();
        Point2D center = getImageCenter(oldScale);
        drawImage(center.getX(), center.getY());
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
        ImageBounds.setWidth(image.getWidth());
        ImageBounds.setHeight(image.getHeight());
    }

    private Point2D getImageCenter(double oldScale) {
        double imageCenterX = imageX + (image.getWidth() * oldScale) / 2;
        double imageCenterY = imageY + (image.getHeight() * oldScale) / 2;
        double newImageWidth = image.getWidth() * ImageScale.getScale() / 100;
        double newImageHeight = image.getHeight() * ImageScale.getScale() / 100;
        double x = imageCenterX - newImageWidth / 2;
        double y = imageCenterY - newImageHeight / 2;
        return new Point2D(x, y);
    }

    public ChangeListener<Number> getChangeListener() {
        return (ObservableValue<? extends Number> _, Number _, Number newValue) -> {
            double oldScale = ImageScale.getScale() / 100;
            ImageScale.setScale(ImageScale.fromPercent((double) newValue));
            Point2D center = getImageCenter(oldScale);
            drawImage(center.getX(), center.getY());
        };
    }
}
