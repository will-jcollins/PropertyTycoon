package model.mainGame;

import model.Player.AIPlayer;
import model.Player.HumanPlayer;
import model.Player.Player;
import model.actions.Action;
import model.actions.Actionable;
import model.board.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    public static final int TURNS_TO_JAIL = 3;

    // Model
    private Board board = new Board();
    private ArrayList<Player> players;
    private int currentPlayer = 0;

    // RNG
    private Dice dice = new Dice(2,6);


    public Game(int noHumans, int noAIs) {

        players = new ArrayList<>();

        for (int i = 0; i < noHumans; i++) {
            players.add(new HumanPlayer(i, "Hello"));
        }

        for (int i = 0; i < noAIs; i++) {
            players.add(new AIPlayer(i));
        }
    }

    public void startGameLoop() {
        // While end state of game has not been reached
        while (players.size() > 1) {
            boolean anotherTurn;
            dice.roll();

            if (dice.getDoubles() >= TURNS_TO_JAIL) {
                // After 3 doubles send player straight to jail and prep next player
                sendToJail(players.get(currentPlayer));
                anotherTurn = false;
            } else {
                takeTurn(players.get(currentPlayer));
                anotherTurn = dice.isDouble();
            }

            System.out.println("Player " + players.get(currentPlayer).getId() + " has " + players.get(currentPlayer).getMoney());

            if (players.get(currentPlayer).getMoney() <= 0) {
                removePlayer(players.get(currentPlayer));
                anotherTurn = false;
            }

            if (!anotherTurn) {
                dice.reset();
                selectNextPlayer();
            }
        }
    }

    public void selectNextPlayer() {
        currentPlayer = (currentPlayer + 1) % players.size();
    }

    public void sendToJail(Player p) {

    }

    private void takeTurn(Player p) {
        // Move the player
        p.askPlayer("Do you want to move");
        int prevPos = p.getPos();
        p.changePos(dice.getRollTotal());

        if (prevPos > p.getPos()) {
//            executeActionable(new Action(BANKPAY, 10, 0));
        }


        // Interact with tile landed on
        Tile tile = board.getTile(p.getPos());
        System.out.println("landed on " + tile.getName());
        if (tile instanceof ActionTile) {
            executeActionable((ActionTile) tile);
        }
        else if (tile instanceof BuyableTile) {
            interactWithBuyable(p);
        }

    }

    private void interactWithBuyable(Player p) {
        BuyableTile tile = (BuyableTile) board.getTile(p.getPos());
        Player owner = tile.getOwner();

        // If the property is not owned by anyone yet prompt player to buy it
        if (tile.canBuy()) {
            boolean toBuy = p.askPlayer("wanna buy "+ tile.getName()+" for "+ tile.getCost()+"?");
            if (toBuy){
                tile.buy(p);
            } else {
                // TODO :: Auction mechanics
            }
        } else if (owner != p) {
            int rentToPay;

            // Calculate the amount of rent to pay based on tile type
            if (tile instanceof PropertyTile) {
                rentToPay = ((PropertyTile) tile).getRent();
            } else if (tile instanceof StationTile) {
                StationTile station = (StationTile) tile;
                // Index rent by number of stations owned by owner
                rentToPay = StationTile.rent[noStationsOwned(station.getOwner())];
            } else if (tile instanceof UtilityTile) {
                UtilityTile utility = (UtilityTile) tile;
                // Pay rent as a factor of the number of utilities owned by owner
                rentToPay = UtilityTile.rentFactor[noUtilitiesOwned(utility.getOwner())] * dice.getRollTotal();
            } else {
                throw new IllegalStateException("Case for tile not enumerated");
            }


            System.out.println("pay " + rentToPay);

            tile.payRent(p, rentToPay);
        }
    }

    private void executeActionable(Actionable actionable) {
        Action action = actionable.getAction();

        switch (action.getActCode()) {
            case BANKPAY:
                players.get(currentPlayer).pay(action.getVal1());
                break;
            default:
                // TODO :: enumerate rest of cases
        }

        System.out.println(actionable);
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

    public void removePlayer(Player p) {
        players.remove(p);
        currentPlayer = currentPlayer % players.size();
        board.freeProperties(p);
    }

    public static void main(String[] args){
        Game game = new Game(0,4);
        game.startGameLoop();
    }
}

