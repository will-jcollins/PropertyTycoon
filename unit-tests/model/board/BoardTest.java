package model.board;

import model.actions.Action;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    Board board;

    @Test
    @BeforeEach
    void testConstructor() {
        try {
            board = new Board();
        } catch (IOException e) {
            fail("failed to load board data from file");
        } catch (IllegalStateException e) {
            fail("failed to construct objects from file data");
        }
    }

    @Test
    void testGetTile() {
        ActionTile tile = new ActionTile("Go", new Action("NOP"));
        board.setTile(0, tile);
        assertEquals(board.getTile(0), tile);
    }

    @Test
    void testGetTileName() {
        ActionTile tile = new ActionTile("Go", new Action("NOP"));
        board.setTile(0, tile);
        assertEquals(board.getTile(tile.getName()), tile);
    }
}