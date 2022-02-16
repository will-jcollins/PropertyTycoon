package model.board;

import model.actions.ActCode;
import model.actions.Action;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionTileTest {

    @Test
    void testGetters() {
        String name = "Oppurtunity Knock";
        Action action = new Action(ActCode.OPPURTUNITY, 0,0);
        ActionTile actTile = new ActionTile(name, action);
        assertEquals(actTile.getName(), name);
        assertEquals(actTile.getAction(), action);
    }
}