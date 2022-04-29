package model.board;

import javafx.scene.paint.Color;
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

    @Test
    void testGetDevelopCost(){
        Street[] codes = Street.values();
        assertEquals(codes[0].getDevelopCost(), 50);
        assertEquals(codes[2].getDevelopCost(), 200);
    }

    @Test
    void testGetColor(){
        Street[] codes = Street.values();
        assertEquals(codes[0].getColor(), Color.SADDLEBROWN);
        assertEquals(codes[2].getColor(), Color.ROYALBLUE);
    }
}