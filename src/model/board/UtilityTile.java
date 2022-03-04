package model.board;

public class UtilityTile extends BuyableTile {

    public static final int[] rentFactor = {4,10};

    /**
     * Constructor assigns attributes from parameters
     *
     * @param name value for tile's name
     * @param cost
     */
    public UtilityTile(String name, int cost) {
        super(name, cost);
    }
}
