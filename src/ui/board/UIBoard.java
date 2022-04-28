package ui.board;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.Player.Player;
import model.board.*;
import model.game.Game;

import java.util.HashMap;
/**
 * Class which is responsible for building and displaying the game board
 * @Author Will Collins
 */
public class UIBoard extends Group {

    // Geometry constants
    private final int SIZE;
    private final int TILESPERSIDE;
    private final int TILE_HEIGHT;
    private final int TILE_WIDTH;

    // Color constants
    private static final Color BACKCOLOR = new Color(0.75,0.86,0.68,1.0);

    private UITile[] tiles;
    private Board board;

    /**
     * Costructor of class UIBoard
     * @param board Instance of class Board from model.board to represent
     * @param size width and height of displayed board
     */
    public UIBoard(Board board, int size) {
        super();

        this.board = board;

        this.tiles = new UITile[Board.SIZE];

        // Not possible to draw a monopoly board with tile count that isn't a factor of 4
        if (Board.SIZE % 4 != 0) {
            throw new IllegalArgumentException("No of tiles on board must be divisible by 4");
        }

        // Calculate geometry constants
        this.SIZE = size;
        TILESPERSIDE = (Board.SIZE - 4) / 4;
        TILE_HEIGHT = this.SIZE / 8;
        TILE_WIDTH = (this.SIZE - (2 * TILE_HEIGHT)) / TILESPERSIDE;

        drawBoard(this.board);

        setCache(true);
    }

    /**
     * Method responsible for drawing the board
     * @param board instance of class Board from model.board
     */
    public void drawBoard(Board board) {
        // Reset group so board has an empty canvas to draw onto
        getChildren().clear();

        final int[] xDirections = new int[] {-1, 0, 1, 0};
        final int[] yDirections = new int[] {0, -1, 0, 1};
        // Tracks position where elements are placed on the screen
        int x = SIZE - TILE_HEIGHT, y = SIZE - TILE_HEIGHT;
        int angle = 0;

        // Draw centre of the board
        Rectangle background = new Rectangle();
        background.setX(TILE_HEIGHT);
        background.setY(TILE_HEIGHT);
        background.setWidth(SIZE - (2 * TILE_HEIGHT));
        background.setHeight(SIZE - (2 * TILE_HEIGHT));
        background.setFill(BACKCOLOR);
        getChildren().add(background);

        UITile tmpTile;

        // Starting in the bottom right corner, draw each row
        for (int i = 0; i < 4; i++) {
            // Draw corner tile from board info and add to array
            tmpTile = board.getTile(Board.SIZE / 4 * i).getUITile(x, y, TILE_HEIGHT, TILE_HEIGHT, angle);
            getChildren().add(tmpTile);
            tiles[Board.SIZE / 4 * i] = tmpTile;

            for (int j = 1; j <= TILESPERSIDE; j++) {
                int pos = i * Board.SIZE / 4 + j;

                // Update point where elements are added to the screen
                x += TILE_WIDTH * xDirections[i];
                y += TILE_WIDTH * yDirections[i];

                // Draw inner tile from board info and add to array
                tmpTile = board.getTile(pos).getUITile(x, y, TILE_WIDTH, TILE_HEIGHT, angle);
                getChildren().add(tmpTile);
                tiles[pos] = tmpTile;
            }

            // Update angle tiles are placed
            angle = (angle + 90) % 360;
        }

        update();
    }

    /**
     * Returns the x position of tile in local space at position given
     * @param pos position of tile in Board object
     * @return x co-ordinate
     */
    public double getXTilePos(int pos) {
        if (pos != Game.JAIL_POS) {
            return (tiles[pos].getMinX() + tiles[pos].getMaxX()) / 2;
        } else {
            // Position returned when player is in 'just visiting' needs to be offset so they don't appear to be in jail
            return (tiles[pos].getMinX() + tiles[pos].getMaxX()) / 2 - TILE_HEIGHT / 5;
        }

    }

    /**
     * Returns the y position of tile in local space at position given
     * @param pos position of tile in Board object
     * @return y co-ordinate
     */
    public double getYTilePos(int pos) {
        if (pos != Game.JAIL_POS) {
            return (tiles[pos].getMinY() + tiles[pos].getMaxY()) / 2;
        } else {
            return (tiles[pos].getMinY() + tiles[pos].getMaxY()) / 2 + TILE_HEIGHT / 5;
        }
    }

    /**
     * Returns the x position of jail tile
     * @return x co-ordinate
     */
    public double getXJailPos() {
        return (tiles[Game.JAIL_POS].getMinX() + tiles[Game.JAIL_POS].getMaxX()) / 2 + TILE_HEIGHT / 5;
    }

    /**
     * Returns the y position of jail tile
     * @return y co-ordinate
     */
    public double getYJailPos() {
        return (tiles[Game.JAIL_POS].getMinY() + tiles[Game.JAIL_POS].getMaxY()) / 2 - TILE_HEIGHT / 5;
    }

    /**
     * Returns how large the drawn board is
     * @return size
     */
    public int getSize() {
        return SIZE;
    }

    /**
     * Adds ownership ribbons to any tiles that have a new owner
     * For BuyableTiles
     */
    public void update() {
        for (UITile t : tiles) {
            if (t instanceof PropertyUITile) {
                ((PropertyUITile) t).update();
            } else if (t instanceof AssetUITile) {
                ((AssetUITile) t).update();
            }
        }
    }
}
