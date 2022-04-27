package model.game;

import model.Player.Player;

/**
 * Class for creating bids for auctions
 */
public class Bid {

    private Player player;
    private int amount;

    public Bid(Player player, int amount) {
        this.player = player;
        this.amount = amount;
    }

    /**
     * Getter for player
     * @return INstance of class player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the bid amount
     * @return the amount value
     */
    public int getAmount() {
        return amount;
    }
}
