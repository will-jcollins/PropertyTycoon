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
    public static final int TURNS_TO_JAIL = 3; // Number of doubles before being sent to jail
    public static final int TURNS_IN_JAIL = 4; // Number of turns spent in jail (including the turn where player is sent to jail)
    public static final int GO_REWARD = 200;
    public static final int JAIL_POS = 10;

    // Model
    private Board board = new Board();
    private ArrayList<Player> players;
    private int currentPlayer = 0;
    private Player storedPlayer;
    private Deck potLuck = new Deck(System.getProperty("user.dir") + "/assets/jsons/PotLuck.json");
    private Deck opportunity = new Deck(System.getProperty("user.dir") + "/assets/jsons/Opportunity.json");
    private int freeParking = 0;
    private String payReason;
    private boolean playerPay;
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

        if (players.size() < 2) {
            return UITip.SHOW_GAME_OVER;
        } else if (getCurrentPlayer().inJail()) {
            return UITip.SHOW_JAIL_MENU;
        } else {
            dice.roll();
            Player player = getCurrentPlayer();
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
            } else if (!buyable.getOwner().equals(getCurrentPlayer()) && !buyable.getOwner().inJail()) {
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

    private UITip executeActionable(Actionable actionable) {

        Action action = actionable.getAction();
        Player tempPlayer;
        switch (action.getActCode()) {
            case PAYFINEOROPP:
                collectedCard = opportunity.draw();
                return UITip.SHOW_OPPCHOICE;
            case BANKPAY:
                getCurrentPlayer().pay(-action.getVal1());
                payReason = "THE BANK";
                playerPay = false;
                return UITip.SHOW_TRANSFERMONEY;
            case PAYBANK:
                getCurrentPlayer().pay(action.getVal1());
                payReason = "THE BANK";
                playerPay = true;
                return UITip.SHOW_TRANSFERMONEY;
            case JAIL:
                getCurrentPlayer().sendToJail();
                return UITip.SHOW_GOTO_JAIL_MENU;
            case MOVETO:
                tempPlayer = players.get(currentPlayer);
                tempPlayer.setPos(action.getVal1());
                if (tempPlayer.getPos() < tempPlayer.getPrevPos() && action.getVal2() == 1) {
                    tempPlayer.pay(GO_REWARD * action.getVal2());
                    passedGo = true;
                }
                break;
            case MOVEN:
                tempPlayer = players.get(currentPlayer);
                int currentPos = tempPlayer.getPos();
                tempPlayer.setPos(currentPos + action.getVal1());
                if (tempPlayer.getPos() < tempPlayer.getPrevPos() && action.getVal2() == 1){
                    tempPlayer.pay(GO_REWARD * action.getVal2());
                    passedGo = true;
                }
                break;
            case PAYASSETS:
                tempPlayer = getCurrentPlayer();
                if (board.getNoHouses(tempPlayer) != 0) {
                    tempPlayer.pay(action.getVal1() * board.getNoHouses(tempPlayer) + action.getVal2() * board.getNoHotels(tempPlayer));
                    payReason = "THE BANK";
                    playerPay = true;
                    return UITip.SHOW_TRANSFERMONEY;
                }
                break;
            case PAYFINE:
                getCurrentPlayer().pay(action.getVal1());
                freeParking += action.getVal1();
                payReason = "THE TAXMAN";
                playerPay = true;
                return UITip.SHOW_TRANSFERMONEY;
            case FINEPAY:
                if (freeParking != 0) {
                    getCurrentPlayer().pay(-freeParking);
                    freeParking = 0;
                    payReason = "COLLECTED FINES";
                    playerPay = false;
                    return UITip.SHOW_TRANSFERMONEY;
                }
                else break;
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
                storedPlayer = players.get(currentPlayer);
                Player playerToPay;
                for (int i = 0; i < players.size(); i++) {
                    playerToPay = players.get(i);
                    playerToPay.pay(action.getVal1());
                    tempPlayer.pay(-action.getVal1());
                }
                return UITip.SHOW_MULTITRANSFER;
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

    public Player getStoredPlayer() {
        return storedPlayer;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    public boolean isPlayersLastRoll() {
        return !dice.isDouble() && !getCurrentPlayer().inJail();
    }

    public boolean isPlayerPaying() { return playerPay; }

    public String getPayReason() { return payReason; }

    public Card getCollectedCard() {
        return collectedCard;
    }

    public static void main(String[] args){
        Game game = new Game(0,4);
        game.iterateGame();
    }
}

