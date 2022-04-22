package ui.board;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.board.ActionTile;
import ui.Sizes;

/**
 * Displays a UITile with an image and a title above it
 * @Author Will Collins
 */
public class ImgTitleUITile extends UITile {

    public ImgTitleUITile(int x, int y, double width, double height, double angle, String name, String imgPath) {
        super(x, y, width, height, angle);

        // Replace space chars with newline
        name = formatText(name).toUpperCase();

        // Draw name
        Text title = new Text(name);
        title.setX(x);
        title.setY(y + 1.7 * (height / 10));
        title.setWrappingWidth(width);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setLineSpacing(Sizes.getLineSpacing());
        title.setFont(TITLEFONT);
        getChildren().add(title);

        ImageView img = new ImageView(new Image(imgPath,width / 1.4,width / 1.4,false,true));
        img.setX(x + (width - width / 1.4) / 2);
        img.setY(y + 1.25 * height / 3.5);
        getChildren().add(img);
    }
}
