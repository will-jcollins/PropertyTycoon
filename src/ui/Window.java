package ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import model.Player.Player;
import model.game.Game;

import java.util.ArrayList;

public class Window extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Sizes.computeSizes();

        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player(0, "Will",false));
        players.add(new Player(1,"Max",true));
        players.add(new Player(2,"Ant",true));
        players.add(new Player(3,"Hannah",true));
        players.add(new Player(4,"Tom",true));
        players.add(new Player(5,"Oliver",true));

        // Create monopoly model from options selected in menu
        Game model = new Game(players);
        UIGame root = new UIGame(model);

        // Trigger game logic after UI has loaded
        Platform.runLater(() -> root.start());

        // Scene & Stage setup
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.show();
    }
}
