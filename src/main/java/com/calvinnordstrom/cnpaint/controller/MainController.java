package com.calvinnordstrom.cnpaint.controller;

import javafx.event.ActionEvent;

public class MainController {
    public void close(ActionEvent event) {
        System.out.println("Prompt for save or exit");
        System.exit(0);
    }
}