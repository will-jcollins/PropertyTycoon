package model.board;

import model.actions.ActCode;
import model.actions.Action;

public class Card {
    public String text;
    public int amount;
    public String location = "";
    public ActCode action;

    public Card(String text, int amount, ActCode action){
        this.text = text;
        this.amount = amount;
        this.action = action;
    }

    public Card(String text, String location, ActCode action){
        this.text = text;
        this.location = location;
        this.action = action;
    }
    public Card(String text, int amount, String location, ActCode action){
        this.text = text;
        this.amount = amount;
        this.action = action;
        this.location = location;
    }
}
