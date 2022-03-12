package ui;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Player.AIPlayer;
import model.Player.Player;
import model.board.Board;
import model.board.PropertyTile;
import model.game.Dice;
import model.game.Game;
import ui.board.UIBoard;
import ui.menu.PropertyMenu;
import ui.menu.dice.DiceMenu;
import ui.menu.dice.UIDie;
import ui.player.UIPlayers;

import java.io.File;
import java.util.ArrayList;

public class UIGame extends Application {

    private static final int MENU_OFFSET = 50;

    private boolean waiting = false;

    private StackPane gameStack;
    private UIPlayers players;
    private UIBoard board;
    private Game model;

    @Override
    public void start(Stage primaryStage) {

        model = new Game(2,0);

        board = new UIBoard(new Board());
        players = new UIPlayers(model.getPlayers(), board);

        gameStack = new StackPane();
        gameStack.getChildren().add(board);
        gameStack.getChildren().add(players);

        BorderPane root = new BorderPane(gameStack);

        // Sound setup
        File musicPath = new File("assets/audio/back.mp3");
        Media backMusic = new Media(musicPath.toURI().toString());
        MediaPlayer player = new MediaPlayer(backMusic);
        player.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                player.seek(Duration.ZERO);
                player.play();
            }
        });

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                player.play();
                startNextIteration();
            }
        });

        // Scene & Stage setup
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startNextIteration() {
        switch (model.iterateGame()) {
            case SHOW_DICE_MENU:
                createDicePopup(model.getDice());
                break;
            case SHOW_GAME_OVER:
                // TODO:: create game over screen
                System.out.println("game over");
                break;
            default:
                throw new IllegalStateException("Not enumerated");
        }
    }

    private void createDicePopup(Dice dice) {
        System.out.println("created dice popup");
        DiceMenu menu = new DiceMenu(dice);
        menu.setOpacity(0);
        gameStack.getChildren().add(menu);
        menu.setTranslateY(menu.getTranslateY() + MENU_OFFSET);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(250),menu);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(250),menu);
        translateTransition.setByY(-MENU_OFFSET);

        ParallelTransition transitions = new ParallelTransition(menu,fadeTransition,translateTransition);
        transitions.play();

        FadeTransition exitFadeTransition = new FadeTransition(Duration.millis(250),menu);
        exitFadeTransition.setFromValue(1);
        exitFadeTransition.setToValue(0);

        TranslateTransition exitTranslateTransition = new TranslateTransition(Duration.millis(250),menu);
        exitTranslateTransition.setByY(MENU_OFFSET);

        ParallelTransition exitTransitions = new ParallelTransition(menu,exitFadeTransition,exitTranslateTransition);

        Task exitTask = new Task() {
            @Override
            protected Object call() throws Exception {
                while (!menu.isFinished()) {
                    // Thread has to sleep to have enough time to recognise value changes
                    Thread.sleep(1);
                }
                Thread.sleep(1000);
                exitTransitions.play();
                return null;
            }
        };

        exitTask.setOnSucceeded(event -> {
            try {
                players.updatePlayers(model.getCurrentPlayer(), board);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startNextIteration();
        });

        Thread exitThread = new Thread(exitTask);
        exitThread.setDaemon(true);
        exitThread.start();
    }

    private void createPropertyPopup() {

    }

    public static void main(String[] args) {
        launch(args);
    }
}
