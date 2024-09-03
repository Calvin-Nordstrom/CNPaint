package com.calvinnordstrom.cnpaint.view;

import com.calvinnordstrom.cnpaint.property.PositionProperty;
import com.calvinnordstrom.cnpaint.property.BoundsProperty;
import com.calvinnordstrom.cnpaint.view.node.DefaultButton;
import com.calvinnordstrom.cnpaint.view.node.DefaultLabel;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.entypo.Entypo;
import org.kordamp.ikonli.javafx.FontIcon;

public class EditorInfoPane extends BorderPane {
    private final PositionProperty positionProperty;
    private final BoundsProperty boundsProperty;

    public EditorInfoPane(PositionProperty positionProperty, BoundsProperty boundsProperty) {
        this.positionProperty = positionProperty;
        this.boundsProperty = boundsProperty;
        init();
    }

    private void init() {
        setStyle("-fx-background-color: rgb(64, 64, 64); -fx-padding: 5px;");
        setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
                BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE,
                CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));

        DefaultLabel mouseLocation = new DefaultLabel();
        mouseLocation.setFontIcon(FontIcon.of(Entypo.MOUSE_POINTER), 16, Color.WHITE);
        mouseLocation.textProperty().bind(positionProperty.xProperty().asString("%.0f, ")
                .concat(positionProperty.yProperty().asString("%.0f")));
        mouseLocation.setStyle("-fx-text-fill: white;");

        DefaultLabel imageDimensions = new DefaultLabel();
        imageDimensions.setFontIcon(FontIcon.of(Entypo.IMAGE), 16, Color.WHITE);
        imageDimensions.textProperty().bind(boundsProperty.widthProperty().asString("%.0f x ")
                .concat(boundsProperty.heightProperty().asString("%.0f")));
        imageDimensions.setStyle("-fx-text-fill: white;");

        DefaultButton homeButton = new DefaultButton();
        homeButton.setFontIcon(FontIcon.of(Entypo.HOME), 20, Color.WHITE);

        HBox infoHBox = new HBox(40, mouseLocation, imageDimensions, homeButton);
        setRight(infoHBox);
    }
}
