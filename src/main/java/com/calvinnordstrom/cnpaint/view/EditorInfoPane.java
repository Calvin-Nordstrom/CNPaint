package com.calvinnordstrom.cnpaint.view;

import com.calvinnordstrom.cnpaint.property.ImageBounds;
import com.calvinnordstrom.cnpaint.property.ImageScale;
import com.calvinnordstrom.cnpaint.property.MousePosition;
import com.calvinnordstrom.cnpaint.view.node.DefaultButton;
import com.calvinnordstrom.cnpaint.view.node.DefaultLabel;
import com.calvinnordstrom.cnpaint.view.node.Spacer;
import javafx.geometry.Insets;
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

        setStyle("-fx-background-color: rgb(64, 64, 64); -fx-padding: 5px;");
        setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
                BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE,
                CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));

        initImageInfo();
        initImageControls();
        setRight(right);
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

        right.getChildren().addAll(mouseLocation, new Spacer(40, 0), imageDimensions, new Spacer(40, 0));
    }

    private void initImageControls() {
        DefaultButton minimizeButton = new DefaultButton();
        minimizeButton.setFontIcon(FontIcon.of(Evaicons.MINIMIZE_OUTLINE), 20, Color.WHITE);
        minimizeButton.addEventFilter(MouseEvent.MOUSE_CLICKED, _ -> ImageScale.downscale());

        Slider slider = new Slider(ImageScale.MIN_SCALE, ImageScale.MAX_SCALE, ImageScale.getScale());
        ImageScale.scaleProperty().bindBidirectional(slider.valueProperty());

        System.out.println(toPercent(ImageScale.MIN_SCALE, ImageScale.MAX_SCALE, ImageScale.getScale()));

        DefaultButton maximizeButton = new DefaultButton();
        maximizeButton.setFontIcon(FontIcon.of(Evaicons.MAXIMIZE_OUTLINE), 20, Color.WHITE);
        maximizeButton.addEventFilter(MouseEvent.MOUSE_CLICKED, _ -> ImageScale.upscale());

        HBox sliderControls = new HBox(minimizeButton, slider, maximizeButton);

        DefaultButton homeButton = new DefaultButton();
        homeButton.setFontIcon(FontIcon.of(Entypo.HOME), 20, Color.WHITE);

        right.getChildren().addAll(sliderControls, new Spacer(40, 0));
        mainView.addButtonToPane(right, homeButton, "centerImage");
    }

    private double toPercent(double min, double max, double value) {
        double range = max - min;
        return value * 10 / range * 100;
    }

//    private double fromPercent() {
//
//    }
}
