package com.calvinnordstrom.cnpaint.view;

import com.calvinnordstrom.cnpaint.view.node.DefaultLabel;
import com.calvinnordstrom.cnpaint.view.node.Spacer;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ControlsPane extends VBox {
    public ControlsPane() {
        getStyleClass().add("controls-pane");

        init();
    }

    private void init() {
        DefaultLabel left = new DefaultLabel("Left:");
        ColorPicker leftPicker = new ColorPicker(Color.BLACK);
        DefaultLabel right = new DefaultLabel("Right:");
        ColorPicker rightPicker = new ColorPicker(Color.WHITE);
        getChildren().addAll(left, leftPicker, new Spacer(0, 10), right, rightPicker);
    }
}
