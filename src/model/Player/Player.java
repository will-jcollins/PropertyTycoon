package model.Player;

import model.board.Board;
import model.game.Game;

import java.util.Objects;
import java.util.Random;

/**
 * Class defining player
 */
public class Player {

    private final int id;
    private final String name;
    private int pos = 0; // Player position
    private int prevPos = 0;
    private int money = 200;
    private int prevMoney = money;
    private int jailCards = 0;
    private boolean passedGo = true; // Whether the player has EVER passed go
    private int turnsInJail = 0;
    private boolean leftJail = false;
    private boolean isAuto;
    private boolean movingBack = false;

    /**
     * Costructor of class player
     * @param id player id
     * @param name player name
     * @param isAuto is the player controlled autonomously
     */
    public Player(int id, String name, boolean isAuto) {
        this.id = id;
        this.name = name;
        this.isAuto = isAuto;
    }

    /**
     * @return return player position
     */
    public int getPos(){
        return pos;
    }

    /**
     * Set player position
     * @param newPos players new position
     */
    public void setPos(int newPos) {
        prevPos = pos;
        pos = newPos;
        if (prevPos > newPos) {
            passedGo = true;
        }
    }

    /**
     * Changes players position
     * @param newPos new position after change
     */
    public void changePos(int newPos) {
        setPos((pos + newPos) % Board.SIZE);
    }

    /**
     * get money before action
     * @return money before action
     */
    public int getPrevMoney() {
        return prevMoney;
    }

    /**
     * get current money
     * @return current money value
     */
    public int getMoney() {
        return money;
    }

    /**
     * Edits money value after paying
     * @param amount amount which has to be paid
     */
    public void pay(int amount) {
        prevMoney = money;
        money -= amount;
    }

    /**
     * Get player id
     * @return player id
     */
    public int getId() {
        return id;
    }

    /**
     * Increase player's number of get out of jail free
     * cards by 1
     */
    public void addJailCard(){
        jailCards += 1;
    }

    /**
     * Decrease player's number of get out of jail free
     * cards by 1
     */
    public void removeJailCard() {
        jailCards -= 1;
    }

    /**
     * Indicates if player has at least 1 jail card
     * @return true if player has at least 1 jail card
     */
    public int getJailCards() {
        return jailCards;
    }

    /**
     * get player position before move
     * @return player starting postion in a turn
     */
    public int getPrevPos() {
        return prevPos;
    }

    /**
     * get player name
     * @return player name
     */
    public String getName() {
        return name;
    }

    /**
     * Checks if player passed starting tile
     * @return true if passed, false otherwise
     */
    public boolean hasPassedGo() {
        return passedGo;
    }

    /**
     * Sets player state to enter jail
     */
    public void sendToJail() {
        turnsInJail = 1;
    }

    public void leaveJail() {
        turnsInJail = 0;
        pos = Game.JAIL_POS;
        leftJail = true;
    }

    public boolean inJail() {
        return turnsInJail > 0;
    }

    public void addTurnInJail() {
        turnsInJail += 1;
    }

    public int getTurnsInJail() {
        return turnsInJail;
    }

    public boolean hasLeftJail() {
        return leftJail;
    }

    public void setLeftJail(boolean leftJail) {
        this.leftJail = leftJail;
    }

    public boolean isAuto() {
        return isAuto;
    }

    public boolean isMovingBack() {
        return movingBack;
    }

    public void setMovingBack(boolean movingBack) {
        this.movingBack = movingBack;
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
