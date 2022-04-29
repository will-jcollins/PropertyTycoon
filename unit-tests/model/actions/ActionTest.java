package model.actions;

import org.junit.jupiter.api.BeforeAll;
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
     void testConstructorString() {
        ActCode actCode = MOVETO;
        int val1 = 10, val2 = 20;
        Action action = new Action(actCode + " " + val1 + " " + val2);
        assertEquals(action.getActCode(), MOVETO);
        assertEquals(action.getVal1(), 10);
        assertEquals(action.getVal2(), 20);
    }

    @Test
    void testConstructorTwoVal(){
        //The string input is formated as action val1 val2
        ActCode actCode = MOVETO;
        int val1 = 10, val2 = 20;
        Action action = new Action(actCode, val1 , val2);
        assertEquals(action.getActCode(), MOVETO);
        assertEquals(action.getVal1(), 10);
        assertEquals(action.getVal2(), 20);
    }

    @Test
    void testConstructorOneVal(){
        ActCode actCode = MOVETO;
        int val1 = 10;
        Action action = new Action(actCode, val1);
        assertEquals(action.getActCode(), MOVETO);
        assertEquals(action.getVal1(), 10);
        assertEquals(action.getVal2(), 0);
    }

    @Test
    void testConstructorNoVal(){
        ActCode actCode = MOVETO;
        Action action = new Action(actCode);
        assertEquals(action.getActCode(), MOVETO);
        assertEquals(action.getVal1(), 0);
        assertEquals(action.getVal2(), 0);
    }

    @Test
    void testStringOverride(){
        ActCode actCode = MOVETO;
        Action action = new Action(actCode);
        assertEquals(action.toString(), "Action{" +
                "action=" + MOVETO +
                ", val1=" + 0 +
                ", val2=" + 0 +
                '}' );
    }
}