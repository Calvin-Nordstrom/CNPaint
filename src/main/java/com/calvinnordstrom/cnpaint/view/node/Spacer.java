package com.calvinnordstrom.cnpaint.view.node;

import javafx.scene.layout.Region;

public class Spacer extends Region {
    public Spacer(double width, double height) {
        setMinWidth(width);
        setMinHeight(height);
    }
}
