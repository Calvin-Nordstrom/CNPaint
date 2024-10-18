package com.calvinnordstrom.cnpaint.view;

import com.calvinnordstrom.cnpaint.property.ImageBounds;
import com.calvinnordstrom.cnpaint.property.ImageScale;
import com.calvinnordstrom.cnpaint.property.MousePosition;
import com.calvinnordstrom.cnpaint.tool.ToolManager;
import com.calvinnordstrom.cnpaint.util.DragContext;
import com.calvinnordstrom.cnpaint.util.ImageUtils;
import com.calvinnordstrom.cnpaint.util.Resources;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;

public class EditorPane extends StackPane {
    private final StackPane stackPane = new StackPane();
    private final Canvas canvas = new Canvas();
    private final GraphicsContext gc = canvas.getGraphicsContext2D();
    private final ImageBounds imageBounds = new ImageBounds();
    private final ImageScale imageScale = new ImageScale();
    private final MousePosition mousePosition = new MousePosition();
    private final DragContext dragContext = new DragContext();
    private WritableImage image;

    public EditorPane() {
        getStyleClass().add("editor-pane");

        init();
        initEventHandlers();

        setImage(ImageUtils.toWritableImage(Resources.DEFAULT_IMAGE));
    }

    public EditorPane(WritableImage image) {
        getStyleClass().add("editor-pane");

        init();
        initEventHandlers();

        setImage(image);
    }

    private void init() {
        getChildren().add(stackPane);
        stackPane.setMinSize(0, 0);
        canvas.getGraphicsContext2D().setImageSmoothing(false);
        canvas.widthProperty().bind(stackPane.widthProperty());
        canvas.heightProperty().bind(stackPane.heightProperty());
        stackPane.widthProperty().addListener((_, _, _) -> resetImage());
        stackPane.heightProperty().addListener((_, _, _) -> resetImage());
        stackPane.getChildren().addAll(canvas);
    }

    private void initEventHandlers() {
        stackPane.addEventFilter(MouseEvent.MOUSE_PRESSED, this::handleMousePressed);
        stackPane.addEventFilter(MouseEvent.MOUSE_DRAGGED, this::handleMouseDragged);
        stackPane.addEventFilter(MouseEvent.MOUSE_RELEASED, this::handleMouseReleased);
        stackPane.addEventFilter(ScrollEvent.SCROLL, this::handleScroll);
        stackPane.addEventFilter(MouseEvent.MOUSE_MOVED, this::handleMouseMoved);
    }

    private void handleMousePressed(MouseEvent event) {
        if (event.isMiddleButtonDown()) {
            dragContext.x = event.getX();
            dragContext.y = event.getY();
            dragContext.dx = imageBounds.getX();
            dragContext.dy = imageBounds.getY();
        } else {
            ToolManager.getInstance().handleMousePressed(event, image, mousePosition);
            drawImage();
        }
    }

    public void handleMouseDragged(MouseEvent event) {
        if (event.isMiddleButtonDown()) {
            imageBounds.setX(dragContext.dx + event.getX() - dragContext.x);
            imageBounds.setY(dragContext.dy + event.getY() - dragContext.y);
        } else {
            mousePosition.setX((event.getX() - imageBounds.getX()) / (imageScale.getScale() / 100));
            mousePosition.setY((event.getY() - imageBounds.getY()) / (imageScale.getScale() / 100));
            ToolManager.getInstance().handleMouseDragged(event, image, mousePosition);
        }
        drawImage();
    }

    public void handleMouseReleased(MouseEvent event) {
        if (!event.isMiddleButtonDown()) {
            ToolManager.getInstance().handleMouseReleased(event, image, mousePosition);
        }
    }

    public void handleMouseMoved(MouseEvent event) {
        mousePosition.setX((event.getX() - imageBounds.getX()) / (imageScale.getScale() / 100));
        mousePosition.setY((event.getY() - imageBounds.getY()) / (imageScale.getScale() / 100));
    }

    public void handleScroll(ScrollEvent event) {
        if (event.isControlDown()) {
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
            drawImage();
        }
    }

    public void drawImage() {
        drawImage(imageBounds.getX(), imageBounds.getY());
    }

    public void drawImage(double x, double y) {
        double width = image.getWidth() * imageScale.getScale() / 100;
        double height = image.getHeight() * imageScale.getScale() / 100;
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.drawImage(image, x, y, width, height);
        imageBounds.setX(x);
        imageBounds.setY(y);
    }

    public void centerImage() {
        imageScale.setScale(imageScale.getDefaultEditorScale());
        double centerX = canvas.getWidth() / 2;
        double centerY = canvas.getHeight() / 2;
        double width = image.getWidth() * imageScale.getScale() / 100;
        double height = image.getHeight() * imageScale.getScale() / 100;
        double x = centerX - width / 2;
        double y = centerY - height / 2;
        drawImage(x, y);
    }

    public void scaleToImage() {
        double widthRatio = image.getWidth() / canvas.getWidth();
        double heightRatio = image.getHeight() / canvas.getHeight();
        double ratio = Math.max(widthRatio, heightRatio);
        imageScale.setDefaultEditorScale(85 / ratio);
    }

    public void resetImage() {
        scaleToImage();
        centerImage();
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

    private Point2D getImageCenter(double scale) {
        double centerX = imageBounds.getX() + (image.getWidth() * scale) / 2;
        double centerY = imageBounds.getY() + (image.getHeight() * scale) / 2;
        double newWidth = image.getWidth() * imageScale.getScale() / 100;
        double newHeight = image.getHeight() * imageScale.getScale() / 100;
        double x = centerX - newWidth / 2;
        double y = centerY - newHeight / 2;
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

    public MousePosition getMousePosition() {
        return mousePosition;
    }

    public WritableImage getImage() {
        return image;
    }

    public void setImage(WritableImage image) {
        this.image = image;
        imageBounds.setWidth(image.getWidth());
        imageBounds.setHeight(image.getHeight());
        resetImage();
    }
}
