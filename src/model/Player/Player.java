package model.Player;

import java.util.Random;

public class Player {
    private static int IDCount = 0;

    private int id;
    private int pos = 0; // the player position
    private Random dice;
    private int money = 1500;

    public Player(){
        dice = new Random();
        id = IDCount++;
    }

    public boolean move(){
        int roll1 = dice.nextInt(6)+1;
        System.out.println(roll1);
        int roll2 = dice.nextInt(6)+1;
        System.out.println(roll2);
        pos = (pos + (roll1+roll2))%40;

        return roll1 == roll2;
    }

    public int getPos(){
        return pos;
    }

    public void pay(int amount){
        money += amount;
    }

    public int getId() {
        return id;
    }
}
