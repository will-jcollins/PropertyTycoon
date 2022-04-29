package model.board;

import model.actions.ActCode;
import model.actions.Action;
import model.actions.Actionable;

/**
 * Class for describing cards with an associated action
 */
public class Card implements Actionable {
    private String text;
    private Action action;

    /**
     * Constructor
     * @param text description of action shown to player
     * @param action associated action that should happen after card is drawn
     */
    public Card(String text, Action action){
        this.text = text;
        this.action = action;
    }

    @Override
    public String toString() {
        return text;
    }

    /**
     * Getter for description
     * @return description
     */
    public String getText() {
        return text;
    }

    @Override
    public Action getAction() {
        return action;
    }
}
