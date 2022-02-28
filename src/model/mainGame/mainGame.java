package model.mainGame;

import model.Player.Player;
import model.board.AssetTile;
import model.board.AssetType;
import model.board.Board;
import model.board.Tile;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.Scanner;

public class mainGame {
    public Scanner scanner;
    public Board gameBoard;
    public Player[] players;
    public mainGame() {
        players = new Player[]{new Player()};
        Scanner scanner = new Scanner(System.in);
        try {
            gameBoard = new Board();
        } catch (IOException e) {
            e.printStackTrace();
        }
        players[0] = ;
    }

    public static void main(String[] args){
        mainGame game = new mainGame();
        int gCount = 3;
        while (gCount < 0){
            gCount--;
            for (Player player: game.players) {
                game.turn(player);
            }
        }

    }

    public void interact(Player p){
    Tile tile = gameBoard.getTile(p.getPos());
    String tileType = tile.getTileType();
    if (tileType == "AssetTile"){
        (AssetTile) Player owner = tile.getOwner();
        if (owner == null){
            System.out.println("wanna buy?");
        }else{
            p.pay(tile.getRent());
            owner.pay(tile.getRent());
        }
    }
    }

    public void turn(Player p){
        int dCount = 0;
        askPlayer("type yes to move");
        boolean doub = p.move(); //This is a boolean for weather there was a double roll
        System.out.println(p.getPos());
        while (doub && dCount < 3){
            dCount ++;
            askPlayer("type yes to move");
            doub = p.move();
            System.out.println(p.getPos());
        }
        if (dCount < 3){
            ;
            System.out.println("Go to jail!");
            //bad things happen
        }
    }
    public boolean askPlayer(String message){
        String playerDecision = "";
        System.out.println(message);
        while(playerDecision != "yes" && playerDecision != "no"){
        playerDecision = scanner.next();
        }
        return (playerDecision == "yes");

    }

}

