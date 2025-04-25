package com.calvinnordstrom.cnpaint;

import com.calvinnordstrom.cnpaint.controller.MainController;
import com.calvinnordstrom.cnpaint.event.KeyListener;
import com.calvinnordstrom.cnpaint.model.MainModel;
import com.calvinnordstrom.cnpaint.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.logging.Logger;

public class Main extends Application {
    public static final Logger LOGGER = Logger.getLogger(Main.class.getPackageName());
    public static final String VERSION = "0.0";
    public static final String TITLE = "CNPaint " + VERSION;

    @Override
    public void start(Stage stage) {
        MainModel model = new MainModel();
        MainView view = new MainView();
        MainController controller = new MainController(model, view);

        Scene scene = new Scene(controller.getView(), 960, 540);
        scene.setOnKeyPressed(keyEvent -> KeyListener.getInstance().keyPressed(keyEvent.getCode()));
        scene.setOnKeyReleased(keyEvent -> KeyListener.getInstance().keyReleased(keyEvent.getCode()));
        scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("css/main.css")).toExternalForm());

        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    @Override
    public void stop() {
        System.out.println("Prompt for save or exit");
    }

    public static void main(String[] args) {
        launch(args);
    }
}