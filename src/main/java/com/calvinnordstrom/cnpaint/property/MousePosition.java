package com.calvinnordstrom.cnpaint.property;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class MousePosition {
    private static MousePosition instance;
    private final DoubleProperty x = new SimpleDoubleProperty(0);
    private final DoubleProperty y = new SimpleDoubleProperty(0);
    private final IntegerProperty intX = new SimpleIntegerProperty(0);
    private final IntegerProperty intY = new SimpleIntegerProperty(0);

    private MousePosition() {
        x.addListener((_, _, _) -> intX.set(round(x.get())));
        y.addListener((_, _, _) -> intY.set(round(y.get())));
    }

    public static double getX() {
        return getInstance().x.get();
    }

    public static void setX(double x) {
        getInstance().x.set(x);
    }

    public static DoubleProperty xProperty() {
        return getInstance().x;
    }

    public static double getY() {
        return getInstance().y.get();
    }

    public static void setY(double y) {
        getInstance().y.set(y);
    }

    public static DoubleProperty yProperty() {
        return getInstance().y;
    }

    public static IntegerProperty xIntProperty() {
        return getInstance().intX;
    }

    public static IntegerProperty yIntProperty() {
        return getInstance().intY;
    }

    public static MousePosition getInstance() {
        if (instance == null) {
            instance = new MousePosition();
        }
        return instance;
    }

    private static int round(double value) {
        return value < 0 ? (int) Math.floor(value) : (int) value;
    }
}
