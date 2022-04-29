package model.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilityTileTest {

    @Test
    void testGetters() {
        String name = "utility";
        UtilityTile tile = new UtilityTile(name, 200);
        assertEquals(tile.getName(), name);
    }
}