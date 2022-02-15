package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class Board {
    private static final String DATAPATH = System.getProperty("user.dir") + "/assets/jsons/BoardData.json";
    private static final int GOREWARD = 200;
    public static final int SIZE = 40;

    private static final int[] UTILITYFACTORS = new int[] {4,10};

    private Tile[] tiles;

    private Dice dice = new Dice(2,6);

    private Player[] players;
    private int currentPlayer = 0;

    public Board(int humanPlayers, int AIPlayers) throws IOException {
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
                default: throw new IllegalStateException("Invalid JSON data from \"" + DATAPATH + "\"");
            }
            tiles[obj.getInt("position")] = tile;
        }

        players = new Player[humanPlayers + AIPlayers];

        for (int i = 0; i < humanPlayers; i++) {
            // TODO: insert human players into array
        }

        for (int i = 0; i < AIPlayers; i++) {
            // TODO: insert AI players into array
        }
    }

    public void takeTurn() {
        dice.roll();

        int prevPos = players[currentPlayer].getPos();
        players[currentPlayer].movePlayer(dice.getRollTotal());

        if (prevPos > players[currentPlayer].getPos()) {
            players[currentPlayer].addToCurrency(GOREWARD);
        }

        if (0 < dice.getMatches() && dice.getMatches() < 3) {
            takeTurn();
        }
        else {
            if (3 <= dice.getMatches()) {
                executeActionable(new Action("JAIL"));
            }

            // Update currentPlayer to the next player
            currentPlayer = (currentPlayer + 1) % players.length;

            dice.reset();
        }
    }

    private void actOnTile(PropertyTile tile) {
        // TODO:: implement logic for properties
    }

    private void actOnTile(ActionTile tile) {
        // TODO:: implement logic for actions
    }

    private void actOnTile(AssetTile tile) {
        switch (tile.getType()) {
            case STATION:
                // TODO:: implement logic for stations
                break;
            case UTILITY:
                // TODO:: implement logic for utilities
                break;
            default: throw new IllegalStateException("Impossible position: should not be here");
        }
    }

    private void executeActionable(Actionable action) {

    }

    public Tile getTile(int i) {
        return tiles[i];
    }

    public Tile getTile(String name) {
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].getName().equals(name)) {
                return tiles[i];
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
            Board b = new Board(1, 1);
            System.out.println(b);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
