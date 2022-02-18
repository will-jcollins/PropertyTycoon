package ui;

import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.board.Board;

import java.io.IOException;

public class TestWindow extends Application {
    private ParallelTransition pt;

    @Override
    public void start(Stage primaryStage) throws IOException {

        UIBoard root = new UIBoard(new Board());
        root.setOnMouseClicked(event -> {
            System.out.println("a");
            pt.play();
        });

        RotateTransition rt = new RotateTransition(Duration.millis(1500), root);
        rt.setByAngle(90);
        rt.setCycleCount(1);

        ScaleTransition st = new ScaleTransition(Duration.millis(750), root);
        st.setByX(-0.4);
        st.setByY(-0.4);
        st.setCycleCount(2);
        st.setAutoReverse(true);

        pt = new ParallelTransition(rt, st);

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
