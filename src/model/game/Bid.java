package model.game;

import model.Player.Player;

public class Bid {

    private Player player;
    private int amount;

    public Bid(Player player, int amount) {
        this.player = player;
        this.amount = amount;
    }

    public Player getPlayer() {
        return player;
    }

    public int getAmount() {
        return amount;
    }
}
