package model.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    @Test
    void testGetters() {
        String name = "GO";
        Tile tile = new Tile(name);
        assertEquals(tile.getName(), name);
    }
}