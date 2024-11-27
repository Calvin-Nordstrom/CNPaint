package com.calvinnordstrom.cnpaint.view.stage;

import com.calvinnordstrom.cnpaint.view.node.DefaultLabel;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProgressStage extends DefaultStage {
    private final DefaultLabel label;
    private final ProgressBar progressBar = new ProgressBar(0);

    public ProgressStage(Stage owner, String title) {
        this(owner, title, "Initializing...");
    }

    public ProgressStage(Stage owner, String title, String text) {
        super(owner, title);
        label = new DefaultLabel(text);

        init();
    }

    private void init() {
        VBox vBox = new VBox(label, progressBar);
        Scene scene = new Scene(vBox);
        setScene(scene);
    }

    public void setProgress(double progress) {
        progressBar.setProgress(progress);
    }
}
