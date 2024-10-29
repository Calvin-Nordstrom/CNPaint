package com.calvinnordstrom.cnpaint.util;

import com.calvinnordstrom.cnpaint.view.control.ColorControl;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.List;

/**
 * A utility class that provides methods for {@link Image} objects. This
 * includes pixel manipulation and converters used by the editor.
 *
 * @author Calvin Nordstrom
 */
public class ImageUtils {
    private ImageUtils() {}

    /**
     * Converts an object of {@link Image} to its subclass
     * {@link WritableImage}.
     * <p>
     * The {@code width} and {@code height} of the image
     * are retained during this conversion.
     *
     * @param image the {@link Image} to convert
     * @return the {@link WritableImage}
     */
    public static WritableImage toWritableImage(Image image) {
        return new WritableImage(image.getPixelReader(),
                (int) image.getWidth(), (int) image.getHeight());
    }

    /**
     * Retrieves the {@link Color} based on the specified {@link MouseEvent}.
     * <p>
     * When the button of the {@link MouseEvent} is equal to
     * {@code MouseButton.PRIMARY}, the primary color determined by the
     * {@link ColorControl} will be returned.
     * <p>
     * When the button of the {@link MouseEvent} is equal to
     * {@code MouseButton.SECONDARY}, the secondary color determined by the
     * {@link ColorControl} will be returned.
     *
     * @param event the {@link MouseEvent} to evaluate
     * @return the primary {@link Color} if the event's button is primary,
     * or the secondary {@link Color} if the event's button is secondary.
     */
    public static Color getColor(MouseEvent event) {
        ColorControl colorControl = (ColorControl) ServiceLocator.getInstance().getNode("color-control");
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            return colorControl.getPrimaryColor();
        } else if (event.getButton().equals(MouseButton.SECONDARY)) {
            return colorControl.getSecondaryColor();
        }
        return colorControl.getPrimaryColor();
    }

    public static void drawLine(PixelWriter pixelWriter, double x0, double y0, double x1, double y1, Color color) {
        double dx = x1 - x0;
        double dy = y1 - y0;
        double distance = Math.max(Math.abs(dx), Math.abs(dy));

        for (int i = 0; i <= distance; i++) {
            double t = i / distance;
            int x = (int) Math.round(x0 + t * dx);
            int y = (int) Math.round(y0 + t * dy);
            pixelWriter.setColor(x, y, color);
        }
    }

    public static void drawSpline(PixelWriter pixelWriter, Point2D P0, Point2D P1, Point2D P2, Point2D P3, double alpha, int steps) {
        CatmullRomSpline spline = new CatmullRomSpline();
        List<Point2D> splinePoints = spline.computeSpline(P0, P1, P2, P3, alpha, steps);

        for (Point2D point : splinePoints) {
            pixelWriter.setColor((int) point.getX(), (int) point.getY(), Color.BLACK);
        }
    }
}
