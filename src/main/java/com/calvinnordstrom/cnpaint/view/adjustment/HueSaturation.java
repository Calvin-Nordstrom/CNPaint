package com.calvinnordstrom.cnpaint.view.adjustment;

import com.calvinnordstrom.cnpaint.view.node.DefaultButton;
import com.calvinnordstrom.cnpaint.view.node.DefaultLabel;
import com.calvinnordstrom.cnpaint.view.node.Spacer;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

public class HueSaturation extends VBox {
    public HueSaturation() {
        super(10);

        getStyleClass().add("hue-saturation");

        init();
    }

    private void init() {
        setPadding(new Insets(10, 10, 10, 10));

        SliderControl hue = new SliderControl("Hue", -180, 180, 0, "°");
        SliderControl saturation = new SliderControl("Saturation", 0, 200, 100, "%");
        SliderControl brightness = new SliderControl("Brightness", -100, 100, 0, "%");

        DefaultButton okButton = new DefaultButton("OK");
        okButton.setPrefWidth(80);
        DefaultButton cancelButton = new DefaultButton("Cancel");
        cancelButton.setPrefWidth(80);
        HBox buttons = new HBox(10, okButton, cancelButton);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        getChildren().addAll(hue, saturation, brightness, new Spacer(0, 5), buttons);
    }

    private static class SliderControl extends VBox {
        private final int min;
        private final int max;
        private final DefaultLabel label;
        private final Slider slider;
        private final TextField textField;
        private final DefaultLabel symbolLabel;
        private ChangeListener<Number> sliderListener;
        private ChangeListener<String> textFieldListener;
        private double textFieldValue;

        public SliderControl(String text, int min, int max, int value, @NotNull String symbol) {
            this.min = min;
            this.max = max;
            label = new DefaultLabel(text);
            slider = new Slider(min, max, value);
            textField = new TextField(String.valueOf((int) slider.getValue()));
            symbolLabel = new DefaultLabel(symbol);

            getStyleClass().add("slider-control");

            init();
        }

        private void init() {
            HBox labelPane = new HBox(label);
            labelPane.setMinWidth(64);
            labelPane.setAlignment(Pos.CENTER_LEFT);

            slider.setPrefWidth(192);
            HBox sliderPane = new HBox(slider);
            sliderPane.setAlignment(Pos.CENTER);

            textField.setPrefWidth(48);

            HBox symbolPane = new HBox(symbolLabel);
            symbolPane.setMinWidth(16);
            symbolPane.setAlignment(Pos.CENTER);

            HBox hbox = new HBox(new HBox(sliderPane, textField, symbolPane));
            hbox.setAlignment(Pos.CENTER);

            getChildren().addAll(labelPane, hbox);
        }
    }
}
