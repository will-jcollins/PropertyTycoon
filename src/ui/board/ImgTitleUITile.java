package ui.board;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.board.ActionTile;

public class ImgTitleUITile extends UITile {

    protected static final Image OPPURTUNITYIMG = new Image("file:assets/images/opportunity.png");
    protected static final Image POTLUCKIMG = new Image("file:assets/images/potluck.png");

    public ImgTitleUITile(int x, int y, double width, double height, double angle, ActionTile action) {
        super(x, y, width, height, angle);

        // Replace space chars with newline
        String name = formatText(action.getName()).toUpperCase();

        // Draw name
        Text title = new Text(name);
        title.setX(x);
        title.setY(y + 1.7 * (height / 10));
        title.setWrappingWidth(width);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setLineSpacing(-7.5);
        title.setFont(TITLEFONT);
        getChildren().add(title);

        ImageView img;

        switch (action.getAction().getActCode()) {
            case OPPORTUNITY:
                img = new ImageView(OPPURTUNITYIMG);
                break;
            case POTLUCK:
                img = new ImageView(POTLUCKIMG);
                break;
            default:
                throw new IllegalStateException("Action not enumerated in UI");
        }

        img.setX(x + (width - width / 1.4) / 2);
        img.setY(y + 1.25 * height / 3.5);
        img.setFitWidth(width / 1.4);
        img.setFitHeight(width / 1.4);
        getChildren().add(img);
    }
}
