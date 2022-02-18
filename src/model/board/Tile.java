package model.board;

import ui.PropertyUITile;
import ui.UITile;

public class Tile {

    private String name;

    /**
     * Constructor assigns attributes from parameter
     * @param name value for tile's name
     */
    public Tile(String name) {
        this.name = name;
    }


    /**
     * Getter for tile's name
     * @return tile's name
     */
    public String getName() {
        return name;
    }

    public UITile getUITile(int x, int y, int width, int height, int angle) {
        return new UITile(x, y, width, height, angle);
    }

    @Override
    public String toString() {
        return "Tile{" +
                "name='" + name + '\'' +
                '}';
    }
}
