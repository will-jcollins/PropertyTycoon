package model.board;

import ui.board.PropertyUITile;
import ui.board.UITile;

import java.util.Arrays;
/**
 * Class which describes PropertyTiles
 */
public class PropertyTile extends BuyableTile {

    public static final int MAX_NO_HOUSES = 5;

    private final Street street;
    private final int[] rent;
    private int noHouses = 0;

    /**
     * Constructor of PropertyTile class
     * @param name name of the tile
     * @param cost cost of the tile
     * @param rent array of rents of the tile
     */
    public PropertyTile(String name, int cost, int[] rent, Street group) {
        super(name,cost);

        // Attribute assignment
        this.street = group;
        this.rent = rent;

        if (rent.length != MAX_NO_HOUSES + 1) {
            throw new IllegalArgumentException("array of rents should be " + MAX_NO_HOUSES + ", instead is " + rent.length);
        }
    }

    /**
     * Street getter
     * @return street
     */
    public Street getStreet() {
        return street;
    }


    public int getRent() {
        return rent[noHouses];
    }

    public int getRent(int i) {
        return rent[i];
    }

    public int getNoHouses() {
        return noHouses;
    }

    public void setNoHouses(int noHouses) {
        this.noHouses = noHouses;
    }

    @Override
    public UITile getUITile(int x, int y, int width, int height, int angle) {
        return new PropertyUITile(x,y,width,height,angle,this);
    }

    @Override
    public String toString() {
        return "PropertyTile{" +
                "name=" + getName() +
                ", group=" + street +
                ", cost=" + getCost() +
                ", rent=" + Arrays.toString(rent) +
                ", owner=" + getOwner().toString() +
                ", noHouses=" + noHouses +
                '}';
    }


}
