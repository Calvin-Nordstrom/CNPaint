package com.calvinnordstrom.cnpaint.view;

import com.calvinnordstrom.cnpaint.util.ServiceLocator;
import com.calvinnordstrom.cnpaint.view.control.ColorControl;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.VBox;

public class ControlsPane extends VBox {
    public ControlsPane() {
        getStyleClass().add("controls-pane");

        init();
    }

    private void init() {
        ColorControl colorControl = new ColorControl();
        ServiceLocator.getInstance().register("color_control", colorControl);

        getChildren().addAll(colorControl);

        ColorPicker colorPicker = new ColorPicker();
        getChildren().add(colorPicker);
    }
}
