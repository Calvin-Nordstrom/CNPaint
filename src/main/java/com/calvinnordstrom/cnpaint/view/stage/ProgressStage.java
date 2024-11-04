package com.calvinnordstrom.cnpaint.view.stage;

import com.calvinnordstrom.cnpaint.view.node.DefaultLabel;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ProgressStage extends Stage {
    private final String title;
    private final DefaultLabel label;
    private final ProgressBar progressBar = new ProgressBar(0);

    public ProgressStage(String title) {
        this(title, "Initializing...");
    }

    public ProgressStage(String title, String text) {
        super(StageStyle.UTILITY);
        this.title = title;
        label = new DefaultLabel(text);

        init();
    }

    private void init() {
        setTitle(title);

        VBox vBox = new VBox(label, progressBar);
        Scene scene = new Scene(vBox);
        setScene(scene);

        setAlwaysOnTop(true);
        setResizable(false);
    }

    public void dispose() {
        setScene(null);
        hide();
    }

    public void setProgress(double progress) {
        progressBar.setProgress(progress);
    }
}
