package model.board;

import model.actions.Action;
import model.actions.Actionable;
import ui.ImgTitleUITile;
import ui.UITile;

public class ActionTile extends Tile implements Actionable {

    private Action action;

    public ActionTile(String name, Action action) {
        super(name);

        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    @Override
    public UITile getUITile(int x, int y, int width, int height, int angle) {
        switch (action.getActCode()) {
            case OPPORTUNITY:
            case POTLUCK:
                return new ImgTitleUITile(x, y, width, height, angle, this);
            default:
                return super.getUITile(x, y, width, height, angle);
        }
    }

    @Override
    public String toString() {
        return "ActionTile{" +
                "name=" + getName() +
                ", action=" + action +
                '}';
    }
}
