package ui.board;

import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Player.HumanPlayer;
import model.Player.Player;
import model.board.*;

import java.util.HashMap;

public class UIBoard extends BorderPane {

    private static final int HEIGHT = 810;
    private static final int WIDTH = 810;

    private final int TILESPERSIDE;
    private final int TILEHEIGHT;
    private final int TILEWIDTH;

    public static final String[] TOKEN_IMG = {"file:assets/images/tax.png"};
    public final int TOKEN_SIZE;

    // Color constants
    private static final Color BACKCOLOR = new Color(0.75,0.86,0.68,1.0);

    private Group tiles;
    private int[][] positions;

    private HashMap<Player,ImageView> players = new HashMap<>();

    public UIBoard(Board board) {
        super();

        // Not possible to draw a monopoly board with tile count that isn't a factor of 4
        if (board.SIZE % 4 != 0) {
            throw new IllegalArgumentException("No of tiles on board must be divisible by 4");
        }

        // Calculate geometry constants
        TILESPERSIDE = (Board.SIZE - 4) / 4;
        TILEHEIGHT = HEIGHT / 8;
        TILEWIDTH = (WIDTH - (2 * TILEHEIGHT)) / TILESPERSIDE;
        TOKEN_SIZE = TILEWIDTH;

        drawBoard(board);

        setCache(true);
        setCacheHint(CacheHint.SCALE_AND_ROTATE);
    }

    public void drawBoard(Board board) {
        final int[] xDirections = new int[] {-1, 0, 1, 0};
        final int[] yDirections = new int[] {0, -1, 0, 1};
        // Tracks position where elements are placed on the screen
        int x = WIDTH - TILEHEIGHT, y = HEIGHT - TILEHEIGHT;
        int angle = 0;
        positions = new int[Board.SIZE][2];

        // Root node all board UI elements are placed onto
        tiles = new Group();

        // Draw centre of the board
        Rectangle background = new Rectangle();
        background.setX(TILEHEIGHT);
        background.setY(TILEHEIGHT);
        background.setWidth(WIDTH - (2 * TILEHEIGHT));
        background.setHeight(HEIGHT - (2 * TILEHEIGHT));
        background.setFill(BACKCOLOR);
        tiles.getChildren().add(background);

        // Starting in the bottom right corner, draw each row
        for (int i = 0; i < 4; i++) {
            // Draw corner tile from board info
            tiles.getChildren().add(board.getTile(Board.SIZE / 4 * i).getUITile(x, y, TILEHEIGHT, TILEHEIGHT, angle));

            for (int j = 1; j <= TILESPERSIDE; j++) {
                int pos = i * Board.SIZE / 4 + j;
                positions[pos][0] = x;
                positions[pos][1] = y;

                // Update point where elements are added to the screen
                x += TILEWIDTH * xDirections[i];
                y += TILEWIDTH * yDirections[i];

                tiles.getChildren().add(board.getTile(pos).getUITile(x, y, TILEWIDTH, TILEHEIGHT, angle));
            }

            // Update angle tiles are placed
            angle = (angle + 90) % 360;
        }

        drawPlayer(new HumanPlayer(0,"Will"));

        setCenter(tiles);
    }

    public void drawPlayer(Player p) {
        if (!players.containsKey(p)) {
            ImageView token = new ImageView(TOKEN_IMG[0]);
            token.setFitHeight(TOKEN_SIZE);
            token.setFitWidth(TOKEN_SIZE);
            token.setX(709);
            token.setY(709);
            tiles.getChildren().add(token);
            players.put(p, token);
        } else {
            players.get(p).setX(positions[p.getPos()][0]);
            players.get(p).setY(positions[p.getPos()][1]);
        }
    }
}
