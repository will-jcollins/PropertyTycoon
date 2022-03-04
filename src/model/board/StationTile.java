package model.board;

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
}
