package ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Player.AIPlayer;
import model.Player.HumanPlayer;
import model.Player.Player;
import model.board.Board;
import ui.board.UIBoard;
import ui.player.PlayerStats;
import ui.player.UIPlayers;

import java.util.ArrayList;

public class UIGame extends Application {

    @Override
    public void start(Stage primaryStage) {
        ArrayList<Player> tempPlayers = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            tempPlayers.add(new AIPlayer(i));
        }

        UIBoard board = new UIBoard(new Board());
        UIPlayers players = new UIPlayers(tempPlayers, board);

        StackPane gameStack = new StackPane();
        gameStack.getChildren().add(board);
        gameStack.getChildren().add(players);

        BorderPane root = new BorderPane(gameStack);
        root.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            tempPlayers.get(0).changePos(1);
            players.updatePlayers(board);
        });

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
