package com.calvinnordstrom.cnpaint.adjustment;

import javafx.concurrent.Task;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import static com.calvinnordstrom.cnpaint.adjustment.Adjustment.PROGRESS_THRESHOLD;
import static com.calvinnordstrom.cnpaint.adjustment.Adjustment.startTask;

public class InvertColors {
    private InvertColors() {}

    /**
     * Inverts the colors of the specified image.
     * <p>
     * This method creates a daemon task that will process each pixel by
     * flipping the bits of the RGB value, and updating the pixel color.
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
}
