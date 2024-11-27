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
 * A utility class that provides methods for {@link Image} objects, including
 * converters and pixel manipulation.
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
     * Returns the {@link Color} based on the specified {@link MouseEvent}.
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
        ColorControl cc = (ColorControl) ServiceLocator.getInstance().getNode("color-control");
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            return cc.getPrimaryColor();
        } else if (event.getButton().equals(MouseButton.SECONDARY)) {
            return cc.getSecondaryColor();
        }
        return cc.getPrimaryColor();
    }

    /**
     * Returns the scaled duration dependent on the specified brush size.
     * <p>
     * This method is used to slow down the draw rate for tools to improve
     * performance when using large brush sizes.
     * <p>
     * The scaling function is brush size to the power of 1.5, plus 50. If the
     * specified brush size is less than 0, this method will return 50.
     *
     * @param brushSize the brush size
     * @return the scaled duration
     */
    public static double getDuration(int brushSize) {
//        return Math.pow(Math.max(brushSize, 0), 1.5) + 50;
        return brushSize + 50;
    }

    /**
     * Checks if the specified coordinates are within the bounds of the
     * specified width and height.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param width the width
     * @param height the height
     * @return true if x and y are greater than or equal to zero and less than
     * width and height, respectively; false otherwise
     */
    public static boolean isValid(double x, double y, double width, double height) {
        return (x >= 0 && x < width && y >= 0 && y < height);
    }

    public static void drawLine(WritableImage image, double x0, double y0,
                                double x1, double y1, int radius, Color color) {
        double dx = x1 - x0;
        double dy = y1 - y0;
        double distance = Math.max(Math.abs(dx), Math.abs(dy));

        for (int i = 0; i <= distance; i++) {
            double t = i / distance;
            int x = (int) Math.round(x0 + t * dx);
            int y = (int) Math.round(y0 + t * dy);
            drawFilledCircle(image, x, y, radius, color);
        }
    }

    public static void drawFilledCircle(WritableImage image,
                                        int centerX, int centerY,
                                        int radius, Color color) {
        int startX = centerX - radius;
        int endX = centerX + radius;
        int startY = centerY - radius;
        int endY = centerY + radius;
        int radius2 = radius * radius;
        double width = image.getWidth();
        double height = image.getHeight();
        PixelWriter pixelWriter = image.getPixelWriter();

        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                int dx = x - centerX;
                int dy = y - centerY;
                if (dx * dx + dy * dy <= radius2 && isValid(x, y, width, height)) {
                    pixelWriter.setColor(x, y, color);
                }
            }
        }
    }

    public static void drawSpline(PixelWriter pixelWriter, Point2D P0,
                                  Point2D P1, Point2D P2, Point2D P3,
                                  double alpha, int steps) {
        CatmullRomSpline spline = new CatmullRomSpline();
        List<Point2D> splinePoints = spline.compute(P0, P1, P2, P3, alpha, steps);

        for (Point2D point : splinePoints) {
            pixelWriter.setColor((int) point.getX(),
                    (int) point.getY(), Color.BLACK);
        }
    }
}
