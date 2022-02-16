package model.actions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static model.actions.ActCode.JAIL;
import static model.actions.ActCode.MOVETO;
import static org.junit.jupiter.api.Assertions.*;

class ActionTest {

    @Test
    void testGetters() {
        ActCode actCode = JAIL;
        int val1 = 10, val2 = 20;
        Action action = new Action(actCode, val1, val2);
        assertEquals(action.getAction(), action);
        assertEquals(action.getActCode(), actCode);
        assertEquals(action.getVal1(), val1);
        assertEquals(action.getVal2(), val2);
    }

    @Test
     void testConstructor() {
        ActCode actCode = MOVETO;
        int val1 = 10, val2 = 20;
        Action action = new Action(actCode + " " + val1 + " " + val2);
        assertEquals(action.getActCode(), MOVETO);
        assertEquals(action.getVal1(), 10);
        assertEquals(action.getVal2(), 20);
    }
}