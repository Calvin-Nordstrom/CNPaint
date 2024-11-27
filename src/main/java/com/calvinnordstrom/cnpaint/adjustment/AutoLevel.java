package com.calvinnordstrom.cnpaint.adjustment;

import javafx.concurrent.Task;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import static com.calvinnordstrom.cnpaint.util.ColorUtils.clamp;

public class AutoLevel extends Adjustment {
    private AutoLevel() {}

    public static Task<Void> apply(WritableImage image) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                PixelReader pixelReader = image.getPixelReader();
                PixelWriter pixelWriter = image.getPixelWriter();
                int width = (int) image.getWidth();
                int height = (int) image.getHeight();

                // Step 1: Find the minimum and maximum RGB values
                double minRed = 1.0, maxRed = 0.0;
                double minGreen = 1.0, maxGreen = 0.0;
                double minBlue = 1.0, maxBlue = 0.0;

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        Color color = pixelReader.getColor(x, y);

                        // Update min and max for each color channel
                        minRed = Math.min(minRed, color.getRed());
                        maxRed = Math.max(maxRed, color.getRed());
                        minGreen = Math.min(minGreen, color.getGreen());
                        maxGreen = Math.max(maxGreen, color.getGreen());
                        minBlue = Math.min(minBlue, color.getBlue());
                        maxBlue = Math.max(maxBlue, color.getBlue());
                    }
                    // Update progress
                    updateProgress(y, height);
                }

                // Print min and max values for debugging
                System.out.println("Red: min = " + minRed + ", max = " + maxRed);
                System.out.println("Green: min = " + minGreen + ", max = " + maxGreen);
                System.out.println("Blue: min = " + minBlue + ", max = " + maxBlue);

                // Step 2: Calculate scaling factors for each channel if range is non-zero
                double redScale = maxRed > minRed ? 1.0 / (maxRed - minRed) : 1.0;
                double greenScale = maxGreen > minGreen ? 1.0 / (maxGreen - minGreen) : 1.0;
                double blueScale = maxBlue > minBlue ? 1.0 / (maxBlue - minBlue) : 1.0;

                // Step 3: Apply the auto-level adjustment
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        Color color = pixelReader.getColor(x, y);

                        // Apply scaling for each color channel if applicable
                        double adjustedRed = maxRed > minRed ? (color.getRed() - minRed) * redScale : color.getRed();
                        double adjustedGreen = maxGreen > minGreen ? (color.getGreen() - minGreen) * greenScale : color.getGreen();
                        double adjustedBlue = maxBlue > minBlue ? (color.getBlue() - minBlue) * blueScale : color.getBlue();

                        // Clamp to [0, 1] range and set the pixel
                        Color newColor = Color.color(
                                clamp(adjustedRed),
                                clamp(adjustedGreen),
                                clamp(adjustedBlue)
                        );
                        pixelWriter.setColor(x, y, newColor);
                    }
                    // Update progress
                    updateProgress(y + height, height * 2.0);
                }

                return null;
            }
        };

        startTask(task);

        return task;
    }
}
