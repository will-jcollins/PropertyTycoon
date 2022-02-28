package model.board;

public class Tile {
    private String tileType = "Tile";
    private String name;

    /**
     * Constructor assigns attributes from parameter
     * @param name value for tile's name
     */
    public Tile(String name) {
        this.name = name;
        this.tileType =  "Tile";
    }


    /**
     * Getter for tile's name
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

    public String getTileType(){
       return tileType;
    }
}
