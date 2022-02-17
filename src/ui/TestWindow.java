package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.board.Board;

import java.io.IOException;

public class TestWindow extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {

        UIBoard root = new UIBoard(new Board());



        // Scene & Stage setup
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
