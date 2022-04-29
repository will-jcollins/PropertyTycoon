package model.board;

import model.actions.ActCode;
import model.actions.Action;
import org.junit.jupiter.api.Test;
import ui.board.ImgUITile;

import static org.junit.jupiter.api.Assertions.*;

class ActionTileTest {

    @Test
    void testConstructor() {
        String name = "Oppurtunity Knock";
        Action action = new Action(ActCode.OPPORTUNITY, 0,0);
        ActionTile actTile = new ActionTile(name, action);
        assertEquals(actTile.getName(), name);
        assertEquals(actTile.getAction(), action);
    }

    @Test
    void testToString(){
        String name = "Oppurtunity Knock";
        Action action = new Action(ActCode.OPPORTUNITY, 0,0);
        ActionTile actTile = new ActionTile(name, action);
        assertEquals(actTile.toString(), "ActionTile{" + "name=" + "Oppurtunity Knock" + ", action=" + action + '}');
    }

/*
    @Test
    void testGetUITile(){
        String name = "Go to jail";
        Action action = new Action(ActCode.JAIL, 0,0);
        ActionTile actTile = new ActionTile(name, action);

        ImgUITile tile = new ImgUITile(1,3,1,3,2, "file:assets/images/gotojail.png");

        assertEquals(actTile.getUITile(1,3,1,3,2), tile);
    }*/
}