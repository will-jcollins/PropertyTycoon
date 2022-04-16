package model.board;

import model.Player.Player;
import model.actions.Action;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
    public Board() {
        // Initialise and populate array with empty tiles (avoid null pointer exceptions)
        tiles = new Tile[SIZE];

        StringBuilder sb = new StringBuilder();

        // Open file input stream and read characters into string builder
        try (FileInputStream fis = new FileInputStream(DATAPATH)) {

            int content;
            while ((content = fis.read()) != -1) {
                sb.append((char) content);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                            obj.getInt("cost"),
                            new int[] {obj.getInt("rent0"),obj.getInt("rent1"),obj.getInt("rent2"),obj.getInt("rent3"),obj.getInt("rent4"),obj.getInt("rent5")},
                            Street.fromString(obj.getString("group"))
                    );

                    tiles[obj.getInt("position")] = tile;
                    break;
                case "action":
                    // Construct ActionTile
                    Action action = new Action(obj.getString("action"));
                    tile = new ActionTile(
                            obj.getString("name"),
                            action
                    );
                    break;
                case "utility":
                    // Construct AssetTile object for utility
                    tile = new UtilityTile(
                            obj.getString("name"),
                            obj.getInt("cost")
                    );
                    break;
                case "station":
                    // Construct AssetTile object for utility
                    tile = new StationTile(
                            obj.getString("name"),
                            obj.getInt("cost")
                    );
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

    public ArrayList<PropertyTile> getStreetTiles(Street street) {
        ArrayList<PropertyTile> out = new ArrayList<>();
        for (Tile t : tiles) {
            if (t instanceof PropertyTile) {
                PropertyTile prop = (PropertyTile) t;
                if (prop.getStreet() == street) {
                    out.add(prop);
                }
            }
        }
        return out;
    }

    public void freeProperties(Player p) {
        // Removes ownership from every property with player p
        for (Tile tile : tiles) {
            if (tile instanceof BuyableTile) {
                BuyableTile buyable = (BuyableTile) tile;

                if (buyable.getOwner() == p) {
                    buyable.setOwner(null);
                }
            }
        }
    }

    /**
     * Determines number of houses a player owns
     * @param p player to analyse
     * @return number of houses owned by player p
     */
    public int getNoHouses(Player p) {
        int houses = 0;

        for (Tile tile : tiles) {
            if (tile instanceof PropertyTile) {
                houses += Math.max(((PropertyTile) tile).getNoHouses(),PropertyTile.MAX_NO_HOUSES-1);
            }
        }

        return houses;
    }

    /**
     * Determines number of hotels a player owns
     * @param p player to analyse
     * @return number of hotels owned by player p
     */
    public int getNoHotels(Player p) {
        int hotels = 0;

        for (Tile tile : tiles) {
            if (tile instanceof PropertyTile) {
                if (((PropertyTile) tile).getNoHouses() == PropertyTile.MAX_NO_HOUSES) {
                    hotels += 1;
                }
            }
        }

        return hotels;
    }

    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }
}
