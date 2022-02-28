package model.board;

import model.actions.Action;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;


public class Board {
    private static final String DATAPATH = System.getProperty("user.dir") + "/assets/jsons/BoardData.json";
    public static final int SIZE = 40;
    private Tile[] tiles;

    /**
     * Constructs a Board object based on the information stored in the JSON file at DATAPATH
     *
     * @throws IOException if the JSON file does not exist at DATAPATH
     * @author Will Collins
     */
    public Board() throws IOException {
        // Initialise and populate array with empty tiles (avoid null pointer exception)
        tiles = new Tile[SIZE];
        Arrays.fill(tiles, new Tile("Empty"));

        // Open file input stream and read characters into string builder
        FileInputStream fis = new FileInputStream(DATAPATH);
        StringBuilder sb = new StringBuilder();

        int content;
        while ((content = fis.read()) != -1) {
            sb.append((char)content);
        }

        fis.close();

        // Parse json file
        JSONArray objects = new JSONArray(sb.toString());

        // Populate board with Tile objects using information from JSON
        for (int i = 0; i < objects.length(); i++) {
            JSONObject obj = objects.getJSONObject(i);

            String type = obj.getString("type");

            Tile tile;

            switch (type) {
                case "property":
                    // Construct PropertyTile
                    tile = new PropertyTile(
                            obj.getString("name"),
                            StreetColor.fromString(obj.getString("group")),
                            obj.getInt("cost"),
                            new int[] {obj.getInt("rent0"),obj.getInt("rent1"),obj.getInt("rent2"),obj.getInt("rent3"),obj.getInt("rent4"),obj.getInt("rent5")}
                    );

                    tiles[obj.getInt("position")] = tile;
                    break;
                case "action":
                    // Construct ActionTile
                    Action act = new Action(obj.getString("action"));
                    tile = new ActionTile(
                            obj.getString("name"),
                            act
                    );
                    break;
                case "utility":
                    // Construct AssetTile object for utility
                    tile = new AssetTile(
                            obj.getString("name"),
                            AssetType.UTILITY,
                            obj.getInt("cost")
                    );
                    break;
                case "station":
                    // Construct AssetTile object for utility
                    tile = new AssetTile(
                            obj.getString("name"),
                            AssetType.STATION,
                            obj.getInt("cost")
                    );
                    break;
                case "notype":
                    tile = new Tile(obj.getString("name"));
                    break;
                default: throw new IllegalStateException("Invalid JSON data from \"" + DATAPATH + "\"");
            }
            tiles[obj.getInt("position")] = tile;
        }
    }

    public void setTile(int i, Tile tile) {
        tiles[i] = tile;
    }

    /**
     * Returns the tile at index i in the board
     *
     * @param i index
     * @return tile indexed
     * @author Will Collins
     */
    public Tile getTile(int i) {
        return tiles[i];
    }

    /**
     * Returns the first board tile with the name entered
     *
     * @param name name attribute of tile searching for
     * @return tile with name equal to param, null if there is no tile with that name
     * @author Will Collins
     */
    public Tile getTile(String name) {
        for (Tile tile : tiles) {
            if (tile.getName().equals(name)) {
                return tile;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    public static void main(String[] args) {
        try {
            Board b = new Board();
            System.out.println(b);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
