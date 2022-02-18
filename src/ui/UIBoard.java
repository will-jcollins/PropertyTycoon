package ui;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import model.actions.Action;
import model.board.*;

public class UIBoard extends BorderPane {

    private static final int HEIGHT = 810;
    private static final int WIDTH = 810;

    private final int TILESPERSIDE;
    private final int TILEHEIGHT;
    private final int TILEWIDTH;

    // Color constants
    private static final Color BACKCOLOR = new Color(0.75,0.86,0.68,1.0);

    Group tiles;

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

        drawBoard(board);
    }

    public void drawBoard(Board board) {

        final int[] xDirections = new int[] {-1, 0, 1, 0};
        final int[] yDirections = new int[] {0, -1, 0, 1};
        // Tracks position where elements are placed on the screen
        int x = WIDTH - TILEHEIGHT, y = HEIGHT - TILEHEIGHT;
        int angle = 0;

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

        // Draw Jeeves Stobbs logo on the centre of the board (just for funsies :P)
        ImageView img = new ImageView(new Image("file:assets/images/corpLogo.png"));
        img.setX(WIDTH / 2 - (WIDTH/ 6));
        img.setY(HEIGHT / 2 - (WIDTH / 6));
        img.setFitWidth(WIDTH / 3);
        img.setFitHeight(HEIGHT / 3);
        img.setOpacity(0.5);
        tiles.getChildren().add(img);

        // Starting in the bottom right corner, draw each row
        for (int i = 0; i < 4; i++) {
            System.out.println(i);
            // Draw corner tile from board info
            drawSquareTile(x, y, angle, board.getTile(i * TILESPERSIDE));

            for (int j = 0; j < TILESPERSIDE; j++) {
                // Update point where elements are added to the screen
                x += TILEWIDTH * xDirections[i];
                y += TILEWIDTH * yDirections[i];

                // Draw rectangular tile from board info
                // VERY BAD USING INSTANCEOF WILL THINK OF A BETTER WAY OF DOING THIS
                if (board.getTile(j + i * TILESPERSIDE) instanceof PropertyTile) {
                    drawTile(x, y, angle, (PropertyTile) board.getTile(j + i * TILESPERSIDE));
                } else {
                    drawTile(x, y, angle, board.getTile(j + i * TILESPERSIDE));
                }
            }

            // Update angle tiles are placed
            angle = (angle + 90) % 360;
        }

        setCenter(tiles);
    }

    private void drawTile(int x, int y, int angle, Tile tile) {
        RectangleUITile uiTile = new RectangleUITile(x, y, TILEWIDTH, TILEHEIGHT, angle);
        tiles.getChildren().add(uiTile);
    }

    private void drawTile(int x, int y, int angle, PropertyTile propTile) {
        PropertyUITile uiTile = new PropertyUITile(x, y, TILEWIDTH, TILEHEIGHT, angle, propTile);
        tiles.getChildren().add(uiTile);
    }

    private void drawSquareTile(int x, int y, int angle, Tile tile) {
        SquareUITile uiTile = new SquareUITile(x, y, TILEHEIGHT, angle);
        tiles.getChildren().add(uiTile);
    }
}
