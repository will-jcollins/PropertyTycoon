package ui;

import javafx.scene.image.ImageView;
import model.board.ActionTile;
import model.board.Tile;

public class ImgUITile extends UITile {

    protected static final String GOIMG = "file:assets/images/go.png";
    protected static final String JAILIMG = "file:assets/images/jail.png";
    protected static final String GOTOJAILIMG = "file:assets/images/gotojail.png";
    protected static final String PARKINGIMG = "file:assets/images/parking.png";

    public ImgUITile(int x, int y, double width, double height, double angle, Tile tile) {
        super(x, y, width, height, angle);

        ImageView img;

        switch (tile.getName()) {
            case "Go":
                img = new ImageView(GOIMG);
                break;
            case "Jail":
                img = new ImageView(JAILIMG);
                break;
            default:
                throw new IllegalStateException("Tile passed does not have an image");
        }

        img.setX(x);
        img.setY(y);
        img.setFitWidth(width);
        img.setFitHeight(height);
        getChildren().add(img);
    }

    public ImgUITile(int x, int y, double width, double height, double angle, ActionTile action) {
        super(x, y, width, height, angle);

        ImageView img;

        switch (action.getAction().getActCode()) {
            case FINEPAY:
                img = new ImageView(PARKINGIMG);
                break;
            case JAIL:
                img = new ImageView(GOTOJAILIMG);
                break;
            default:
                throw new IllegalStateException("Tile passed does not have an image");
        }

        img.setX(x);
        img.setY(y);
        img.setFitWidth(width);
        img.setFitHeight(height);
        getChildren().add(img);

    }
}
