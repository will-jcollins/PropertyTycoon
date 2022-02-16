package model.board;

import model.actions.ActCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StreetColorTest {

    @Test
    void testFromString() {
        StreetColor[] codes = StreetColor.values();
        for (StreetColor code : codes) {
            assertEquals(StreetColor.fromString(code.toString()),code);
        }
    }
}