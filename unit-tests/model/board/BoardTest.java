package model.board;


import model.Player.Player;
import model.actions.Action;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    Board board;



    @Test
    @BeforeEach
    void testConstructor() {
        try {
            board = new Board();
        } catch (Exception e) {
            fail("failed to load board data from file");
        }
        assertEquals(board.SIZE, 40);
    }

    @Test
    void testGetTile() {
        ActionTile tile = new ActionTile("Go", new Action("NOP"));
        board.setTile(0, tile);
        assertEquals(board.getTile(0), tile);
    }

    @Test
    void testGetTileName() {
        ActionTile tile = new ActionTile("Go", new Action("NOP"));
        board.setTile(0, tile);
        assertEquals(board.getTile(0), tile);
    }

    @Test
    void testGetStreetTiles(){
        int[] rent = {1,2,3, 4,5, 6};

        ArrayList<Street> streets = new ArrayList<>(Arrays.asList(Street.values()));
        Street t = streets.get(0);

        PropertyTile tile = new PropertyTile("testProperty", 500,rent,t);
        board.setTile(0, tile);
        assertEquals(board.getTile(0), tile);
    }

    @Test
    void testFreeProperties(){
        int[] rent = {1,2,3, 4,5, 6};

        ArrayList<Street> streets = new ArrayList<>(Arrays.asList(Street.values()));
        Street t = streets.get(0);

        PropertyTile tile = new PropertyTile("testProperty", 500,rent,t);
        board.setTile(0, tile);
        BuyableTile buyable = tile;
        Player p = new Player(1, "Test", false);
        buyable.setOwner(p);

        assertEquals(buyable.getOwner(), p);
        board.freeProperties(p);
        assertEquals(buyable.getOwner(), null);


    }

    @Test
    void testNoProperties(){
        int[] rent = {1,2,3, 4,5, 6};

        ArrayList<Street> streets = new ArrayList<>(Arrays.asList(Street.values()));
        Street t = streets.get(0);

        PropertyTile tile = new PropertyTile("testProperty", 500,rent,t);
       StationTile station = new StationTile("station", 200);
        board.setTile(0, tile);
        board.setTile(1, station);
        BuyableTile buyable = tile;
        Player p = new Player(1, "Test", false);
        buyable.setOwner(p);
        station.setOwner(p);

        //assertEquals(board.getNoHouses(p), 0);
        assertEquals(board.noStationsOwned(p), 1);
        assertEquals(board.noUtilitiesOwned(p), 0);

    }
}