package com.calvinnordstrom.cnpaint.view.stage;

import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DefaultMenu extends Stage {
    public DefaultMenu() {
        super(StageStyle.UTILITY);
        setAlwaysOnTop(true);
        setResizable(false);
    }
}
