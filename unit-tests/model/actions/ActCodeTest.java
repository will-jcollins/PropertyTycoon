package model.actions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActCodeTest {

    //tests if string inputs are correctly matched to enum values
    @Test
    void testFromString() {
        ActCode[] codes = ActCode.values();
        for (ActCode code : codes) {
            System.out.println(ActCode.fromString(code.toString()) + " = " + code);
            assertEquals(ActCode.fromString(code.toString()),code);
        }
    }
}