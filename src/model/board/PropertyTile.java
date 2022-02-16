package model.board;

import java.util.Arrays;

public class PropertyTile extends Tile {

    public static final int MAXNOHOUSES = 6;

    private StreetColor group;
    // Cost attributes
    private int cost;
    private int[] rent;
    // Development attributes
    private int owner = -1;
    private int noHouses = 0;

    public PropertyTile(String name, StreetColor group, int cost, int[] rent) {
        super(name);

        // Attribute assignment
        this.group = group;
        this.cost = cost;
        this.rent = rent;

        if (rent.length != MAXNOHOUSES) {
            throw new IllegalArgumentException("array of rents should be " + MAXNOHOUSES + ", instead is " + rent.length);
        }
    }

    public StreetColor getGroup() {
        return group;
    }

    public int getCost() {
        return cost;
    }

    public int getRent() {
        return rent[noHouses];
    }

    public int getOwnerID() {
        return owner;
    }

    public int getNoHouses() {
        return noHouses;
    }

    public boolean addHouse() {
        int prevHouses = noHouses;
        noHouses = Math.min(noHouses + 1, MAXNOHOUSES - 1);
        return !(prevHouses == noHouses);
    }

    @Override
    public String toString() {
        return "PropertyTile{" +
                "name=" + getName() +
                ", group=" + group +
                ", cost=" + cost +
                ", rent=" + Arrays.toString(rent) +
                ", owner=" + owner +
                ", noHouses=" + noHouses +
                '}';
    }
}
