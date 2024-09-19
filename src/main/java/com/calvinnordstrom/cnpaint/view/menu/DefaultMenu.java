package com.calvinnordstrom.cnpaint.view.menu;

import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DefaultMenu extends Stage {
    public DefaultMenu() {
        super(StageStyle.UTILITY);
        setAlwaysOnTop(true);
        setResizable(false);
    }
}
