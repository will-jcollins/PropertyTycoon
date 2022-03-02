package model.mainGame;

import model.Player.Player;
import model.board.*;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.Scanner;

public class mainGame {
    public Scanner scanner;
    public Board gameBoard;
    public Player[] players;
    public int currentPlayer = 0;
    public mainGame() {
        players = new Player[]{new Player()};
        this.scanner = new Scanner(System.in);
        try {
            gameBoard = new Board();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args){
        System.out.println("main");
        mainGame game = new mainGame();
        int gCount = 15; //the number of turn we can play (testing not main game)
        while (gCount > 0){
            gCount--;
            for (Player player: game.players) {
                game.turn(player);
            }
        }

    }

    public void interact(Player p){
    Tile tile = gameBoard.getTile(p.getPos());
    String tileType = tile.getTileType();
    if (tileType.equals("PropertyTile")){
        interactWithProperty(p);
    }
    if(tileType.equals("Asset")){
        interactWithAsset(p);
        }
    }

    public void interactWithProperty(Player p) {
        PropertyTile tile = (PropertyTile) gameBoard.getTile(p.getPos());
        int owner = tile.getOwner();
        if (owner == -1) {
            boolean toBuy = askPlayer("wanna buy "+ gameBoard.getTile(p.getPos()).getName()+"?");
            if (toBuy){
                p.pay(tile.getCost()*-1);
                tile.setOwner(currentPlayer);
            }
        } else {
            p.pay((tile.getRent()) * -1);
            players[owner].pay(tile.getRent());
        }
    }

    public void interactWithAsset(Player p){
        AssetTile tile = (AssetTile) gameBoard.getTile(p.getPos());
        int owner = tile.getOwner();
        if (owner == -1){
            System.out.println("wanna buy?");
        }else{
            p.pay((tile.getRent())*-1);
            players[owner].pay(tile.getRent());
        }
    }

    public void turn(Player p){
        int dCount = 0;
        askPlayer("type yes to move");
        boolean doub = p.move(); //This is a boolean for weather there was a double roll
        interact(p);
        System.out.println(p.getPos());
        while (doub && dCount < 3){
            dCount ++;
            askPlayer("type yes to move");
            doub = p.move();
            interact(p);
            System.out.println(p.getPos());
            System.out.println(gameBoard.getTile(p.getPos()));
        }
        if (dCount > 3){
            ;
            System.out.println("Go to jail!");
            //bad things happen
        }
    }
    public boolean askPlayer(String message){
        String playerDecision = "";
        System.out.println(message);
        while(!playerDecision.equals("yes") && !playerDecision.equals("no")){
            playerDecision = scanner.nextLine();
        }
        return (playerDecision.equals("yes"));


    }

}

