package model.game;

import model.Player.AIPlayer;
import model.Player.HumanPlayer;
import model.Player.Player;
import model.actions.Action;
import model.actions.Actionable;
import model.board.*;
import ui.menu.UITip;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Game {

    public static final int TURNS_TO_JAIL = 3;
    public static final int GO_REWARD = 200;

    private boolean passedGo = false;

    // Model
    private Board board = new Board();
    private ArrayList<Player> players;
    private int currentPlayer = 0;

    // RNG
    private Dice dice = new Dice(2,6);

    private boolean gameOver = false;


    public Game(int noHumans, int noAIs) {

        players = new ArrayList<>();

        for (int i = 0; i < noHumans; i++) {
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
                    rentToPay = ((PropertyTile) tile).getRent();
                } else if (tile instanceof StationTile) {
                    StationTile station = (StationTile) tile;
                    // Index rent by number of stations owned by owner
                    rentToPay = StationTile.rent[noStationsOwned(station.getOwner()) - 1];
                } else if (tile instanceof UtilityTile) {
                    UtilityTile utility = (UtilityTile) tile;
                    // Pay rent as a factor of the number of utilities owned by owner
                    rentToPay = UtilityTile.rentFactor[noUtilitiesOwned(utility.getOwner()) - 1] * dice.getRollTotal();
                } else {
                    throw new IllegalStateException("Case for buyable tile not enumerated");
                }

                getCurrentPlayer().pay(rentToPay);
                buyable.getOwner().pay(-rentToPay);

                return UITip.SHOW_RENT_PAY;
            } else {
                return UITip.NOP;
            }
        } else {
            return UITip.NOP;
        }
    }

//    private void interactWithBuyable(Player p) {
//        BuyableTile tile = (BuyableTile) board.getTile(p.getPos());
//        Player owner = tile.getOwner();
//
//        // If the property is not owned by anyone yet prompt player to buy it
//        if (tile.canBuy()) {
//            boolean toBuy = p.askPlayer("wanna buy "+ tile.getName()+" for "+ tile.getCost()+"?");
//            if (toBuy){
//                tile.buy(p);
//            } else {
//                // TODO :: Auction mechanics
//            }
//        } else if (owner != p) {
//            int rentToPay;
//
//            // Calculate the amount of rent to pay based on tile type
//            if (tile instanceof PropertyTile) {
//                rentToPay = ((PropertyTile) tile).getRent();
//            } else if (tile instanceof StationTile) {
//                StationTile station = (StationTile) tile;
//                // Index rent by number of stations owned by owner
//                rentToPay = StationTile.rent[noStationsOwned(station.getOwner())];
//            } else if (tile instanceof UtilityTile) {
//                UtilityTile utility = (UtilityTile) tile;
//                // Pay rent as a factor of the number of utilities owned by owner
//                rentToPay = UtilityTile.rentFactor[noUtilitiesOwned(utility.getOwner())] * dice.getRollTotal();
//            } else {
//                throw new IllegalStateException("Case for tile not enumerated");
//            }
//
//
//            System.out.println("pay " + rentToPay);
//
//            tile.payRent(p, rentToPay);
//        }
//    }

    private void executeActionable(Actionable actionable) {
        Action action = actionable.getAction();

        switch (action.getActCode()) {
            case BANKPAY:
                players.get(currentPlayer).pay(action.getVal1());
                break;
            default:
                // TODO :: enumerate rest of cases
        }
    }

    private int noStationsOwned(Player p) {
        int noStations = 0;

        for (int i = 0; i < Board.SIZE; i++) {
            Tile tile = board.getTile(i);

            if (tile instanceof StationTile) {
                if (((StationTile) tile).getOwner() == p) {
                    noStations++;
                }
            }
        }

        return noStations;
    }

    private int noUtilitiesOwned(Player p) {
        int noStations = 0;

        for (int i = 0; i < Board.SIZE; i++) {
            Tile tile = board.getTile(i);

            if (tile instanceof UtilityTile) {
                if (((UtilityTile) tile).getOwner() == p) {
                    noStations++;
                }
            }
        }

        return noStations;
    }

    public ArrayList<PropertyTile> getDevelopProperties() {
        Player p = getCurrentPlayer();
        ArrayList<PropertyTile> out = new ArrayList<>();

        for (Street street : streetsOwnedByPlayer()) {
            ArrayList<PropertyTile> props = board.getStreetTiles(street);

            // Find minimum number of houses
            int minNoHouses = PropertyTile.MAX_NO_HOUSES + 1;
            for (PropertyTile prop : props) {
                minNoHouses = Math.min(minNoHouses, prop.getNoHouses());
            }

            // Exclude properties that are greater than or equal to minimum number of houses plus 1
            for (PropertyTile prop : props) {
                if (prop.getNoHouses() < (minNoHouses + 1) && p.getMoney() >= prop.getStreet().getDevelopCost() && prop.getNoHouses() < PropertyTile.MAX_NO_HOUSES) {
                    out.add(prop);
                }
            }
        }

        return out;
    }

    private ArrayList<Street> streetsOwnedByPlayer() {
        Player p = getCurrentPlayer();
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

    private void removePlayer(Player p) {
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
        return !dice.isDouble();
    }

    public static void main(String[] args){
        Game game = new Game(0,4);
        game.iterateGame();
    }
}

