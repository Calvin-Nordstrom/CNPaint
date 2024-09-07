package com.calvinnordstrom.cnpaint.property;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class MousePosition {
    private static MousePosition instance;
    private final DoubleProperty x = new SimpleDoubleProperty(0);
    private final DoubleProperty y = new SimpleDoubleProperty(0);

    private MousePosition() {}

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

    public static MousePosition getInstance() {
        if (instance == null) {
            instance = new MousePosition();
        }
        return instance;
    }
}
