package com.calvinnordstrom.cnpaint.view.control;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

public class ColorControl2 extends VBox {
    private final ObjectProperty<Color> leftColor = new SimpleObjectProperty<>(Color.BLACK);

    private DoubleProperty hue = new SimpleDoubleProperty(-1);
    private DoubleProperty sat = new SimpleDoubleProperty(-1);
    private DoubleProperty bright = new SimpleDoubleProperty(-1);
    private DoubleProperty alpha = new SimpleDoubleProperty(100) {
        @Override protected void invalidated() {
            setLeftColor(new Color(getLeftColor().getRed(), getLeftColor().getGreen(),
                    getLeftColor().getBlue(), clamp(alpha.get() / 100)));
        }
    };

    // TEMPORARY
    int rectWidth = 200;
    int rectHeight = 200;

    public ColorControl2() {
//        getStyleClass().add("color-control");
        getStyleClass().add("my-custom-color");

        init();
    }

    private void init() {
        HBox gradientHBox = new HBox(10);
        gradientHBox.getStyleClass().add("color-rect-pane");

        final Pane colorRectOpacityContainer = new StackPane();

        Pane rectangle = new StackPane();
        rectangle.setMinSize(rectWidth, rectHeight);

        Pane colorRectHue = new Pane();

        colorRectHue.backgroundProperty().bind(new ObjectBinding<>() {
            {
                bind(hue);
            }

            @Override
            protected Background computeValue() {
                return new Background(new BackgroundFill(
                        Color.hsb(hue.getValue(), 1.0, 1.0),
                        CornerRadii.EMPTY, Insets.EMPTY));
            }
        });

        Pane rectangleOverlayOne = new Pane();
        rectangleOverlayOne.setBackground(new Background(new BackgroundFill(
                new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.rgb(255, 255, 255, 1)),
                        new Stop(1, Color.rgb(255, 255, 255, 0))),
                CornerRadii.EMPTY, Insets.EMPTY)));

        EventHandler<MouseEvent> rectMouseHandler = event -> {
            final double x = event.getX();
            final double y = event.getY();
            sat.set(clamp(x / rectangle.getWidth()) * 100);
            bright.set(100 - (clamp(y / rectangle.getHeight()) * 100));
            updateHSBColor();
        };

        Pane rectangleOverlayTwo = new Pane();
        rectangleOverlayTwo.setBackground(new Background(new BackgroundFill(
                new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.rgb(0, 0, 0, 0)), new Stop(1, Color.rgb(0, 0, 0, 1))),
                CornerRadii.EMPTY, Insets.EMPTY)));
        rectangleOverlayTwo.setOnMouseDragged(rectMouseHandler);
        rectangleOverlayTwo.setOnMousePressed(rectMouseHandler);

        Region colorRectIndicator = new Region();
        colorRectIndicator.setId("color-rect-indicator");
        colorRectIndicator.setManaged(false);
        colorRectIndicator.setMouseTransparent(true);
        colorRectIndicator.setCache(true);
        colorRectIndicator.layoutXProperty().bind(
                sat.divide(100).multiply(rectangle.widthProperty()));
        colorRectIndicator.layoutYProperty().bind(
                Bindings.subtract(1, bright.divide(100)).multiply(rectangle.heightProperty()));

        Pane hueBar = new Pane();
        hueBar.setMinWidth(20);
        hueBar.setMinHeight(200);
        hueBar.setBackground(new Background(new BackgroundFill(
                createHueGradient(), CornerRadii.EMPTY, Insets.EMPTY)));
        EventHandler<MouseEvent> hueBarHandler = event -> {
            hue.set(clamp(event.getY() / rectangle.getWidth()) * 360);
            updateHSBColor();
        };
        hueBar.setOnMousePressed(hueBarHandler);
        hueBar.setOnMouseDragged(hueBarHandler);

        Region hueBarIndicator = new Region();
        hueBarIndicator.setId("color-bar-indicator");
        hueBarIndicator.setMouseTransparent(true);
        hueBarIndicator.setCache(true);
        hueBarIndicator.layoutYProperty().bind(hue.divide(360).multiply(hueBar.heightProperty()));

        colorRectOpacityContainer.opacityProperty().bind(alpha.divide(100));

        hueBar.getChildren().setAll(hueBarIndicator);
        colorRectOpacityContainer.getChildren().addAll(colorRectHue, rectangleOverlayOne, rectangleOverlayTwo);
        rectangle.getChildren().addAll(colorRectOpacityContainer, colorRectIndicator);
        gradientHBox.getChildren().addAll(rectangle, hueBar);

        getChildren().add(gradientHBox);
    }

    public Color getLeftColor() {
        return leftColor.get();
    }

    public void setLeftColor(Color color) {
        leftColor.set(color);
    }

    public ObjectProperty<Color> leftColorProperty() {
        return leftColor;
    }

    private void updateHSBColor() {
        Color newColor = Color.hsb(hue.get(), clamp(sat.get() / 100),
                clamp(bright.get() / 100), clamp(alpha.get() / 100));
        setLeftColor(newColor);
    }

    private static LinearGradient createHueGradient() {
        double offset;
        Stop[] stops = new Stop[255];
        for (int y = 0; y < 255; y++) {
            offset = (1.0 / 255) * y;
            int h = (int)((y / 255.0) * 360);
            stops[y] = new Stop(offset, Color.hsb(h, 1.0, 1.0));
        }
        return new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
    }

    private static double clamp(double value) {
        return value < 0 ? 0 : value > 1 ? 1 : value;
    }
}
