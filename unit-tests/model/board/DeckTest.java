package model.board;

import model.actions.Action;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeckTest {

    @Test
    void testDraw() {
     Deck d = new Deck(System.getProperty("user.dir") + "/assets/jsons/PotLuck.json");
     Card c = d.draw();

     assertEquals(c!=null, true);
    }

}
