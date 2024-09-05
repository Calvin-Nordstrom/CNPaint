package com.calvinnordstrom.cnpaint.view.node;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class Spacer extends Region {
    public Spacer(double width, double height) {
        setMinWidth(width);
        setMinHeight(height);
        HBox.setHgrow(this, Priority.ALWAYS);
        VBox.setVgrow(this, Priority.ALWAYS);
    }
}
