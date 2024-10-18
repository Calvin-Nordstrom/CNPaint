package com.calvinnordstrom.cnpaint.view.control;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ColorControl extends HBox {
    private final ObjectProperty<Color> primaryColor = new SimpleObjectProperty<>(Color.BLACK);
    private final ObjectProperty<Color> secondaryColor = new SimpleObjectProperty<>(Color.WHITE);

    public ColorControl() {
        super(10);

        init();
        initLeft();
        initRight();
    }

    private void init() {

    }

    private void initLeft() {
        VBox left = new VBox(10);

        HBox gradientHBox = new HBox(10);
        double size = PresetColorsPane.getPixelWidth();
        Rectangle gradientRect = new Rectangle(size, size);
        Rectangle hueRect = new Rectangle(24, size);
        gradientHBox.getChildren().addAll(gradientRect, hueRect);
        left.getChildren().add(gradientHBox);

        left.getChildren().add(new PresetColorsPane());

        getChildren().add(left);
    }

    private void initRight() {
        VBox right = new VBox(10);

        Rectangle primaryRect = new Rectangle(100, 30);
        primaryRect.fillProperty().bind(primaryColor);
        primaryRect.widthProperty().bind(right.widthProperty().divide(2));
        Rectangle secondaryRect = new Rectangle(100, 30);
        secondaryRect.fillProperty().bind(secondaryColor);
        secondaryRect.widthProperty().bind(right.widthProperty().divide(2));
        HBox colorHBox = new HBox(primaryRect, secondaryRect);
        right.getChildren().add(colorHBox);

        TabPane controlTabPane = new TabPane();
        Tab rgbTab = new Tab("RGB", new Rectangle(400, 200));
        Tab hsbTab = new Tab("HSB", null);
        Tab labTab = new Tab("Lab", null);
        Tab cmykTab = new Tab("CMYK", null);
        controlTabPane.getTabs().addAll(rgbTab, hsbTab, labTab, cmykTab);
        right.getChildren().add(controlTabPane);

        getChildren().add(right);
    }

    public Color getPrimaryColor() {
        return primaryColor.get();
    }

    public void setPrimaryColor(Color color) {
        primaryColor.set(color);
    }

    public ObjectProperty<Color> primaryColorProperty() {
        return primaryColor;
    }

    public Color getSecondaryColor() {
        return secondaryColor.get();
    }

    public void setSecondaryColor(Color color) {
        secondaryColor.set(color);
    }

    public ObjectProperty<Color> secondaryColorProperty() {
        return secondaryColor;
    }

    private class PresetColorsPane extends GridPane {
        private static final int ROWS = 9;
        private static final int COLUMNS = 13;
        private static final double RECT_SIZE = 15;
        private static final double GAP = 1;
        private final ColorPair[] colors = {
                new ColorPair(Color.rgb(0, 0, 0), Color.rgb(255, 255, 255)),
                new ColorPair(Color.rgb(0, 51, 51), Color.rgb(204, 255, 255)),
                new ColorPair(Color.rgb(0, 26, 128), Color.rgb(204, 230, 255)),
                new ColorPair(Color.rgb(26, 0, 104), Color.rgb(230, 204, 255)),
                new ColorPair(Color.rgb(51, 0, 51), Color.rgb(255, 204, 255)),
                new ColorPair(Color.rgb(77, 0, 26), Color.rgb(255, 204, 230)),
                new ColorPair(Color.rgb(153, 0, 0), Color.rgb(255, 204, 204)),
                new ColorPair(Color.rgb(153, 51, 0), Color.rgb(255, 204, 179)),
                new ColorPair(Color.rgb(153, 77, 0), Color.rgb(255, 230, 204)),
                new ColorPair(Color.rgb(153, 102, 0), Color.rgb(255, 255, 179)),
                new ColorPair(Color.rgb(153, 153, 0), Color.rgb(255, 255, 204)),
                new ColorPair(Color.rgb(102, 102, 0), Color.rgb(230, 230, 204)),
                new ColorPair(Color.rgb(0, 51, 0), Color.rgb(204, 255, 204)),
        };

        private PresetColorsPane() {
            super(GAP, GAP);

            init();
        }

        private void init() {
            for (int col = 0; col < COLUMNS; col++) {
                Color first = colors[col].first;
                Color second = colors[col].second;
                for (int row = 0; row < ROWS; row++) {
                    Rectangle r = new Rectangle(RECT_SIZE, RECT_SIZE);
                    r.getStyleClass().add("preset-color-rect");
                    Color color = first.interpolate(second, (double) row / (ROWS - 1));
                    r.setFill(color);
                    r.setOnMousePressed(event -> handleMousePressed(event, color));
                    r.setOnMouseEntered(_ -> r.toFront());
                    add(r, col, row + 1);
                }
            }
        }

        private void handleMousePressed(MouseEvent event, Color color) {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                primaryColor.set(color);
            } else if (event.getButton().equals(MouseButton.SECONDARY)) {
                secondaryColor.set(color);
            }
        }

        private record ColorPair(Color first, Color second) {}

        public static double getPixelWidth() {
            return COLUMNS * RECT_SIZE + COLUMNS - GAP;
        }

        public static double getPixelHeight() {
            return ROWS * RECT_SIZE + ROWS - GAP;
        }
    }
}
