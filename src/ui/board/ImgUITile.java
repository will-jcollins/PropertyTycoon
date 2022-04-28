package ui.board;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.board.ActionTile;
import model.board.Tile;

/**
 * Displays a UITile which is filled by an image
 */
public class ImgUITile extends UITile {

    public ImgUITile(int x, int y, double width, double height, double angle, String imgPath) {
        super(x, y, width, height, angle);

        ImageView img = new ImageView(new Image(imgPath,width,height,false,true));
        img.setX(x);
        img.setY(y);
        img.setFitWidth(width);
        img.setFitHeight(height);
        getChildren().add(img);
    }
}
