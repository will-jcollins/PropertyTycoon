package model.board;

import ui.ImgUITile;
import ui.UITile;

public abstract class Tile {
    private final String name;

    /**
     * Constructor assigns attributes from parameter
     * @param name value for tile's name
     */
    public Tile(String name) {
        this.name = name;
    }


    /**
     * Returns tile's name
     * @return tile's name
     */
    public String getName() {
        return name;
    }

    public UITile getUITile(int x, int y, int width, int height, int angle) {
        switch (name) {
            case "Go":
            case "Jail":
                return new ImgUITile(x,y,width,height,angle, this);
            default:
                return new UITile(x, y, width, height, angle);
        }
    }

    @Override
    public String toString() {
        return "Tile{" +
                "name='" + name + '\'' +
                '}';
    }
}

