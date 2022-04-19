package model.game;

import javafx.scene.paint.Color;
import model.Player.AIPlayer;
import model.Player.HumanPlayer;
import model.Player.Player;
import model.actions.ActCode;
import model.actions.Action;
import model.actions.Actionable;
import model.board.*;
import ui.menu.UITip;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Game {

    // Constants
    public static final int TURNS_TO_JAIL = 3;
    public static final int GO_REWARD = 200;

    // Model
    private Board board = new Board();
    private ArrayList<Player> players;
    private int currentPlayer = 0;
    private Deck potLuck = new Deck(System.getProperty("user.dir") + "/assets/jsons/PotLuck.json");
    private Deck opportunity = new Deck(System.getProperty("user.dir") + "/assets/jsons/Opportunity.json");
    private int freeParking = 0;
    private boolean gameOver = false;
    private boolean passedGo = false; // Whether current player passed go on this turn
    private Card collectedCard;
    // RNG
    private Dice dice = new Dice(2,6);


    public Game(int noHumans, int noAIs) {

        players = new ArrayList<>();

        for (int i = 0; i < noHumans; i++) {

            // Testing with 2 human players
            if (i == 0) {
                players.add(new HumanPlayer(i, "Will"));
            } else if (i == 1) {
                players.add(new HumanPlayer(i, "Levi"));
            } else {
                players.add(new HumanPlayer(i, "Hello"));
            }
        }

        for (int i = 0; i < noAIs; i++) {
            players.add(new AIPlayer(i));
        }
    }

    public UITip iterateGame() {
        passedGo = false;
        collectedCard = null;

        // Select next player if needed
        if (isPlayersLastRoll()) {
            selectNextPlayer();
        }

        // While end state of game has not been reached
        if (players.size() < 2) {
            return UITip.SHOW_GAME_OVER;
        } else {
            dice.roll();
            Player player = players.get(currentPlayer);
            player.changePos(dice.getRollTotal());

            if (player.getPos() < player.getPrevPos()) {
                player.pay(-GO_REWARD);
                passedGo = true;
            }

            return UITip.SHOW_DICE_MENU;
        }
    }

    public void selectNextPlayer() {
        currentPlayer = (currentPlayer + 1) % players.size();
        dice.reset();
    }

    public UITip takeTurn() {
        passedGo = false;
        Player p = getCurrentPlayer();

        // Interact with tile landed on
        Tile tile = board.getTile(p.getPos());
        if (tile instanceof BuyableTile) {
            BuyableTile buyable = (BuyableTile) tile;
            if (buyable.getOwner() == null) {
                // If player has passed go, give them opportunity to buy
                return p.hasPassedGo() ? UITip.SHOW_BUY_BUYABLE : UITip.NOP;
            } else if (!buyable.getOwner().equals(getCurrentPlayer())) {
                int rentToPay;
                // Calculate the amount of rent to pay based on tile type
                if (tile instanceof PropertyTile) {
                    PropertyTile prop = (PropertyTile) tile;
                    rentToPay = prop.getRent();

                    // If all properties in street and there are no houses, rent multiplied by 2
                    if (streetsOwnedByPlayer(prop.getOwner()).contains(prop.getStreet()) && prop.getNoHouses() < 1) {
                        rentToPay = rentToPay * 2;
                    }

                } else if (tile instanceof StationTile) {
                    StationTile station = (StationTile) tile;
                    // Index rent by number of stations owned by owner
                    rentToPay = StationTile.rent[board.noStationsOwned(station.getOwner()) - 1];
                } else if (tile instanceof UtilityTile) {
                    UtilityTile utility = (UtilityTile) tile;
                    // Pay rent as a factor of the number of utilities owned by owner
                    rentToPay = UtilityTile.rentFactor[board.noUtilitiesOwned(utility.getOwner()) - 1] * dice.getRollTotal();
                } else {
                    throw new IllegalStateException("Case for buyable tile not enumerated");
                }

                // If rent to be paid exceeds player's current money, player is bankrupt
                if (rentToPay > getCurrentPlayer().getMoney()) {
                    return UITip.SHOW_BANKRUPT;
                }

                getCurrentPlayer().pay(rentToPay);
                buyable.getOwner().pay(-rentToPay);

                return UITip.SHOW_RENT_PAY;
            } else {
                return UITip.NOP;
            }
        } else if (tile instanceof ActionTile) {
            return executeActionable((ActionTile) tile);
        }
        else {
            return UITip.NOP;
        }
    }

    private void sendToJail(Player player) {
    player.setPos(10);
    }

    private UITip executeActionable(Actionable actionable) {

        Action action = actionable.getAction();
        System.out.println(action);
        Player tempPlayer;
        switch (action.getActCode()) {
            case BANKPAY:
                getCurrentPlayer().pay(-action.getVal1());
                break;
            case PAYBANK:
                getCurrentPlayer().pay(action.getVal1());
                break;
            case JAIL:
                sendToJail(players.get(currentPlayer));
                break;
            case MOVETO:
                tempPlayer = players.get(currentPlayer);
                tempPlayer.setPos(action.getVal1());
                if (tempPlayer.getPos() < tempPlayer.getPrevPos()) {
                    tempPlayer.pay(GO_REWARD * action.getVal2());
                    passedGo = action.getVal2() == 1;
                }
                break;
            case MOVEN:
                tempPlayer = players.get(currentPlayer);
                int currentPos = tempPlayer.getPos();
                tempPlayer.setPos(currentPos + action.getVal1());
                if (tempPlayer.getPos() < tempPlayer.getPrevPos()){
                    tempPlayer.pay(GO_REWARD * action.getVal2());
                    passedGo = action.getVal2() == 1;
                }
                break;
            case PAYASSETS:
                tempPlayer = getCurrentPlayer();
                tempPlayer.pay(action.getVal1() * board.getNoHouses(tempPlayer) + action.getVal2() * board.getNoHotels(tempPlayer));
                break;
            case PAYFINE:
                getCurrentPlayer().pay(action.getVal1());
                freeParking += action.getVal1();
                break;
            case FINEPAY:
                getCurrentPlayer().pay(-freeParking);
                freeParking = 0;
                break;
            case POTLUCK:
                collectedCard = potLuck.draw();
                return UITip.SHOW_POTLUCK;
            case OPPORTUNITY:
                collectedCard = opportunity.draw();
                return UITip.SHOW_OPPORTUNITY;
            case JAILCARD:
                tempPlayer = players.get(currentPlayer);
                tempPlayer.addJailCard();
                break;
            case COLLECTALL:
                tempPlayer = players.get(currentPlayer);
                Player playerToPay;
                for (int i = 0; i < players.size(); i++) {
                    playerToPay = players.get(i);
                    playerToPay.pay(action.getVal1());
                    tempPlayer.pay(-action.getVal1());
                }
            default:
                return UITip.NOP;
        }

        return UITip.NOP;
    }

    public UITip executeCollectedCard() {
        return executeActionable(collectedCard);
    }

    public ArrayList<PropertyTile> getDevelopProperties(Player p) {
        ArrayList<PropertyTile> out = new ArrayList<>();

        for (Street street : streetsOwnedByPlayer(p)) {
            ArrayList<PropertyTile> props = board.getStreetTiles(street);

            // Find minimum number of houses
            int minNoHouses = PropertyTile.MAX_NO_HOUSES + 1;
            for (PropertyTile prop : props) {
                minNoHouses = Math.min(minNoHouses, prop.getNoHouses());
            }

            // Exclude properties that are greater than or equal to minimum number of houses plus 1
            for (PropertyTile prop : props) {
                if (prop.getNoHouses() < (minNoHouses + 1) && prop.getNoHouses() < PropertyTile.MAX_NO_HOUSES) {
                    out.add(prop);
                }
            }
        }

        return out;
    }

    private ArrayList<Street> streetsOwnedByPlayer(Player p) {
        ArrayList<Street> streets = new ArrayList<>(Arrays.asList(Street.values()));

        for (Street street : Street.values()) {
            ArrayList<PropertyTile> properties = board.getStreetTiles(street);

            for (PropertyTile prop : properties) {
                if (prop.getOwner() != p) {
                    streets.remove(street);
                    break;
                }
            }
        }

        return streets;
    }

    public void developProperty(PropertyTile prop) {
        Player p = getCurrentPlayer();
        p.pay(prop.getStreet().getDevelopCost());
        prop.setNoHouses(prop.getNoHouses() + 1);
    }

    public void removePlayer(Player p) {
        players.remove(p);
        currentPlayer = currentPlayer % players.size();
        board.freeProperties(p);
    }

    public Dice getDice() {
        return dice;
    }

    public Board getBoard() {
        return board;
    }

    public void buyTile(BuyableTile tile) {
        Player p = getCurrentPlayer();
        tile.setOwner(p);
        p.pay(tile.getCost());
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean hasPassedGo() {
        return passedGo;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    public boolean isPlayersLastRoll() {
        return !dice.isDouble() && !getCurrentPlayer().isInJail();
    }

    public Card getCollectedCard() {
        return collectedCard;
    }

    public static void main(String[] args){
        Game game = new Game(0,4);
        game.iterateGame();
    }
}

