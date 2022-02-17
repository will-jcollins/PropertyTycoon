package ui;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import model.board.Board;

public class UIBoard extends BorderPane {

    Group tiles;

    public UIBoard(Board board) {
        super();

        // Temporary testing variables
        final int NOTILES = 40;
        if (NOTILES % 4 != 0) {
            throw new IllegalArgumentException("# tiles on board must be divisible by four");
        }

        // Determine screen bounds
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double height = 810, width = 810;
        // Create geometry variables
        final int TILESPERSIDE = (NOTILES - 4) / 4;
        final int TILEHEIGHT = (int) height / 8;
        final int TILEWIDTH = (int) (width - (2 * TILEHEIGHT)) / TILESPERSIDE;
        int[] cursor = new int[]{TILEHEIGHT, TILEHEIGHT};

        // Root node all children are placed onto
        tiles = new Group();

        final Color BACKCOLOR = new Color(0.75,0.86,0.68,1.0);
        Rectangle background = new Rectangle();
        background.setX(TILEHEIGHT);
        background.setY(TILEHEIGHT);
        background.setWidth(width - (2 * TILEHEIGHT));
        background.setHeight(height - (2 * TILEHEIGHT));
        background.setFill(BACKCOLOR);
        tiles.getChildren().add(background);

        SquareUITile s = new SquareUITile(cursor[0], cursor[1],TILEHEIGHT,180);
        tiles.getChildren().add(s);

        cursor[0] += TILEWIDTH;

        for (int i = 0; i < TILESPERSIDE; i++) {
            RectangleUITile r = new PropertyUITile(cursor[0],cursor[1],TILEWIDTH,TILEHEIGHT, 180, StreetColor.BROWN, "MONTREAL", 10);
            tiles.getChildren().add(r);

            cursor[0] += TILEWIDTH;
        }

        cursor[0] += TILEHEIGHT - TILEWIDTH;

        s = new SquareUITile(cursor[0], cursor[1],TILEHEIGHT,180);
        tiles.getChildren().add(s);

        cursor[0] -= TILEHEIGHT;
        cursor[1] += TILEWIDTH;

        for (int i = 0; i < TILESPERSIDE; i++) {
            RectangleUITile r = new PropertyUITile(cursor[0],cursor[1],TILEWIDTH,TILEHEIGHT, 270, StreetColor.BROWN, "MONTREAL", 10);
            tiles.getChildren().add(r);

            cursor[1] += TILEWIDTH;
        }

        cursor[1] -= TILEWIDTH;

        s = new SquareUITile(cursor[0], cursor[1],TILEHEIGHT,0);
        tiles.getChildren().add(s);

        for (int i = 0; i < TILESPERSIDE; i++) {
            cursor[0] -= TILEWIDTH;

            RectangleUITile r = new PropertyUITile(cursor[0],cursor[1],TILEWIDTH,TILEHEIGHT, 0, StreetColor.BROWN, "MONTREAL", 10);
            tiles.getChildren().add(r);
        }

        cursor[0] -= TILEHEIGHT;

        s = new SquareUITile(cursor[0], cursor[1],TILEHEIGHT,0);
        tiles.getChildren().add(s);

        cursor[0] += TILEHEIGHT;

        for (int i = 0; i < TILESPERSIDE; i++) {
            cursor[1] -= TILEWIDTH;

            RectangleUITile r = new PropertyUITile(cursor[0],cursor[1],TILEWIDTH,TILEHEIGHT, 90, StreetColor.BLUE, "MONTREAL HALL", 10);
            tiles.getChildren().add(r);
        }

//        ImageView img = new ImageView(new Image("file:assets/images/corpLogo.png"));
//        img.setX(width / 2 - (width / 6));
//        img.setY(height / 2 - (height / 6));
//        img.setFitWidth(width / 3);
//        img.setFitHeight(height / 3);
//        img.setOpacity(0.5);
//        tiles.getChildren().add(img);

        setCenter(tiles);
    }
}
