package model.board;

import ui.board.UITile;

public class StationTile extends BuyableTile {

    public static int[] rent = new int[] {25,50,100,200};

    /**
     * Constructor assigns attributes from parameters
     *
     * @param name value for tile's name
     * @param cost
     */
    public StationTile(String name, int cost) {
        super(name, cost);
    }

    @Override
    public UITile getUITile(int x, int y, int width, int height, int angle) {
        return super.getUITile(x, y, width, height, angle);
    }
}
