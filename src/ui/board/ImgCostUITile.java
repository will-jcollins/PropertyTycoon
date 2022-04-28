package ui.board;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import ui.Sizes;

/**
 * Displays a UITile with an image and a cost below it
 * @Author Will Collins
 */
public class ImgCostUITile extends UITile {

    public ImgCostUITile(int x, int y, double width, double height, double angle, String name, int cost, String imgPath) {
        super(x, y, width, height, angle);

        // Replace space chars with newline
        name = formatText(name);

        // Draw title
        Text title = new Text(name.toUpperCase());
        title.setX(x);
        title.setY(y + 1.7 * (height / 10));
        title.setWrappingWidth(width);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setLineSpacing(Sizes.getLineSpacing());
        title.setFont(TITLEFONT);
        getChildren().add(title);

        // Draw cost
        Text caption = new Text("$" + cost);
        caption.setX(x);
        caption.setY(y + height - (height / 12));
        caption.setWrappingWidth(width);
        caption.setTextAlignment(TextAlignment.CENTER);
        caption.setFont(CAPTIONFONT);
        getChildren().add(caption);

        // Draw image
        ImageView img = new ImageView(new Image(imgPath,width / 1.4, width / 1.4,false,true));
        img.setX(x + (width - width / 1.4) / 2);
        img.setY(y + 1.25 * height / 4);
        getChildren().add(img);
    }
}
