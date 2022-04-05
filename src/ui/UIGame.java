package ui;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Player.Player;
import model.board.BuyableTile;
import model.board.PropertyTile;
import model.game.Dice;
import model.game.Game;
import ui.board.UIBoard;
import ui.menu.*;
import ui.menu.dice.DiceMenu;
import ui.player.PlayerStats;
import ui.player.UIPlayers;


import java.lang.reflect.Array;
import java.util.ArrayList;

public class UIGame extends Application {

    private static final int MENU_OFFSET = 50;

    private StackPane gameStack;
    private ArrayList<PlayerStats> playerStats;

    private UIPlayers players;
    private UIBoard board;
    private Game model;

    @Override
    public void start(Stage primaryStage) {

        model = new Game(2,0);
        Rectangle2D bounds = Screen.getPrimary().getBounds();
        board = new UIBoard(model.getBoard(),(int) (bounds.getHeight() * 0.9));
        players = new UIPlayers(model.getPlayers(), board);

        gameStack = new StackPane();
        gameStack.getChildren().add(board);
        gameStack.getChildren().add(players);

        VBox statsVBox = new VBox();
        statsVBox.setSpacing(10);

        playerStats = new ArrayList<>();

        for (Player player : model.getPlayers()) {
            PlayerStats stats = new PlayerStats(player);
            playerStats.add(stats);
            statsVBox.getChildren().add(stats);
        }

        ArrayList<PropertyTile> temp = new ArrayList<>();
        temp.add((PropertyTile) model.getBoard().getTile(1));

        BorderPane root = new BorderPane(gameStack);
//        root.setLeft(statsVBox);

//        // Sound setup
//        File musicPath = new File("assets/audio/back.mp3");
//        Media backMusic = new Media(musicPath.toURI().toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(backMusic);
//        mediaPlayer.setOnEndOfMedia(() -> {
//            mediaPlayer.seek(Duration.ZERO);
//            mediaPlayer.play();
//        });

        Platform.runLater(() -> {
//            mediaPlayer.play();
            startNextIteration();
        });

        // Scene & Stage setup
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.show();
    }

    private void startNextIteration() {
        players.dismissPlayer(model.getCurrentPlayer());
        UITip tip = model.iterateGame();
        players.higlightPlayer(model.getCurrentPlayer());
        switch (tip) {
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

    private void createTurnEndPopup() {
        if (model.isPlayersLastRoll()) {
            TurnEndMenu menu = new TurnEndMenu();

            showMenu(menu,
                    onShow -> {
                    },
                    onExit -> {
                        if (menu.getOutcome()) {
                            startNextIteration();
                        } else {
                            createDevelopPopup();
                        }
                    });
        } else {
            startNextIteration();
        }
    }

    private void createDevelopPopup() {
        ArrayList<PropertyTile> developProperties = model.getDevelopProperties();
        DevelopMenu menu = new DevelopMenu(developProperties);

        showMenu(menu,onShow -> {}, onExit -> {
            if (menu.getSelectedProperty() != null) {
                model.developProperty(menu.getSelectedProperty());
            }
            board.update();
            createTurnEndPopup();
        });
    }

    private void checkGoReward() {
        if (model.hasPassedGo()) {
            createGoPopup();
        } else {
            takeTurn();
        }
    }

    private void takeTurn() {
        switch (model.takeTurn()) {
            case SHOW_BUY_BUYABLE:
                createBuyablePopup();
                break;
            case SHOW_RENT_PAY:
                createRentPopup();
                break;
            default:
                createTurnEndPopup();
        }
    }

    private void createDicePopup(Dice dice) {
        DiceMenu menu = new DiceMenu(dice);

        showMenu(menu,onShow -> {}, onExit -> {
            try {
                players.updatePlayers(model.getCurrentPlayer(),board,onFinish -> checkGoReward());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void createBuyablePopup() {
        BuyableMenu menu = new BuyableMenu((BuyableTile) model.getBoard().getTile(model.getCurrentPlayer().getPos()),model.getCurrentPlayer());

        showMenu(menu,
                onShow -> {},
                onExit -> {
            if (menu.getOutcome()) {
                model.buyTile((BuyableTile) model.getBoard().getTile(model.getCurrentPlayer().getPos()));
                createTurnEndPopup();
            } else {
                // TODO :: Trigger Auction menu
                createTurnEndPopup(); // Remove once auction menu implemented
            }
        });
    }

    private void createRentPopup() {
        RentMenu menu = new RentMenu((BuyableTile) model.getBoard().getTile(model.getCurrentPlayer().getPos()),model.getCurrentPlayer());

        showMenu(menu,
                onShow -> menu.startAnimation(),
                onExit -> createTurnEndPopup()
        );
    }

    private void createGoPopup() {
        GoMenu menu = new GoMenu(model.getCurrentPlayer());

        showMenu(menu,
                onShow -> menu.startAnimation(),
                onExit -> takeTurn());
    }

    private void showMenu(Menu menu, EventHandler onShow, EventHandler onExit) {
        menu.setOpacity(0);
        gameStack.getChildren().add(menu);
        menu.setTranslateY(menu.getTranslateY() + MENU_OFFSET);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(250),menu);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(250),menu);
        translateTransition.setByY(-MENU_OFFSET);

        ParallelTransition transitions = new ParallelTransition(menu,fadeTransition,translateTransition);
        transitions.setOnFinished(onShow);
        transitions.play();

        FadeTransition exitFadeTransition = new FadeTransition(Duration.millis(250),menu);
        exitFadeTransition.setFromValue(1);
        exitFadeTransition.setToValue(0);

        TranslateTransition exitTranslateTransition = new TranslateTransition(Duration.millis(250),menu);
        exitTranslateTransition.setByY(MENU_OFFSET);

        ParallelTransition exitTransitions = new ParallelTransition(menu,exitFadeTransition,exitTranslateTransition);
        exitTransitions.setOnFinished(event -> {
            remove(menu);
            Platform.runLater(() -> updatePlayerStats());
            onExit.handle(new ActionEvent());
        });

        Task exitTask = new Task() {
            @Override
            protected Object call() throws Exception {
                while (!menu.isFinished()) {
                    // Thread has to sleep to have enough time to recognise value changes
                    Thread.sleep(1);
                }
                Thread.sleep(menu.getEndLatency());
                exitTransitions.play();
                return null;
            }
        };

        Thread exitThread = new Thread(exitTask);
        exitThread.setDaemon(true);
        exitThread.start();
    }

    private void updatePlayerStats() {
        for (PlayerStats stats : playerStats) {
            stats.update();
        }
    }

    private void remove(Node n) {
        gameStack.getChildren().remove(n);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
