package model.board;

import model.Player.Player;
import ui.board.ImgCostUITile;
import ui.board.UITile;

public abstract class BuyableTile extends Tile {

    private final int cost;
    private Player owner;

    /**
     * Constructor assigns attributes from parameter
     *
     * @param name value for tile's name
     */
    public BuyableTile(String name, int cost) {
        super(name);

        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public void buy(Player p) {
        p.pay(-getCost());
        setOwner(p);
    }

    public void payRent(Player p, int amount) {
        p.pay(-amount);
        System.out.println(owner);
        owner.pay(amount);
        System.out.println(owner);
    }

    public boolean canBuy() {
        return owner == null;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    @Override
    public UITile getUITile(int x, int y, int width, int height, int angle) {
        return new ImgCostUITile(x, y, width, height, angle, this);
    }
}
