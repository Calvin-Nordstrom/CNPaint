package com.calvinnordstrom.cnpaint.view.stage;

import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DefaultStage extends Stage {
    private final Stage owner;
    private final String title;

    public DefaultStage(Stage owner) {
        this(owner, null);
    }

    public DefaultStage(Stage owner, String title) {
        super(StageStyle.UTILITY);
        this.owner = owner;
        this.title = title;

        init();
    }

    private void init() {
        initOwner(owner);
        setTitle(title);
        setAlwaysOnTop(true);
        setResizable(false);
    }

    public void dispose() {
        setScene(null);
        hide();
    }
}
