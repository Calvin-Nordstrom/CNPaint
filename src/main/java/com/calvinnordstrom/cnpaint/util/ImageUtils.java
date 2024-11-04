package com.calvinnordstrom.cnpaint.util;

import com.calvinnordstrom.cnpaint.view.control.ColorControl;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.List;

import static com.calvinnordstrom.cnpaint.util.ColorUtils.clamp;

/**
 * A utility class that provides methods for {@link Image} objects, including
 * converters and pixel manipulation.
 *
 * @author Calvin Nordstrom
 */
public class ImageUtils {
    private static final int PROGRESS_THRESHOLD = 500;

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

    /* ***************************************************
     *                Image Adjustments                  *
     *****************************************************/

    /**
     * Applies a grayscale filter to the specified image.
     * <p>
     * This method creates a daemon task that will process each pixel by
     * calculating the average of the RGB values to obtain the grayscale value,
     * and updating the pixel color.
     * <p>
     * The task progress is updated throughout the process. This can be
     * retrieved through the returned {@link Task<Void>} object.
     *
     * @param image the image to apply the adjustment on
     * @return the {@link Task<Void>} that performs the adjustment
     */
    public static Task<Void> grayscale(WritableImage image) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                PixelReader reader = image.getPixelReader();
                PixelWriter writer = image.getPixelWriter();
                int width = (int) image.getWidth();
                int height = (int) image.getHeight();

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        int pixel = reader.getArgb(x, y);
                        int a = (pixel >> 24) & 0xFF;
                        int r = (pixel >> 16) & 0xFF;
                        int g = (pixel >> 8) & 0xFF;
                        int b = pixel & 0xFF;
                        int gray = (int) ((r + g + b) / 3.0);
                        int grayPixel = (a << 24) | (gray << 16) | (gray << 8) | gray;
                        writer.setArgb(x, y, grayPixel);
                    }
                    if (y % PROGRESS_THRESHOLD == 0) {
                        updateProgress(y + 1, height);
                    }
                }
                return null;
            }
        };

        startTask(task);

        return task;
    }

    public static Task<Void> autoLevel(WritableImage image) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                PixelReader reader = image.getPixelReader();
                PixelWriter writer = image.getPixelWriter();
                int width = (int) image.getWidth();
                int height = (int) image.getHeight();
                double minRed = 1.0, maxRed = 0.0;
                double minGreen = 1.0, maxGreen = 0.0;
                double minBlue = 1.0, maxBlue = 0.0;
                Color[][] newColors = new Color[width][height];

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        Color color = reader.getColor(x, y);
                        minRed = Math.min(minRed, color.getRed());
                        maxRed = Math.max(maxRed, color.getRed());
                        minGreen = Math.min(minGreen, color.getGreen());
                        maxGreen = Math.max(maxGreen, color.getGreen());
                        minBlue = Math.min(minBlue, color.getBlue());
                        maxBlue = Math.max(maxBlue, color.getBlue());
                    }
                    if (y % PROGRESS_THRESHOLD == 0) {
                        updateProgress(y, height - 1);
                    }
                }

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        Color color = reader.getColor(x, y);
//                        double newRed = (color.getRed() - minRed) / (maxRed - minRed);
//                        double newGreen = (color.getGreen() - minGreen) / (maxGreen - minGreen);
//                        double newBlue = (color.getBlue() - minBlue) / (maxBlue - minBlue);
                        double newRed = (maxRed - minRed) == 0 ? color.getRed() : (color.getRed() - minRed) / (maxRed - minRed);
                        double newGreen = (maxGreen - minGreen) == 0 ? color.getGreen() : (color.getGreen() - minGreen) / (maxGreen - minGreen);
                        double newBlue = (maxBlue - minBlue) == 0 ? color.getBlue() : (color.getBlue() - minBlue) / (maxBlue - minBlue);
                        newColors[x][y] = new Color(clamp(newRed), clamp(newGreen), clamp(newBlue), color.getOpacity());
//                        writer.setColor(x, y, new Color(clamp(newRed), clamp(newGreen), clamp(newBlue), color.getOpacity()));
                    }
                    if (y % PROGRESS_THRESHOLD == 0) {
                        updateProgress(y + height, 2.0 * height - 1);
                    }
                }

                Platform.runLater(() -> {
                    for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++) {
                            writer.setColor(x, y, newColors[x][y]);
                        }
                    }
                });

                return null;
            }
        };

        startTask(task);

        return task;
    }

    /**
     * Inverts the colors of the specified image.
     * <p>
     * This method creates a daemon task that will process each pixel by
     * flipping the bits of the RGB value, and updating the pixel color.
     * <p>
     * The task progress is updated throughout the process. This can be
     * retrieved through the returned {@link Task<Void>} object.
     *
     * @param image the image to apply the adjustment on
     * @return the {@link Task<Void>} that performs the adjustment
     */
    public static Task<Void> invertColors(WritableImage image) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                PixelReader reader = image.getPixelReader();
                PixelWriter writer = image.getPixelWriter();
                int width = (int) image.getWidth();
                int height = (int) image.getHeight();

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        int pixel = reader.getArgb(x, y);
                        int invertedPixel = (pixel & 0xFF000000) | (~pixel & 0x00FFFFFF);
                        writer.setArgb(x, y, invertedPixel);
                    }
                    if (y % PROGRESS_THRESHOLD == 0) {
                        updateProgress(y, height);
                    }
                }

                return null;
            }
        };

        startTask(task);

        return task;
    }

    /**
     * Inverts the alpha component of the specified image.
     * <p>
     * This method creates a daemon task that will process each pixel by
     * flipping the bits of the alpha value, and updating the pixel color.
     * <p>
     * The task progress is updated throughout the process. This can be
     * retrieved through the returned {@link Task<Void>} object.
     *
     * @param image the image to apply the adjustment on
     * @return the {@link Task<Void>} that performs the adjustment
     */
    public static Task<Void> invertAlpha(WritableImage image) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                PixelReader reader = image.getPixelReader();
                PixelWriter writer = image.getPixelWriter();
                int width = (int) image.getWidth();
                int height = (int) image.getHeight();

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        int pixel = reader.getArgb(x, y);
                        int a = (pixel >> 24) & 0xFF;
                        int invertedA = 255 - a;
                        int invertedPixel = (invertedA << 24) | (pixel & 0x00FFFFFF);
                        writer.setArgb(x, y, invertedPixel);
                    }
                    if (y % PROGRESS_THRESHOLD == 0) {
                        updateProgress(y, height);
                    }
                }

                return null;
            }
        };

        startTask(task);

        return task;
    }

    private static void startTask(Task<Void> task) {
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}
