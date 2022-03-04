package model.board;

import java.util.Arrays;

public class PropertyTile extends BuyableTile {

    public static final int MAX_NO_HOUSES = 6;

    private final Street street;
    private final int[] rent;
    private int noHouses = 0;

    public PropertyTile(String name, int cost, int[] rent, Street group) {
        super(name,cost);

        // Attribute assignment
        this.street = group;
        this.rent = rent;

        if (rent.length != MAX_NO_HOUSES) {
            throw new IllegalArgumentException("array of rents should be " + MAX_NO_HOUSES + ", instead is " + rent.length);
        }
    }

    public Street getStreet() {
        return street;
    }

    public int getRent() {
        return rent[noHouses];
    }

    public int getNoHouses() {
        return noHouses;
    }

    public void setNoHouses(int noHouses) {
        this.noHouses = noHouses;
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
