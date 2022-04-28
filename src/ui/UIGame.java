package ui;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Player.Player;
import model.board.BuyableTile;
import model.board.PropertyTile;
import model.board.Street;
import model.game.Dice;
import model.game.Game;
import ui.board.UIBoard;
import ui.menu.*;
import ui.menu.dice.DiceMenu;
import ui.player.PlayerStats;
import ui.player.UIPlayers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Main class responsible for starting UI game
 */
public class UIGame extends BorderPane {

    private static final int MENU_OFFSET = 50;

    private StackPane gameStack;
    private ArrayList<PlayerStats> playerStats;

    private UIPlayers players;
    private UIBoard board;
    private Game model;

    private UITimer timer;

    public UIGame(Game model, long gameLength) {
        Sizes.computeSizes();

        this.model = model;

        // Create a board that is 9/10 the size of the screen height
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        board = new UIBoard(model.getBoard(),(int) (screenBounds.getHeight() * 0.9));

        players = new UIPlayers(model.getPlayers(), board);

        // Stack player tokens on top of board
        gameStack = new StackPane();
        gameStack.getChildren().add(board);
        gameStack.getChildren().add(players);

        // Create vertical list of player information and stats
        VBox statsVBox = new VBox();
        statsVBox.setSpacing(Sizes.getPadding());
        playerStats = new ArrayList<>();

        for (Player player : model.getPlayers()) {
            PlayerStats stats = new PlayerStats(player);
            playerStats.add(stats);
            statsVBox.getChildren().add(stats);
        }

        // Button which allows players to quit the game without finishing
        TextButton quitButton = new TextButton(Sizes.getButtonWidth(),Sizes.getButtonHeight(), Street.RED.getColor(),"QUIT");
        quitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> quit());
        statsVBox.getChildren().add(quitButton);

        // If game has a time limit, add timer to stats VBox
        if (gameLength > 0) {
            timer = new UITimer(gameLength);
            statsVBox.getChildren().add(timer);
        }

