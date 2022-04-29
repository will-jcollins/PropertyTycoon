package model.player;


import model.Player.Player;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    void PlayerTest(){
       Player player = new Player(1,"name", false);
       assertEquals(player.getId(),1);
       assertEquals(player.getName(),"name");
       assertEquals(player.isAuto(), false);
        assertEquals(player.getMoney(),1500);
    }

    @Test
    void TestPos(){
        Player player = new Player(1,"name", false);
        player.setPos(5);
        assertEquals(player.getPos(),5);
        player.changePos(6);
        assertEquals(player.getPos(),11);

    }

    @Test
    void TestMoney(){
        Player player = new Player(1,"name", false);
        assertEquals(player.getMoney(), 1500);
        player.pay(500);
        assertEquals(player.getMoney(), 1000);

    }

    @Test
    void TestJailCards(){
        Player player = new Player(1,"name", false);
        assertEquals(player.getJailCards(), 0);
        player.addJailCard();
        assertEquals(player.getJailCards(), 1);
        player.removeJailCard();
        assertEquals(player.getJailCards(), 0);

    }



}
