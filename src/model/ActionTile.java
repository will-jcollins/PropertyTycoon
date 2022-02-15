package model;

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
    public String toString() {
        return "ActionTile{" +
                "name=" + getName() +
                ", action=" + action +
                '}';
    }
}
