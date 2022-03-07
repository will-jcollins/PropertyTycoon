package model.board;

import model.actions.Action;
import model.actions.Actionable;
import ui.board.ImgCostUITile;
import ui.board.ImgTitleUITile;
import ui.board.ImgUITile;
import ui.board.UITile;

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
    public UITile getUITile(int x, int y, int width, int height, int angle) {
        switch (action.getActCode()) {
            case OPPORTUNITY:
            case POTLUCK:
                return new ImgTitleUITile(x, y, width, height, angle, this);
            case PAYFINE:
                return new ImgCostUITile(x,y,width,height,angle,this);
            case FINEPAY:
            case JAIL:
                return new ImgUITile(x,y,width,height,angle,this);
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