        // Create layout with board in center and player stats to the left
        setCenter(gameStack);
        setLeft(statsVBox);
        setPadding(new Insets(Sizes.getPadding(),Sizes.getPadding(),Sizes.getPadding(),Sizes.getPadding()));
        setBackground(new Background(new BackgroundFill(new Color(1,0.89,0.83,0.5),null,null)));
    }

    public void start() {
        if (timer != null) {
            timer.start();
        }
        UITip tip = model.iterateGame();
        players.higlightPlayer(model.getCurrentPlayer());
        TurnMenu menu = new TurnMenu(model.getCurrentPlayer());
        showMenu(menu, onShow -> {}, onExit -> executeUITip(tip));
    }

    /**
     * Method responsible for starting next iteration
     */
    private void startNextIteration() {
        if (timer != null) {
            if (timer.isFinished()) {
                createGameOverPopup();
            } else {
                Player prevPlayer = model.getCurrentPlayer();
                UITip tip = model.iterateGame();
                Player nextPlayer = model.getCurrentPlayer();
                players.dismissPlayer(prevPlayer);
                players.higlightPlayer(nextPlayer);
                if (prevPlayer.equals(nextPlayer)) {
                    executeUITip(tip);
                } else {
                    TurnMenu menu = new TurnMenu(nextPlayer);
                    showMenu(menu, onShow -> {}, onExit -> executeUITip(tip));
                }
            }
        } else {
            Player prevPlayer = model.getCurrentPlayer();
            UITip tip = model.iterateGame();
            Player nextPlayer = model.getCurrentPlayer();
            players.dismissPlayer(prevPlayer);
            players.higlightPlayer(nextPlayer);
            if (prevPlayer.equals(nextPlayer)) {
                executeUITip(tip);
            } else {
                TurnMenu menu = new TurnMenu(nextPlayer);
                showMenu(menu, onShow -> {}, onExit -> executeUITip(tip));
            }
        }
    }

    /**
     * Method responsible for creating bunkrupt popup menu
     */
    private void createBankruptPopup() {
        BankruptMenu menu = new BankruptMenu(model);

        showMenu(menu,
                onShow -> {Platform.runLater(() -> updatePlayerStats());},
                onExit -> {
                    if (menu.didConcede()) {
                        model.removePlayer(model.getCurrentPlayer());
                        players.removePlayer(model.getCurrentPlayer());
                        Platform.runLater(() -> board.update());
                        Platform.runLater(() -> updatePlayerStats());
                        startNextIteration();
                    } else {
                        model.sellBuyable(menu.getSelectedProperty());
                        Platform.runLater(() -> board.update());
                        Platform.runLater(() -> updatePlayerStats());
                        if (model.getCurrentPlayer().getMoney() > 0) {
                            startNextIteration();
                        } else {
                            createBankruptPopup();
                        }
                    }
                });
    }

    /**
     * Method responsible for creating game over popup menu
     */
    private void createGameOverPopup() {
        GameOverMenu menu = new GameOverMenu(model.getWinner(),model.calculateValue(model.getWinner()));

        showMenu(menu,
                onShow -> {},
                onExit -> quit());
    }

    /**
     * Mehtod responsible for creating go to prison popup
     */
    private void createGoToJailPopUp()
    {
        GoToPrisonMenu menu = new GoToPrisonMenu (model.getCurrentPlayer ());
        showMenu(menu,
                onShow -> {},
                onExit -> Platform.runLater(() -> players.updatePlayers(model.getCurrentPlayer(), board,e -> startNextIteration())));
    }

    /**
     * Mehtod responsibe for creating popup menu at the end of players turn
     */
    private void createTurnEndPopup() {
        if (model.isPlayersLastRoll()) {
            TurnEndMenu menu = new TurnEndMenu(model);

            showMenu(menu,
                    onShow -> {
                    },
                    onExit -> {
                        if (menu.getOutcome() == 0) {
                            startNextIteration();
                        } else if(menu.getOutcome() == 1){
                            createDevelopPopup();
                        }else if(menu.getOutcome() == 2){
                            createMortgageMenu();
                        }
                        else{
                            createSellMenu();
                        }
                    });
        } else {
            startNextIteration();
        }
    }

    /**
     * Method responsible for creating menu for money transfer between two players
     */
    private void createTransferMoney() {
        TransferMoneyMenu menu = new TransferMoneyMenu(model.getCurrentPlayer(), model.isPlayerPaying(), model.getPayReason());

        showMenu(menu,
                onShow -> menu.startAnimation(),
                onExit -> {
                    Platform.runLater(() -> updatePlayerStats());
                    createTurnEndPopup();
                }
        );
    }

    /**
     * Method responsible for creating menu for money transfer between multiple players
     */
    private void createMultiTransferPopup() {
        int i = 0;

        String[] playerNames = new String[model.getPlayers().size()];

        for (Player otherPlayer : model.getPlayers()) {
            if (!Objects.equals(otherPlayer.getName(), model.getCurrentPlayer().getName())) {
                playerNames[i] = otherPlayer.getName().toUpperCase();
            } else playerNames[i] = "";
            i++;
        }

        MultiTransferMoneyMenu menu = new MultiTransferMoneyMenu(model.getCurrentPlayer(), playerNames);

        showMenu(menu,
                onShow -> menu.startAnimation(),
                onExit -> {Platform.runLater(() -> updatePlayerStats());
                    createTurnEndPopup();
                }
        );
    }

    /**
     * Method responsible for creating popup menu for developing properties
     */
    private void createDevelopPopup() {
        ArrayList<PropertyTile> developProperties = model.getDevelopProperties(model.getCurrentPlayer());
        DevelopMenu menu = new DevelopMenu(developProperties, model.getCurrentPlayer());

        showMenu(menu,onShow -> {}, onExit -> {
            // If player made a selection develop that property
            if (menu.getSelectedProperty() != null) {
                model.developProperty(menu.getSelectedProperty());
            }
            // Update the board and return to turn end menu
            Platform.runLater(() -> board.update());
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

    private void checkGoAndEnd() {
        if (model.hasPassedGo()) {
            GoMenu menu = new GoMenu(model.getCurrentPlayer());

            showMenu(menu,
                    onShow -> menu.startAnimation(),
                    onExit -> {
                        updatePlayerStats();
                        createTurnEndPopup();
                    });
        } else {
            createTurnEndPopup();
        }
    }

    private void takeTurn() {
        executeUITip(model.takeTurn());
    }

    /**
     * Method responsible for choosing which UI action to perform based on what happens in the model
     * @param tip different action varibles
     */
    private void executeUITip(UITip tip) {
        switch (tip) {
            case SHOW_DICE_MENU:
                DiceMenu menu = new DiceMenu(model.getDice());
                showMenu(menu,onShow -> {}, onExit -> {
                    players.updatePlayers(model.getCurrentPlayer(),board,onFinish -> checkGoReward());
                });
                break;
            case SHOW_DICE_FOR_JAIL:
                // Shows dice menu without moving the player
                DiceMenu diceMenu = new DiceMenu(model.getDice());
                showMenu(diceMenu,onShow -> {}, onExit -> createGoToJailPopUp());
                break;
            case SHOW_GAME_OVER:
                createGameOverPopup();
                break;
            case SHOW_BUY_BUYABLE:
                createBuyablePopup();
                break;
            case SHOW_RENT_PAY:
                createRentPopup();
                break;
            case SHOW_BANKRUPT:
                createBankruptPopup();
                break;
            case SHOW_OPPORTUNITY:
                createCardPopup("OPPORTUNITY KNOCK",model.getCollectedCard().getText());
                break;
            case SHOW_POTLUCK:
                createCardPopup("POT LUCK",model.getCollectedCard().getText());
                break;
            case SHOW_JAIL_MENU:
                createJailPopup();
                break;
            case SHOW_GOTO_JAIL_MENU:
                createGoToJailPopUp();
                break;
            case SHOW_TRANSFERMONEY:
                createTransferMoney();
                break;
            case SHOW_OPPCHOICE:
                createOppChoicePopup();
                break;
            case SHOW_MULTITRANSFER:
                createMultiTransferPopup();
                break;
            case MOVE_PLAYER:
                Platform.runLater(() -> players.updatePlayers(model.getCurrentPlayer(), board, e -> checkGoAndEnd()));
                break;
            case EXIT_JAIL:
                Platform.runLater(() -> players.updatePlayers(model.getCurrentPlayer(), board,e -> takeTurn()));
                break;
            case NOP:
            default:
                createTurnEndPopup();
        }
    }

    /**
     * Creatine popup for card when player stands on a property
     * @param title Property name
     * @param description describtion of the card
     */
    private void createCardPopup(String title, String description) {
        CardMenu menu = new CardMenu(title,description);
        showMenu(menu, onShow -> updatePlayerStats(), onExit -> executeUITip(model.executeCollectedCard()));
    }

    /**
     * Method responsible for creating pot luck menu
     */
    private void createOppChoicePopup() {
        OppChoiceMenu menu = new OppChoiceMenu(model.getCurrentPlayer());
        showMenu(menu,
                onShow -> {},
                onExit -> {
                    if(menu.getOutcome()) {
                        executeUITip(UITip.SHOW_OPPORTUNITY);
                    } else {
                        model.getCurrentPlayer().pay(10);
                        Platform.runLater(() -> updatePlayerStats());
                        createTurnEndPopup();
                    }

                });
    }

    /**
     * Method responsible for creating popup menu for buying action
     */
    private void createBuyablePopup() {
        BuyableMenu menu = new BuyableMenu((BuyableTile) model.getBoard().getTile(model.getCurrentPlayer().getPos()),model.getCurrentPlayer());

        showMenu(menu,
                onShow -> {},
                onExit -> {
            if (menu.getOutcome()) {
                model.buyTile((BuyableTile) model.getBoard().getTile(model.getCurrentPlayer().getPos()));
                createTurnEndPopup();
            } else {
                // Show auction menu if property was not purchased
                AuctionMenu auctionMenu = new AuctionMenu(model);
                showMenu(auctionMenu,onShow -> {
                    model.startAuction();
                    auctionMenu.start();
                }, onExit1 -> {
                    if (model.getMaxBid() != null) {
                        AuctionWonMenu wonMenu = new AuctionWonMenu(model.getMaxBid().getPlayer());
                        showMenu(wonMenu, onShow -> {
                        }, onExit2 -> {
                            model.actOnAuction();
                            Platform.runLater(() -> updatePlayerStats());
                            Platform.runLater(() -> board.update());
                            createTurnEndPopup();
                        });
                    } else {
                        createTurnEndPopup();
                    }
                });
            }
            Platform.runLater(() -> updatePlayerStats());
            Platform.runLater(() -> board.update());
        });
    }

    /**
     * Method responsible for creating menu for rent transaction
     */
    private void createRentPopup() {
        RentMenu menu = new RentMenu((BuyableTile) model.getBoard().getTile(model.getCurrentPlayer().getPos()),model.getCurrentPlayer());

        showMenu(menu,
                onShow -> menu.startAnimation(),
                onExit -> {
                    Platform.runLater(() -> updatePlayerStats());
                    createTurnEndPopup();
        }
        );
    }

    /**
     * Method responsible for creating menu when player passes starting point
     */
    private void createGoPopup() {
        GoMenu menu = new GoMenu(model.getCurrentPlayer());

        showMenu(menu,
                onShow -> menu.startAnimation(),
                onExit -> {
                    Platform.runLater(() -> updatePlayerStats());
                    takeTurn();
        });
    }

    /**
     * Method responsible for creating method for player which goes to jail
     */
    private void createJailPopup() {
        JailMenu menu = new JailMenu(model.getCurrentPlayer());

        showMenu(menu,
                onShow -> {},
                onExit -> {
                    switch (menu.getOutcome()) {
                        case ROLL_DICE:
                            // Create DiceMenu and leave jail if dices are double
                            Dice tempDice = model.rollForJail();
                            DiceMenu diceMenu = new DiceMenu(tempDice);
                            showMenu(diceMenu,onShow1 -> {}, onExit1 -> {
                                if (tempDice.isDouble()) {
                                    players.updatePlayers(model.getCurrentPlayer(),board,e -> createTurnEndPopup());
                                } else {
                                    startNextIteration();
                                }
                            });
                            break;
                        case JAILCARD:
                        case PAY:
                            model.leaveJail(menu.getOutcome());
                            Platform.runLater(() -> updatePlayerStats());
                            players.updatePlayers(model.getCurrentPlayer(),board,e -> createTurnEndPopup());
                            break;
                        case WAIT:
                        default:
                            startNextIteration();
                    }
                }
        );
    }

    private void createMortgageMenu()
    {
        ArrayList<BuyableTile> mortageProperties = model.ownedByPlayer(model.getCurrentPlayer());
        MortgageMenu mm = new MortgageMenu(mortageProperties);

        showMenu(mm,onShow -> {}, onExit -> {
            if (mm.getSelectedProperty() != null) {
                model.mortgageBuyable(mm.getSelectedProperty());
            }
            Platform.runLater(() -> board.update());
            Platform.runLater(() -> updatePlayerStats());
            createTurnEndPopup();
        });
    }

    private void createSellMenu()
    {
        ArrayList<BuyableTile> sellProperties = model.ownedByPlayer(model.getCurrentPlayer());
        SellingMenu sm = new SellingMenu(sellProperties);

        showMenu(sm,onShow -> {}, onExit -> {
            if (sm.getSelectedProperty() != null) {
                model.sellBuyable(sm.getSelectedProperty());
            }
            Platform.runLater(() -> board.update());
            Platform.runLater(() -> updatePlayerStats());
            createTurnEndPopup();
        });
    }

    /**
     * Method responsible for showing menu
     * @param menu menu instance
     * @param onShow starting EventHandler
     * @param onExit exit EventHandler
     */
    private void showMenu(Menu menu, EventHandler onShow, EventHandler onExit) {
        // Add menu to be visible on gameStack (StackPane)
        menu.setOpacity(0);
        gameStack.getChildren().add(menu);
        menu.setTranslateY(menu.getTranslateY() + MENU_OFFSET);

        // Create open and close animations
        FadeTransition showFadeTransition = new FadeTransition(Duration.millis(250),menu);
        showFadeTransition.setFromValue(0);
        showFadeTransition.setToValue(1);

        TranslateTransition showTranslateTransition = new TranslateTransition(Duration.millis(250),menu);
        showTranslateTransition.setByY(-MENU_OFFSET);

        ParallelTransition showTransition = new ParallelTransition(menu,showFadeTransition,showTranslateTransition);
        showTransition.setOnFinished(e -> {
            // If current player is AI click through automatically
            onShow.handle(new ActionEvent());
            if (model.getCurrentPlayer().isAuto()) {
                menu.autoFire();
            }
        });
        showTransition.play();

        FadeTransition exitFadeTransition = new FadeTransition(Duration.millis(250),menu);
        exitFadeTransition.setFromValue(1);
        exitFadeTransition.setToValue(0);

        TranslateTransition exitTranslateTransition = new TranslateTransition(Duration.millis(250),menu);
        exitTranslateTransition.setByY(MENU_OFFSET);

        ParallelTransition exitTransitions = new ParallelTransition(menu,exitFadeTransition,exitTranslateTransition);
        exitTransitions.setOnFinished(event -> {
            remove(menu);
            onExit.handle(new ActionEvent());
        });

        // Listen for change in finish value and close when set to true
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

    /**
     * Method responsible for updating player stats
     * (Sidebar menu with each player's information)
     */
    private void updatePlayerStats() {
        for (PlayerStats stats : playerStats) {
            stats.update();
        }
    }

    private void remove(Node n) {
        gameStack.getChildren().remove(n);
    }

    private void quit() {
        ((Stage) getScene().getWindow()).close();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Start");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
