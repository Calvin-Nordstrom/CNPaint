package com.calvinnordstrom.cnpaint.view;

import com.calvinnordstrom.cnpaint.property.ImageBounds;
import com.calvinnordstrom.cnpaint.property.ImageScale;
import com.calvinnordstrom.cnpaint.property.MousePosition;
import com.calvinnordstrom.cnpaint.view.node.DefaultButton;
import com.calvinnordstrom.cnpaint.view.node.DefaultLabel;
import com.calvinnordstrom.cnpaint.view.node.Spacer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.entypo.Entypo;
import org.kordamp.ikonli.evaicons.Evaicons;
import org.kordamp.ikonli.javafx.FontIcon;

public class EditorInfoPane extends BorderPane {
    private final MainView mainView;
    private final HBox right;

    public EditorInfoPane(MainView mainView) {
        this.mainView = mainView;
        right = new HBox();

        init();
        initImageInfo();
        initImageControls();
        setRight(right);
    }

    private void init() {
        setStyle("-fx-background-color: rgb(64, 64, 64); -fx-padding: 3px;");
        setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
                BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE,
                CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));
    }

    private void initImageInfo() {
        DefaultLabel mouseLocation = new DefaultLabel();
        mouseLocation.setFontIcon(FontIcon.of(Entypo.MOUSE_POINTER), 16, Color.WHITE);
        mouseLocation.textProperty().bind(MousePosition.xProperty().asString("%.0f, ")
                .concat(MousePosition.yProperty().asString("%.0f")));
        mouseLocation.setStyle("-fx-text-fill: white;");

        DefaultLabel imageDimensions = new DefaultLabel();
        imageDimensions.setFontIcon(FontIcon.of(Entypo.IMAGE), 16, Color.WHITE);
        imageDimensions.textProperty().bind(ImageBounds.widthProperty().asString("%.0f x ")
                .concat(ImageBounds.heightProperty().asString("%.0f")));
        imageDimensions.setStyle("-fx-text-fill: white;");

        HBox imageInfoHBox = new HBox(mouseLocation, new Spacer(40, 0), imageDimensions, new Spacer(40, 0));
        imageInfoHBox.setAlignment(Pos.CENTER);

        right.getChildren().addAll(imageInfoHBox);
    }

    private void initImageControls() {
        Label scaleLabel = new Label();
        scaleLabel.textProperty().bind(ImageScale.scaleProperty().asString("%.0f").concat("%"));
        scaleLabel.setStyle("-fx-text-fill: white;");

        DefaultButton minimizeButton = new DefaultButton();
        minimizeButton.setFontIcon(FontIcon.of(Evaicons.MINIMIZE_OUTLINE), 20, Color.WHITE);
        minimizeButton.addEventFilter(MouseEvent.MOUSE_CLICKED, _ -> ImageScale.downscale());

        Slider slider = new Slider(0, 100, 50);
        ImageScale.percentProperty().bind(slider.valueProperty());
        ImageScale.scaleProperty().addListener((_, _, newValue) -> {
            slider.setValue(ImageScale.toPercent((double) newValue));
        });

        DefaultButton maximizeButton = new DefaultButton();
        maximizeButton.setFontIcon(FontIcon.of(Evaicons.MAXIMIZE_OUTLINE), 20, Color.WHITE);
        maximizeButton.addEventFilter(MouseEvent.MOUSE_CLICKED, _ -> ImageScale.upscale());

        HBox sliderControls = new HBox(scaleLabel, new Spacer(10, 0), minimizeButton, slider, maximizeButton);
        sliderControls.setAlignment(Pos.CENTER);

        DefaultButton homeButton = new DefaultButton();
        homeButton.setFontIcon(FontIcon.of(Entypo.HOME), 20, Color.WHITE);
        mainView.buttons.put("centerImage", homeButton);

        HBox imageControlsHBox = new HBox(sliderControls, new Spacer(10, 0), homeButton);
        imageControlsHBox.setAlignment(Pos.CENTER);

        right.getChildren().addAll(imageControlsHBox);
    }
}
