package model.player;

import model.Player.HumanPlayer;
import model.Player.Player;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    void PlayerTest(){
       Player player = new HumanPlayer(1,"name");
       assertEquals(player.getId(),1);
       assertEquals(player.getName(),"name");
    }

    @Test
    void TestGetters(){
        Player player = new HumanPlayer(1,"name");
        assertEquals(player.isInJail(),false);
       // assertEquals(player.hasPassedGo(),false);
        assertEquals(player.getMoney(),1500);
    }

}
