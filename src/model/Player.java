package model;

public class Player {
    private static int staticID = 0;

    private int pos = 0;
    private int id;
    private int currency = 0;


    //this is the main class
    public Player(){
        this.id = staticID++;
    }

    public void movePlayer(int units) {
        pos += units % Board.SIZE;
    }

    public void addToCurrency(int units) {
        currency = Math.max(0, currency + units);
    }

    public int getPos() {
        return pos;
    }
}
