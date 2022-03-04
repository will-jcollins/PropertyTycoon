package model.board;

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

    @Override
    public String toString() {
        return "Tile{" +
                "name='" + name + '\'' +
                '}';
    }
}

