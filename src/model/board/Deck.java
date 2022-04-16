package model.board;

import model.actions.ActCode;

public class Deck {
    public Deck(String fileName){

    }

    public Card draw(){
        return new Card("Text",1, ActCode.NOP);
    }
}
