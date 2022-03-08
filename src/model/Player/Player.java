package model.Player;

import model.board.Board;

import java.util.Objects;
import java.util.Random;

public abstract class Player {
    private final int id;
    private final String name;
    private int pos = 0; // the player position
    private int prevPos = 0;
    private int money = 1500;

    public Player(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getPos(){
        return pos;
    }

    public void setPos(int newPos) {
        prevPos = pos;
        pos = newPos;
    }

    public void changePos(int newPos) {
        setPos((pos + newPos) % Board.SIZE);
    }

    public int getMoney() {
        return money;
    }

    public void pay(int amount) {
        money += amount;
    }

    public int getId() {
        return id;
    }

    public int getPrevPos() {
        return prevPos;
    }

    public String getName() {
        return name;
    }

    public boolean askPlayer(String message) {
        System.out.println(message);
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id && pos == player.pos && money == player.money;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", pos=" + pos +
                ", money=" + money +
                '}';
    }
}
