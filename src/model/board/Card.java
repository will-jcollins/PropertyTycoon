package model.board;

import model.actions.ActCode;
import model.actions.Action;
import model.actions.Actionable;

/**
 * Class for describing cards
 */
public class Card implements Actionable {
    private String text;
    private Action action;

    public Card(String text, Action action){
        this.text = text;
        this.action = action;
    }

    @Override
    public String toString() {
        return text;
    }

    public String getText() {
        return text;
    }

    @Override
    public Action getAction() {
        return action;
    }
}
