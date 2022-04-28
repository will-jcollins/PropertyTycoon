package model.board;

import javafx.scene.image.Image;
import model.actions.Action;
import model.actions.Actionable;
import ui.board.ImgCostUITile;
import ui.board.ImgTitleUITile;
import ui.board.ImgUITile;
import ui.board.UITile;

/**
 * Class for implementing action tiles
 * Action tiles have an Action associated with them that can trigger logic on the game
 */
public class ActionTile extends Tile implements Actionable {
    /**
     * Connect png image files into variables
     */
    private static final String OPPURTUNITYIMG = "file:assets/images/opportunity.png";
    private static final String POTLUCKIMG = "file:assets/images/potluck.png";
    private static final String FINEIMGPATH = "file:assets/images/tax.png";
    private static final String GOTOJAILIMG = "file:assets/images/gotojail.png";
    private static final String PARKINGIMG = "file:assets/images/parking.png";

    private Action action;

    /**
     * Constructor of ActionTile class
     * @param name name of action
     */
    public ActionTile(String name, Action action) {

        super(name);
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    /**
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param width width value
     * @param height height value
     * @param angle angle in which the image is positioned
     * @return the appropriate image into the appropriate space in the board
     */
    @Override
    public UITile getUITile(int x, int y, int width, int height, int angle) {
        switch (action.getActCode()) {
            case OPPORTUNITY:
                return new ImgTitleUITile(x, y, width, height, angle, getName(),OPPURTUNITYIMG);
            case POTLUCK:
                return new ImgTitleUITile(x, y, width, height, angle, getName(),POTLUCKIMG);
            case PAYFINE:
                return new ImgCostUITile(x,y,width,height,angle,getName(),action.getVal1(),FINEIMGPATH);
            case FINEPAY:
                return new ImgUITile(x,y,width,height,angle,PARKINGIMG);
            case JAIL:
                return new ImgUITile(x,y,width,height,angle,GOTOJAILIMG);
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
