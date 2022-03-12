package ui.board;

import javafx.animation.TranslateTransition;
import javafx.geometry.Bounds;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.Player.Player;
import model.board.*;

import java.util.ArrayList;
import java.util.HashMap;

public class UIBoard extends Group {

    private static final int HEIGHT = 810;
    private static final int WIDTH = 810;

    private final int TILESPERSIDE;
    private final int TILE_HEIGHT;
    private final int TILE_WIDTH;

    public static final String[] TOKEN_IMG = {"file:assets/images/tax.png"};
    public final int TOKEN_SIZE;

    // Color constants
    private static final Color BACKCOLOR = new Color(0.75,0.86,0.68,1.0);

    private HashMap<Player, Circle> tokens;
    private UITile[] tiles;
    private Board board;

    public UIBoard(Board board) {
        super();

        this.board = board;

        this.tiles = new UITile[Board.SIZE];

        // Not possible to draw a monopoly board with tile count that isn't a factor of 4
        if (Board.SIZE % 4 != 0) {
            throw new IllegalArgumentException("No of tiles on board must be divisible by 4");
        }

        // Calculate geometry constants
        TILESPERSIDE = (Board.SIZE - 4) / 4;
        TILE_HEIGHT = HEIGHT / 8;
        TILE_WIDTH = (WIDTH - (2 * TILE_HEIGHT)) / TILESPERSIDE;
        TOKEN_SIZE = TILE_WIDTH;

        drawBoard(this.board);

        setCache(true);
    }

    public void drawBoard(Board board) {
        final int[] xDirections = new int[] {-1, 0, 1, 0};
        final int[] yDirections = new int[] {0, -1, 0, 1};
        // Tracks position where elements are placed on the screen
        int x = WIDTH - TILE_HEIGHT, y = HEIGHT - TILE_HEIGHT;
        int angle = 0;

        // Draw centre of the board
        Rectangle background = new Rectangle();
        background.setX(TILE_HEIGHT);
        background.setY(TILE_HEIGHT);
        background.setWidth(WIDTH - (2 * TILE_HEIGHT));
        background.setHeight(HEIGHT - (2 * TILE_HEIGHT));
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
    }

    public int getXTilePos(int pos) {
        return (tiles[pos].getMinX() + tiles[pos].getMaxX()) / 2;
    }

    public int getYTilePos(int pos) {
        return (tiles[pos].getMinY() + tiles[pos].getMaxY()) / 2;
    }
}
