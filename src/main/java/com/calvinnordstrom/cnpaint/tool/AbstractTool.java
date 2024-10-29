package com.calvinnordstrom.cnpaint.tool;

import com.calvinnordstrom.cnpaint.view.EditorPane;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public abstract class AbstractTool {
    protected Timeline timeline;
    protected EditorPane editorPane;

    public AbstractTool() {
        timeline = new Timeline(new KeyFrame(Duration.millis(50), _ -> {
            if (editorPane != null) {
                editorPane.drawImage();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
    }
}
