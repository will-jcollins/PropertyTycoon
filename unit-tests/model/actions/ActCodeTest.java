package model.actions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActCodeTest {

    @Test
    void testFromString() {
        ActCode[] codes = ActCode.values();
        for (ActCode code : codes) {
            assertEquals(ActCode.fromString(code.toString()),code);
        }
    }
}