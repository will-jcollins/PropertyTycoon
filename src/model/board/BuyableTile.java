package model.board;

import model.Player.Player;
import ui.board.AssetUITile;
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

    /**
     * Getter of variable cost
     * @return cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * Method which perform buying operation as well as after buying
     * sets ownership of a place to a player
     * @param p Instance of class Player
     */
    public void buy(Player p) {
        p.pay(-getCost());
        setOwner(p);
    }

    /**
     * @return returns owner of a tile
     */
    public Player getOwner() {
        return owner;
    }
    /**
     * Sets owner of a tile
     * @param owner a player who is supposed to be owning a tile
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    @Override
    public UITile getUITile(int x, int y, int width, int height, int angle) {
        return new AssetUITile(x,y,width,height,angle,this);
    }
}
