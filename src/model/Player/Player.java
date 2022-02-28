package model.Player;

import java.util.Random;

public class Player {
    private int pos = 0;
    private Random dice;
    public Player(){
        dice = new Random();
    }
    public boolean move(){
        int roll1 = dice.nextInt(5)+1;
        System.out.println(roll1);
        int roll2 = dice.nextInt(5)+1;
        System.out.println(roll2);
        return roll1 == roll2;
    }

    public int getPos(){
        return pos;
    }
}
