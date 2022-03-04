package model.Player;

import model.board.Board;

import java.util.Objects;
import java.util.Random;

public class Player {
    private final int id;
    private int pos = 0; // the player position
    private int money = 1500;

    public Player(int id){
        this.id = id;
    }

    public int getPos(){
        return pos;
    }

    public void setPos(int newPos) {
        pos = newPos;
    }

    public void changePos(int newPos) {
        pos = (newPos + newPos) % Board.SIZE;
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
