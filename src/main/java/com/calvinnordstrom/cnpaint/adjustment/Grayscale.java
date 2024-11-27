package com.calvinnordstrom.cnpaint.adjustment;

import javafx.concurrent.Task;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class Grayscale extends Adjustment {
    private Grayscale() {}

    /**
     * Applies a grayscale filter to the specified image.
     * <p>
     * This method creates a daemon task that will process each pixel by
     * calculating the average of the RGB values to obtain the grayscale value,
     * and updating the pixel color.
     * <p>
     * The task progress is updated throughout the process. This can be
     * retrieved through the returned {@link Task <Void>} object.
     *
     * @param image the image to apply the adjustment on
     * @return the {@link Task<Void>} that performs the adjustment
     */
    public static Task<Void> apply(WritableImage image) {
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
}
