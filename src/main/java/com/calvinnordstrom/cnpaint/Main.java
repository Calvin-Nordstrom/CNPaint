package com.calvinnordstrom.cnpaint;

import com.calvinnordstrom.cnpaint.controller.MainController;
import com.calvinnordstrom.cnpaint.event.KeyListener;
import com.calvinnordstrom.cnpaint.model.MainModel;
import com.calvinnordstrom.cnpaint.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.stream.Stream;

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

        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        printLineCount();
    }

    @Override
    public void stop() {
        System.out.println("Prompt for save or exit");
    }

    private static void printLineCount() {
        Path rootDir = Paths.get("C:\\Users\\calno\\Desktop\\IntelliJ Projects\\CNPaint\\src\\main\\java\\com\\calvinnordstrom\\cnpaint");
        try (Stream<Path> paths = Files.walk(rootDir)) {
            long totalLines = paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .mapToLong(Main::countLinesInFile)
                    .sum();
            System.out.println("Total lines in all .java files: " + totalLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long countLinesInFile(Path path) {
        long lineCount = 0;
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            while (reader.readLine() != null) {
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineCount;
    }

    public static void main(String[] args) {
        launch(args);
    }
}