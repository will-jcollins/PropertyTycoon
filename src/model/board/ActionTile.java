package model.board;

import model.actions.Action;
import model.actions.Actionable;

public class ActionTile extends Tile implements Actionable {

    private Action action;
    private String tileType;
    public ActionTile(String name, Action action) {

        super(name);
        this.tileType = "action";
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    @Override
    public String toString() {
        return "ActionTile{" +
                "name=" + getName() +
                ", action=" + action +
                '}';
    }

}
