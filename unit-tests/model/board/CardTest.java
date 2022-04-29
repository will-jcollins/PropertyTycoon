package model.board;

import model.actions.Action;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTest {

    @Test
    void testGetters() {
        Action action = new Action("JAIL");
        Card c = new Card("text", action);
        assertEquals(c.getText(), "text");
        assertEquals(c.getAction(), action);

    }

    @Test
    void testGetString(){
        Action action = new Action("JAIL");
        Card c = new Card("text", action);
        assertEquals(c.toString(), "text");
    }
    }
