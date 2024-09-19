package com.calvinnordstrom.cnpaint.view;

import com.calvinnordstrom.cnpaint.property.ImageScale;
import com.calvinnordstrom.cnpaint.property.MousePosition;
import com.calvinnordstrom.cnpaint.view.node.DefaultButton;
import com.calvinnordstrom.cnpaint.view.node.DefaultLabel;
import com.calvinnordstrom.cnpaint.view.node.Spacer;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.entypo.Entypo;
import org.kordamp.ikonli.evaicons.Evaicons;
import org.kordamp.ikonli.javafx.FontIcon;

public class EditorControlsPane extends BorderPane {
    private final EditorPane editor;
    private final HBox right;

    public EditorControlsPane(EditorPane editor) {
        this.editor = editor;
        right = new HBox();

        getStyleClass().add("editor-controls-pane");

        init();
        initImageInfo();
        initImageControls();
    }

    private void init() {
        setRight(right);
    }

    private void initImageInfo() {
        DefaultLabel mouseLocation = new DefaultLabel();
        mouseLocation.setFontIcon(FontIcon.of(Entypo.MOUSE_POINTER), 16, Color.WHITE);
        mouseLocation.textProperty().bind(MousePosition.xIntProperty().asString()
                .concat(", ").concat(MousePosition.yIntProperty().asString()));

        DefaultLabel imageDimensions = new DefaultLabel();
        imageDimensions.setFontIcon(FontIcon.of(Entypo.IMAGE), 16, Color.WHITE);
        imageDimensions.textProperty().bind(editor.getImageBounds().widthProperty().asString("%.0f x ")
                .concat(editor.getImageBounds().heightProperty().asString("%.0f")));

        HBox imageInfoHBox = new HBox(mouseLocation, new Spacer(40, 0), imageDimensions, new Spacer(40, 0));
        imageInfoHBox.setAlignment(Pos.CENTER);

        right.getChildren().addAll(imageInfoHBox);
    }

    private void initImageControls() {
        DefaultLabel scaleLabel = new DefaultLabel();
        scaleLabel.textProperty().bind(editor.getImageScale().scaleProperty().asString("%.0f").concat("%"));

        DefaultButton downscaleButton = new DefaultButton();
        downscaleButton.setFontIcon(FontIcon.of(Evaicons.MINIMIZE_OUTLINE), 20, Color.WHITE);
        downscaleButton.setOnMouseClicked(_ -> editor.downscale());

        Slider slider = new Slider(0, 100, ImageScale.toPercent(editor.getImageScale().getScale()));
        editor.getImageScale().percentProperty().bind(slider.valueProperty());
        editor.getImageScale().scaleProperty().addListener((_, _, newValue) -> {
            slider.setValue(ImageScale.toPercent((double) newValue));
        });
        slider.valueProperty().addListener(editor.getChangeListener());

        DefaultButton upscaleButton = new DefaultButton();
        upscaleButton.setFontIcon(FontIcon.of(Evaicons.MAXIMIZE_OUTLINE), 20, Color.WHITE);
        upscaleButton.setOnMouseClicked(_ -> editor.upscale());

        HBox sliderControls = new HBox(scaleLabel, new Spacer(10, 0), downscaleButton, slider, upscaleButton);
        sliderControls.setAlignment(Pos.CENTER);

        DefaultButton homeButton = new DefaultButton();
        homeButton.setFontIcon(FontIcon.of(Entypo.HOME), 20, Color.WHITE);
        homeButton.setOnMouseClicked(_ -> editor.centerImage());

        HBox imageControlsHBox = new HBox(sliderControls, new Spacer(10, 0), homeButton, new Spacer(10, 0));
        imageControlsHBox.setAlignment(Pos.CENTER);

        right.getChildren().addAll(imageControlsHBox);
    }
}