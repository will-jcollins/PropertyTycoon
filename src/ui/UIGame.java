package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.Player.HumanPlayer;
import model.Player.Player;
import model.board.Board;
import ui.board.UIBoard;
import ui.player.PlayerStats;

import java.io.IOException;

public class UIGame extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        UIBoard board = new UIBoard(new Board());

        BorderPane root = new BorderPane(board);

        VBox leftStats = new VBox();
        leftStats.getChildren().add(new PlayerStats(new HumanPlayer(0, "Will")));
        leftStats.getChildren().add(new PlayerStats(new HumanPlayer(1, "Ant")));
        leftStats.getChildren().add(new PlayerStats(new HumanPlayer(2, "Hannah")));
        leftStats.getChildren().add(new PlayerStats(new HumanPlayer(3, "Tom")));
        leftStats.getChildren().add(new PlayerStats(new HumanPlayer(4, "Max")));
        leftStats.getChildren().add(new PlayerStats(new HumanPlayer(5, "Oliver")));

        leftStats.setPadding(new Insets(5, 5, 5, 5));
        leftStats.setSpacing(10);
        root.setLeft(leftStats);

        // Scene & Stage setup
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
