package com.calvinnordstrom.cnpaint.view;

import com.calvinnordstrom.cnpaint.property.ImageBounds;
import com.calvinnordstrom.cnpaint.property.ImageScale;
import com.calvinnordstrom.cnpaint.property.MousePosition;
import com.calvinnordstrom.cnpaint.tool.ToolManager;
import com.calvinnordstrom.cnpaint.view.node.DefaultButton;
import com.calvinnordstrom.cnpaint.view.node.DefaultLabel;
import com.calvinnordstrom.cnpaint.view.node.RepeatButton;
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
    private final HBox right = new HBox();

    public EditorControlsPane(EditorPane editor) {
        this.editor = editor;

        getStyleClass().add("editor-controls-pane");

        init();
        initImageInfo();
        initImageControls();
    }

    private void init() {
        ToolManager tm = ToolManager.getInstance();
        DefaultLabel toolDescription = new DefaultLabel(tm.getTool().getDescription());
        tm.toolProperty().addListener((_, _, newValue) -> {
            toolDescription.setText(newValue.getDescription());
        });
        setLeft(new HBox(new Spacer(10, 0), toolDescription));

        setRight(right);
    }

    private void initImageInfo() {
        MousePosition mousePosition = editor.getMousePosition();
        ImageBounds imageBounds = editor.getImageBounds();

        DefaultLabel mouseLocation = new DefaultLabel();
        mouseLocation.setFontIcon(FontIcon.of(Entypo.MOUSE_POINTER), 16, Color.WHITE);
        mouseLocation.textProperty().bind(mousePosition.xIntProperty().asString()
                .concat(", ").concat(mousePosition.yIntProperty().asString()));

        DefaultLabel imageDimensions = new DefaultLabel();
        imageDimensions.setFontIcon(FontIcon.of(Entypo.IMAGE), 16, Color.WHITE);
        imageDimensions.textProperty().bind(imageBounds.widthProperty().asString("%.0f x ")
                .concat(imageBounds.heightProperty().asString("%.0f")));

        HBox imageInfoHBox = new HBox(mouseLocation, new Spacer(40, 0),
                imageDimensions, new Spacer(40, 0));
        imageInfoHBox.setAlignment(Pos.CENTER);

        right.getChildren().addAll(imageInfoHBox);
    }

    private void initImageControls() {
        ImageScale imageScale = editor.getImageScale();

        DefaultLabel scaleLabel = new DefaultLabel();
        scaleLabel.textProperty().bind(imageScale.scaleProperty()
                .asString("%.0f").concat("%"));

        RepeatButton downscaleButton = new RepeatButton();
        downscaleButton.setFontIcon(FontIcon.of(Evaicons.MINIMIZE_OUTLINE), 20, Color.WHITE);
        downscaleButton.setEventHandler(_ -> editor.downscale());

        Slider slider = new Slider(0, 100, ImageScale.toPercent(imageScale.getScale()));
        imageScale.percentProperty().bind(slider.valueProperty());
        imageScale.scaleProperty().addListener((_, _, newValue) -> {
            slider.setValue(ImageScale.toPercent((double) newValue));
        });
        slider.valueProperty().addListener(editor.getChangeListener());

        RepeatButton upscaleButton = new RepeatButton();
        upscaleButton.setFontIcon(FontIcon.of(Evaicons.MAXIMIZE_OUTLINE), 20, Color.WHITE);
        upscaleButton.setEventHandler(_ -> editor.upscale());

        HBox sliderControls = new HBox(scaleLabel, new Spacer(10, 0),
                downscaleButton, slider, upscaleButton);
        sliderControls.setAlignment(Pos.CENTER);

        DefaultButton homeButton = new DefaultButton();
        homeButton.setFontIcon(FontIcon.of(Entypo.HOME), 20, Color.WHITE);
        homeButton.setOnMouseClicked(_ -> editor.centerImage());

        HBox imageControlsHBox = new HBox(sliderControls, new Spacer(10, 0),
                homeButton, new Spacer(10, 0));
        imageControlsHBox.setAlignment(Pos.CENTER);

        right.getChildren().addAll(imageControlsHBox);
    }
}