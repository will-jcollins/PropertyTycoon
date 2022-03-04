package model.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertyTileTest {

    @Test
    void testGetters() {
        String name = "Montreal";
        Street color = Street.BROWN;
        int cost = 60;
        int[] rent = {1, 2, 3, 4, 5, 6};
        PropertyTile propTile = new PropertyTile(name, color, cost, rent);
        assertEquals(propTile.getName(), name);
        assertEquals(propTile.getCost(), cost);
        assertEquals(propTile.getStreet(), color);
        assertEquals(propTile.getNoHouses(), 0);
        assertEquals(propTile.getOwnerID(), -1);

        for (int i = 0; i < PropertyTile.MAX_NO_HOUSES; i++) {
            assertEquals(rent[i], propTile.getRent());
            propTile.addHouse();
        }
    }

    @Test
    void testAddHouse() {
        String name = "Montreal";
        Street color = Street.BROWN;
        int cost = 60;
        int[] rent = {1, 2, 3, 4, 5, 6};
        PropertyTile propTile = new PropertyTile(name, color, cost, rent);
        for (int i = 0; i < PropertyTile.MAX_NO_HOUSES - 1; i++) {
            assertTrue(propTile.addHouse());
        }
        assertFalse(propTile.addHouse());
    }
}