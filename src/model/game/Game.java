package model.game;

import model.Player.Player;
import model.actions.Action;
import model.actions.Actionable;
import model.board.*;
import ui.menu.UITip;

import java.util.*;

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
    public static final int AUCTION_TIME = 30; // Time given for each auction

    // Model
    private Board board = new Board();
    private ArrayList<Player> players;
    private int currentPlayer = 0; // Index of current selected player in player list
    private Deck potLuck = new Deck(System.getProperty("user.dir") + "/assets/jsons/PotLuck.json");
    private Deck opportunity = new Deck(System.getProperty("user.dir") + "/assets/jsons/Opportunity.json");
    private int freeParking = 0; // Total fines collected
    private String payReason; // 'Entity' player is paying for an action (purely for UI)
    private boolean playerPay; // Whether player is paying for an action (purely for UI)
    private boolean passedGo = false; // Whether current player passed go on this turn
    private Card collectedCard; // Card picked up on Opportunity knock and Pot Luck spaces
    private Bid maxBid; // Maximum bid in an auction
    private Dice dice = new Dice(2,6); // Random number generator

    /**
     * Constructor of class Game
     * @param players - arraylist of human and non-human players
     */
    public Game(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     * Iterates game by 1 turn, either moves the player from a dice roll or handles jail logic
     * @return enum signal for UI
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

        // Check for bankruptcy from other player's turn
        if (getCurrentPlayer().getMoney() < 0) {
            return UITip.SHOW_BANKRUPT;
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
     * Takes action based on the tile the player is currently on
     * @return enum signal for UI
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
            } else if (!buyable.getOwner().equals(getCurrentPlayer()) && !buyable.getOwner().inJail() && !buyable.isMortgaged()) {
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

                getCurrentPlayer().pay(rentToPay);
                buyable.getOwner().pay(-rentToPay);

                // Check for bankruptcy
                if (getCurrentPlayer().getMoney() < 0) {
                    return UITip.SHOW_BANKRUPT;
                }

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
     * Method responsible for pointing to player for the next turn
     */
    public void selectNextPlayer() {
        currentPlayer = (currentPlayer + 1) % players.size();
        dice.reset();
    }

    /**
     * Performs an enumerated action based on the parameter passed
     * @param actionable interface with Action class
     * @return enum signal for UI
     */
    private UITip executeActionable(Actionable actionable) {

        Action action = actionable.getAction();
        Player currentPlayer = getCurrentPlayer();

        switch (action.getActCode()) {
            case PAYFINEOROPP:
                // Leave a drawn card which can be executed later
                collectedCard = opportunity.draw();
                return UITip.SHOW_OPPCHOICE;
            case BANKPAY:
                // Give supplied amount of money to player
                currentPlayer.pay(-action.getVal1());
                payReason = "BANK";
                playerPay = false;
                return UITip.SHOW_TRANSFERMONEY;
            case PAYBANK:
                // Remove supplied amount of money from player
                currentPlayer.pay(action.getVal1());
                payReason = "BANK";
                playerPay = true;

                // Check for bankruptcy
                if (currentPlayer.getMoney() < 0) {
                    return UITip.SHOW_BANKRUPT;
                }

                return UITip.SHOW_TRANSFERMONEY;
            case JAIL:
                // Send player to jail
                getCurrentPlayer().sendToJail();

                return UITip.SHOW_GOTO_JAIL_MENU;
            case MOVEBACKTO:
                getCurrentPlayer().setMovingBack(true);
            case MOVETO:
                // Change position
                currentPlayer.setPos(action.getVal1());

                // If player passed go and command says to provide go reward, provide go reward
                if (currentPlayer.getPos() < currentPlayer.getPrevPos() && action.getVal2() == 1) {
                    currentPlayer.pay(-GO_REWARD);
                    passedGo = true;
                }
                return UITip.MOVE_PLAYER;
            case MOVEBACKN:
                // Change position
                currentPlayer.setMovingBack(true);
                currentPlayer.setPos(currentPlayer.getPos() - action.getVal1());

                // If player passed go and command says to provide go reward, provide go reward
                if (currentPlayer.getPos() < currentPlayer.getPrevPos() && action.getVal2() == 1){
                    currentPlayer.pay(-GO_REWARD);
                    passedGo = true;
                }
                return UITip.MOVE_PLAYER;
            case MOVEN:
                // Change position
                currentPlayer.setPos(currentPlayer.getPos() + action.getVal1());

                // If player passed go and command says to provide go reward, provide go reward
                if (currentPlayer.getPos() < currentPlayer.getPrevPos() && action.getVal2() == 1){
                    currentPlayer.pay(-GO_REWARD);
                    passedGo = true;
                }
                return UITip.MOVE_PLAYER;
            case PAYASSETS:
                // If player has more than one house / hotel developed
                if (board.getNoHouses(getCurrentPlayer()) > 0 || board.getNoHotels(getCurrentPlayer()) > 0) {
                    // Pay a factor of the number of houses and hotels
                    currentPlayer.pay(action.getVal1() * board.getNoHouses(currentPlayer) + action.getVal2() * board.getNoHotels(currentPlayer));

                    // Signal to UI reason for paying
                    payReason = "BANK";
                    playerPay = true;

                    // Check for bankruptcy
                    if (currentPlayer.getMoney() < 0) {
                        return UITip.SHOW_BANKRUPT;
                    }

                    return UITip.SHOW_TRANSFERMONEY;
                }

                return UITip.NOP; // Pay nothing if player has no houses or hotels developed
            case PAYFINE:
                // Pay amount given in command
                currentPlayer.pay(action.getVal1());

                // Add paid amount to pot of money available on free parking space
                freeParking += action.getVal1();

                // Signal to UI reason for paying
                payReason = "TAX";
                playerPay = true;

                // Check for bankruptcy
                if (currentPlayer.getMoney() < 0) {
                    return UITip.SHOW_BANKRUPT;
                }

                return UITip.SHOW_TRANSFERMONEY;
            case FINEPAY:
                // If any money has been paid to taxes, give it to the current player
                if (freeParking != 0) {
                    // Pay player and reset pot of money available on free parking
                    currentPlayer.pay(-freeParking);
                    freeParking = 0;

                    // Signal to UI reason for paying
                    payReason = "FINES";
                    playerPay = false;

                    return UITip.SHOW_TRANSFERMONEY;
                }
                else return UITip.NOP; // Otherwise, pay them nothing
            case POTLUCK:
                // Leave a drawn card which can be executed later
                collectedCard = potLuck.draw();
                return UITip.SHOW_POTLUCK;
            case OPPORTUNITY:
                // Leave a drawn card which can be executed later
                collectedCard = opportunity.draw();
                return UITip.SHOW_OPPORTUNITY;
            case JAILCARD:
                // Increase player's jail card count by 1
                currentPlayer.addJailCard();
                return UITip.NOP;
            case COLLECTALL:
                // Increase current player's money by number of players * val1 of action
                // Decrease all other player's money by val 1 of action
                Player playerToPay;
                for (int i = 0; i < players.size(); i++) {
                    playerToPay = players.get(i);
                    playerToPay.pay(action.getVal1());
                    currentPlayer.pay(-action.getVal1());
                }
                return UITip.SHOW_MULTITRANSFER;
            default:
                return UITip.NOP;
        }
    }

    /**
     * Returns card picked up from Opportunity knock or Pot luck tile
     * If no card has been picked up returns null
     * @return card object
     */
    public UITip executeCollectedCard() {
        return executeActionable(collectedCard);
    }

    /**
     * Returns list of properties that can be developed
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
     * Returns list of streets where player owns every property
     * @param p instance of class player
     * @return list of streets which player owns
     */
    private ArrayList<Street> streetsOwnedByPlayer(Player p) {
        ArrayList<Street> streets = new ArrayList<>(Arrays.asList(Street.values()));

        for (Street street : Street.values()) {
            ArrayList<PropertyTile> properties = board.getStreetTiles(street);

            for (PropertyTile prop : properties) {
                if (prop.getOwner() != p) {
                    // If player is not the owner of a street remove street from output list
                    streets.remove(street);
                    // Start searching the next street
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
        // Pay the street's cost of building a house
        getCurrentPlayer().pay(prop.getStreet().getDevelopCost());
        // Increase the number of houses for the property by 1
        prop.setNoHouses(prop.getNoHouses() + 1);
    }

    /**
     * Method for removing player from game
     * @param p Instance of class player
     */
    public void removePlayer(Player p) {
        // Remove player from list of playing players
        players.remove(p);
        // Update current player to be within bounds of list size
        currentPlayer = currentPlayer % players.size();
        // Remove ownership from properties for player that has been removed
        board.freeProperties(p);
    }

    /**
     * Returns dice object used to calculate rolls
     * @return dice
     */
    public Dice getDice() {
        return dice;
    }

    /**
     * Returns board object used by game
     * @return board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Purchases the tile current player is on for current player
     * @param tile instance of BuyableTile class to be bought
     */
    public void buyTile(BuyableTile tile) {
        buyTile(tile,getCurrentPlayer(),tile.getCost());
    }

    private void buyTile(BuyableTile tile, Player player, int cost) {
        tile.setOwner(player);
        player.pay(cost);
    }

    /**
     * Removes player from jail
     * @param option way in which player has left jail
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

    /**
     * Creates a dice removes player from jail if it rolls a double
     * @return dice object created for roll
     */
    public Dice rollForJail() {
        // Create and roll a temporary dice
        Dice tempDice = new Dice(2,6);
        tempDice.roll();

        // If player rolled a double remove them from jail
        if (tempDice.isDouble()) {
            getCurrentPlayer().leaveJail();
        }

        return tempDice;
    }

    /**
     * Resets auction system
     */
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

    /**
     * @return highest bid in current auction, if no bid exists returns null
     */
    public Bid getMaxBid() {
        return maxBid;
    }

    /**
     * Getter for list of players that have not gone bankrupt
     * @return ArrayList of players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Returns true if current player has passed GO on this iteration, otherwise false
     * @return boolean
     */
    public boolean hasPassedGo() {
        return passedGo;
    }

    /**
     * Gets the currently selected player
     * @return player object
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    /**
     * Returns true if player does not receive another turn
     * @return boolean
     */
    public boolean isPlayersLastRoll() {
        return !dice.isDouble() || getCurrentPlayer().inJail();
    }

    /**
     * Returns a list of the properties, utilities and stations
     * Owned by the player passed as a parameter
     * @param p player who's owned properties, utilities and stations are returned
     * @return List of player's owned properties, utilities and stations
     */
    public ArrayList<BuyableTile> ownedByPlayer(Player p) {
        ArrayList<BuyableTile> playerBuyables = new ArrayList<>();

        for (int i = 0; i < Board.SIZE; i++) {
            if (board.getTile(i) instanceof BuyableTile) {
                BuyableTile tile = (BuyableTile) board.getTile(i);
                if (tile.getOwner() != null) {
                    if (tile.getOwner().equals(p)) {
                        // If player passed is the owner of tile add them to the return list
                        playerBuyables.add(tile);
                    }
                }
            }
        }

        return playerBuyables;
    }

    /**
     * Returns true if player is paying someone else as the result of an Action
     * @return boolean
     */
    public boolean isPlayerPaying() { return playerPay; }

    /**
     * returns name of the 'entity' player is paying
     * @return string name
     */
    public String getPayReason() { return payReason; }

    /**
     * Method which sells a property back to the bank
     * If property has houses or a hotel developed on it then 1 house / hotel will be sold instead
     * @param buyable the property for selling
     */
    public void sellBuyable(BuyableTile buyable) {
        if (buyable.getOwner() != null) {
            if (buyable instanceof PropertyTile) {
                PropertyTile property = (PropertyTile) buyable;
                // If there is a house developed on the property sell house instead
                if (property.getNoHouses() > 0) {
                    property.setNoHouses(property.getNoHouses() - 1);
                    buyable.getOwner().pay(-property.getStreet().getDevelopCost());
                } else {
                    // Otherwise, sell property for cost and remove ownership
                    buyable.getOwner().pay(-buyable.getCost());
                    board.freeProperty(buyable);
                }
            } else {
                // Otherwise, sell property for cost and remove ownership
                buyable.getOwner().pay(-buyable.getCost());
                board.freeProperty(buyable);
            }
        }
    }

    /**
     * Method for mortgaging a property
     * @param buyable the property to mortgage
     */
    public void mortgageBuyable(BuyableTile buyable) {
        if (buyable.getOwner() != null) {
            buyable.getOwner().pay(-buyable.getCost() / 2);
            buyable.setMortgaged(true);
        }
    }

    /**
     * Calculates the combined value of player's assets as well as their balance
     * @param p player who's value will be calculated
     * @return value
     */
    public int calculateValue(Player p)
    {
        // Start with player's current balance
        int value = p.getMoney();

        for (BuyableTile tile : ownedByPlayer(p)) {

            // Sum value of buyables, half value if property was mortgaged
            if (tile.isMortgaged()) {
                value += tile.getCost() / 2;
            }
            else {
                value += tile.getCost();
            }

            // Sum value of houses
            if (tile instanceof PropertyTile) {
                value += ((PropertyTile) tile).getNoHouses() * ((PropertyTile) tile).getStreet().getDevelopCost();
            }
        }

        return value;
    }

    /**
     * If there is more than one player left, returns highest value player
     * Otherwise returns last remaining player
     * @return player who has won the game
     */
    public Player getWinner()
    {
        if (players.size() > 1) {
            ArrayList<Integer> values = new ArrayList<>();

            // Calculate value of all players
            for (Player p : players) {
                values.add(calculateValue(p));
            }

            // Find max value
            int max = Collections.max(values);
            int index = 0;

            for (int i = 0; i < values.size(); i++) {
                // If player's value is the same as max set index of return value to current index
                if (values.get(i) == max) {
                    index = i;
                }
            }
            return players.get(index);
        } else {
            // If less than 2 players left, winner is last remaining player
            return players.get(0);
        }
    }

    /**
     * Returns card collected by player on opportunity knock or pot luck space
     * @return card object
     */
    public Card getCollectedCard() {
        return collectedCard;
    }
}

