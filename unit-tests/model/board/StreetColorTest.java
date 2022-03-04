package model.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StreetColorTest {

    @Test
    void testFromString() {
        Street[] codes = Street.values();
        for (Street code : codes) {
            assertEquals(Street.fromString(code.toString()),code);
        }
    }
}