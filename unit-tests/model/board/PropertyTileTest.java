package model.board;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PropertyTileTest {

    @Test
    void testGetters() {
        String name = "Montreal";
        Street color = Street.BROWN;
        int cost = 60;
        int[] rent = {1, 2, 3, 4, 5, 6};
        ArrayList<Street> streets = new ArrayList<>(Arrays.asList(Street.values()));
        Street t = streets.get(0);
        PropertyTile propTile = new PropertyTile(name, cost, rent, t);
        // assertEquals(propTile.getName(), name);
        // assertEquals(propTile.getCost(), cost);
        assertEquals(propTile.getStreet(), color);
        assertEquals(propTile.getNoHouses(), 0);
        assertEquals(propTile.getRent(2), 3);
        assertEquals(propTile.getRent(), 1);
        // assertEquals(propTile.getOwnerID(), -1);

    }
}
