package model.game;

import model.Player.Player;
import model.actions.Action;
import model.actions.Actionable;
import model.board.*;
import ui.menu.UITip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * Class for controlling game
 */
public class  Game {

    // Constants
    public static final int DOUBLES_TO_JAIL = 3; // Number of doubles before being sent to jail
    public static final int TURNS_IN_JAIL = 4; // Number of turns spent in jail (including the turn where player is sent to jail)
    public static final int JAIL_COST = 50; // Amount paid to leave jail
    public static final int GO_REWARD = 200; // Money player receives after passing GO
    public static final int JAIL_POS = 10; // Tile where Jail and Just Visiting are
    public static final int AUCTION_TIME = 60; // Time given for each auction

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
    private Bid maxBid;
    // RNG
    private Dice dice = new Dice(2,6);

    /**
     * Constructor of class Game
     * @param players - arraylist of human and non-human players
     */
    public Game(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     * Mehtod for running turns of the game
     * @return appropriate enum for action
     */
    public UITip iterateGame() {
        passedGo = false;
        collectedCard = null;

        // Reset player attributes used to help UI
        if (getCurrentPlayer().hasLeftJail() && !getCurrentPlayer().inJail()) {
            getCurrentPlayer().setLeftJail(false);
        }
        getCurrentPlayer().setMovingBack(false);

        // Select next player if needed
        if (isPlayersLastRoll()) {
            selectNextPlayer();
        }

        dice.roll();

        if (players.size() < 2) {
            return UITip.SHOW_GAME_OVER;
        } else if (getCurrentPlayer().getTurnsInJail() >= TURNS_IN_JAIL) {
            dice.reset();
            getCurrentPlayer().leaveJail();
            return UITip.EXIT_JAIL;
        } else if (getCurrentPlayer().inJail()) {
            dice.reset();
            getCurrentPlayer().addTurnInJail();
            return UITip.SHOW_JAIL_MENU;
        } else if (dice.getDoubles() == DOUBLES_TO_JAIL) {
            getCurrentPlayer().sendToJail();
            return UITip.SHOW_DICE_FOR_JAIL;
        } else {
            Player player = getCurrentPlayer();
            player.changePos(dice.getRollTotal());

            if (player.getPos() < player.getPrevPos()) {
                player.pay(-GO_REWARD);
                passedGo = true;
            }
            return UITip.SHOW_DICE_MENU;
        }
    }

    /**
     * Method responsible for pointing to player for the next turn
     */
    public void selectNextPlayer() {
        currentPlayer = (currentPlayer + 1) % players.size();
        dice.reset();
    }

    /**
     * Method responsible for running turns
     * @return - UITip with action
     */
    public UITip takeTurn() {
        passedGo = false;
        Player p = getCurrentPlayer();

        // Interact with tile landed on
        Tile tile = board.getTile(p.getPos());
        if (tile instanceof BuyableTile) {
            BuyableTile buyable = (BuyableTile) tile;
            if (buyable.getOwner() == null) {
                // If player has passed go, give them opportunity to buy, otherwise don't
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

    /**
     * Execute action
     * @param actionable - action
     * @return UITip action
     */
    private UITip executeActionable(Actionable actionable) {

        Action action = actionable.getAction();
        Player tempPlayer;
        switch (action.getActCode()) {
            case PAYFINEOROPP:
                collectedCard = opportunity.draw();
                return UITip.SHOW_OPPCHOICE;
            case BANKPAY:
                getCurrentPlayer().pay(-action.getVal1());
                payReason = "BANK";
                playerPay = false;
                return UITip.SHOW_TRANSFERMONEY;
            case PAYBANK:
                getCurrentPlayer().pay(action.getVal1());
                payReason = "BANK";
                playerPay = true;
                return UITip.SHOW_TRANSFERMONEY;
            case JAIL:
                getCurrentPlayer().sendToJail();
                return UITip.SHOW_GOTO_JAIL_MENU;
            case MOVEBACKTO:
                getCurrentPlayer().setMovingBack(true);
            case MOVETO:
                tempPlayer = players.get(currentPlayer);
                tempPlayer.setPos(action.getVal1());
                if (tempPlayer.getPos() < tempPlayer.getPrevPos() && action.getVal2() == 1) {
                    tempPlayer.pay(-GO_REWARD);
                    passedGo = true;
                }
                return UITip.MOVE_PLAYER;
            case MOVEBACKN:
                getCurrentPlayer().setMovingBack(true);
                tempPlayer = players.get(currentPlayer);
                tempPlayer.setPos(tempPlayer.getPos() - action.getVal1());
                if (tempPlayer.getPos() < tempPlayer.getPrevPos() && action.getVal2() == 1){
                    tempPlayer.pay(-GO_REWARD);
                    passedGo = true;
                }
                return UITip.MOVE_PLAYER;
            case MOVEN:
                tempPlayer = players.get(currentPlayer);
                int currentPos = tempPlayer.getPos();
                tempPlayer.setPos(currentPos + action.getVal1());
                if (tempPlayer.getPos() < tempPlayer.getPrevPos() && action.getVal2() == 1){
                    tempPlayer.pay(-GO_REWARD);
                    passedGo = true;
                }
                return UITip.MOVE_PLAYER;
            case PAYASSETS:
                tempPlayer = getCurrentPlayer();
                if (board.getNoHouses(getCurrentPlayer()) > 0 || board.getNoHotels(getCurrentPlayer()) > 0) {
                    tempPlayer.pay(action.getVal1() * board.getNoHouses(tempPlayer) + action.getVal2() * board.getNoHotels(tempPlayer));
                    payReason = "BANK";
                    playerPay = true;
                    return UITip.SHOW_TRANSFERMONEY;
                }
                return UITip.NOP;
            case PAYFINE:
                getCurrentPlayer().pay(action.getVal1());
                freeParking += action.getVal1();
                payReason = "TAX";
                playerPay = true;
                return UITip.SHOW_TRANSFERMONEY;
            case FINEPAY:
                if (freeParking != 0) {
                    getCurrentPlayer().pay(-freeParking);
                    freeParking = 0;
                    payReason = "FINES";
                    playerPay = false;
                    return UITip.SHOW_TRANSFERMONEY;
                }
                else return UITip.NOP;
            case POTLUCK:
                collectedCard = potLuck.draw();
                return UITip.SHOW_POTLUCK;
            case OPPORTUNITY:
                collectedCard = opportunity.draw();
                return UITip.SHOW_OPPORTUNITY;
            case JAILCARD:
                tempPlayer = players.get(currentPlayer);
                tempPlayer.addJailCard();
                return UITip.NOP;
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
    }

    /**
     * Returns card
     * @return cards
     */
    public UITip executeCollectedCard() {
        return executeActionable(collectedCard);
    }

    /**
     * Returns list of properties to develop
     * @param p instance of class player
     * @return list of properties
     */
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

    /**
     * Returns list of streets which player owns
     * @param p instance of class player
     * @return list of streets which player owns
     */
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

    /**
     * Method for developing properties
     * @param prop instance of class propertyTile
     */
    public void developProperty(PropertyTile prop) {
        Player p = getCurrentPlayer();
        p.pay(prop.getStreet().getDevelopCost());
        prop.setNoHouses(prop.getNoHouses() + 1);
    }

    /**
     * Method for removing player from property
     * @param p Instance of class player
     */
    public void removePlayer(Player p) {
        players.remove(p);
        currentPlayer = currentPlayer % players.size();
        board.freeProperties(p);
    }

    /**
     * Returns dice
     * @return dice
     */
    public Dice getDice() {
        return dice;
    }

    /**
     * Getter of board
     * @return board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Purchases the tile current player is on in for current player
     * @param tile instance of BuyableTile class
     */
    public void buyTile(BuyableTile tile) {
        buyTile(tile,getCurrentPlayer(),tile.getCost());
    }

    private void buyTile(BuyableTile tile, Player player, int cost) {
        tile.setOwner(player);
        player.pay(cost);
    }

    /**
     * Method for leaving jail
     * @param option instance of JailOption class
     */
    public void leaveJail(JailOption option) {
        switch (option) {
            case JAILCARD:
                getCurrentPlayer().removeJailCard();
                getCurrentPlayer().leaveJail();
                break;
            case PAY:
                getCurrentPlayer().pay(JAIL_COST);
                getCurrentPlayer().leaveJail();
                break;
            case ROLL_DICE:
                getCurrentPlayer().leaveJail();
                break;
            case WAIT:
            default:
                throw new IllegalArgumentException(option + " not enumerated in leaveJail (Game)");
        }
    }

    public Dice rollForJail() {
        Dice tempDice = new Dice(2,6);
        tempDice.roll();
        if (tempDice.isDouble()) {
            getCurrentPlayer().leaveJail();
        }
        return tempDice;
    }

    public void startAuction() {
        maxBid = null;
    }

    /**
     * Adds a bid to the auction, returns true if bid is valid, false otherwise
     * @param bid bid being made
     * @return boolean indicating if bid was valid
     */
    public boolean addBid(Bid bid) {
        // Invalid bid if less than zero
        if (bid.getAmount() <= 0) {
            return false;
        }

        // If bid amount is greater than current bid, bid is valid
        if (maxBid != null) {
            if (maxBid.getAmount() < bid.getAmount()) {
                maxBid = bid;
                return true;
            }
        } else {
            maxBid = bid;
            return true;
        }

        return false;
    }

    /**
     * Uses data gathered in auction to either purchase property for
     * highest bidder or do nothing if no bids were made
     * @return null if no valid bid was made, otherwise player who made last successful bid
     */
    public Player actOnAuction() {
        if (maxBid != null) {
            // Buy tile and return player who won auction
            buyTile((BuyableTile) board.getTile(getCurrentPlayer().getPos()), getMaxBid().getPlayer(), maxBid.getAmount());
            return maxBid.getPlayer();
        } else {
            return null;
        }
    }

    public Bid getMaxBid() {
        return maxBid;
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
        return !dice.isDouble() || getCurrentPlayer().inJail();
    }

    public boolean isPlayerPaying() { return playerPay; }

    public String getPayReason() { return payReason; }

    public Card getCollectedCard() {
        return collectedCard;
    }
}

