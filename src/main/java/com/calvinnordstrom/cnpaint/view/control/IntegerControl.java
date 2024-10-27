package com.calvinnordstrom.cnpaint.view.control;

import com.calvinnordstrom.cnpaint.Main;
import com.calvinnordstrom.cnpaint.view.node.DefaultLabel;
import com.calvinnordstrom.cnpaint.view.node.RepeatButton;
import com.calvinnordstrom.cnpaint.view.node.Spacer;
import javafx.beans.property.IntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.evaicons.Evaicons;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.logging.Level;

public class IntegerControl extends HBox {
    private final String text;
    private final IntegerProperty value;
    private final Integer[] elements;

    public IntegerControl(String text, IntegerProperty value, Integer... elements) {
        this.text = text;
        this.value = value;
        this.elements = elements;

        getStyleClass().add("integer-control");

        init();
    }

    private void init() {
        setAlignment(Pos.CENTER);

        DefaultLabel label = new DefaultLabel(text);

        RepeatButton decreaseButton = new RepeatButton();
        decreaseButton.setFontIcon(FontIcon.of(Evaicons.MINIMIZE_OUTLINE), 20, Color.WHITE);
        decreaseButton.setEventHandler(_ -> value.set(value.get() - 1));

        ComboBox<Integer> comboBox = new ComboBox<>();
        comboBox.setMaxWidth(100);
        comboBox.getItems().setAll(elements);
        comboBox.getSelectionModel().selectFirst();
        comboBox.setEditable(true);
        comboBox.getEditor().textProperty().addListener((_, _, newValue) -> {
            if (newValue.matches("\\d*")) {
                comboBox.getEditor().setText(newValue.replaceAll("\\D", ""));
                try {
                    value.set(Integer.parseInt(comboBox.getEditor().getText()));
                } catch (NumberFormatException e) {
                    Main.LOGGER.log(Level.WARNING, "String does not contain a parsable integer.");
                }
            }
        });

        RepeatButton increaseButton = new RepeatButton();
        increaseButton.setFontIcon(FontIcon.of(Evaicons.MAXIMIZE_OUTLINE), 20, Color.WHITE);
        increaseButton.setEventHandler(_ -> value.set(value.get() + 1));

        value.addListener((_, _, newValue) -> {
            comboBox.getEditor().setText(String.valueOf(newValue));
        });

        getChildren().addAll(label, new Spacer(5, 0), decreaseButton, comboBox, increaseButton);
    }
}
