package com.calvinnordstrom.cnpaint;

import com.calvinnordstrom.cnpaint.controller.MainController;
import com.calvinnordstrom.cnpaint.event.KeyListener;
import com.calvinnordstrom.cnpaint.model.MainModel;
import com.calvinnordstrom.cnpaint.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

public class Main extends Application {
    public static final Logger LOGGER = Logger.getLogger(Main.class.getPackageName());
    public static final String VERSION = "0.0";
    public static final String TITLE = "CNPaint " + VERSION;

    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        MainModel model = new MainModel();
        MainView view = new MainView();
        MainController controller = new MainController(model, view);

        Scene scene = new Scene(controller.getView(), 960, 540);
        scene.setOnKeyPressed(keyEvent -> KeyListener.getInstance().keyPressed(keyEvent.getCode()));
        scene.setOnKeyReleased(keyEvent -> KeyListener.getInstance().keyReleased(keyEvent.getCode()));

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