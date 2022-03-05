package ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.board.PropertyTile;

public class PropertyUITile extends UITile {

    public PropertyUITile(int x, int y, double width, double height, double angle, PropertyTile property) {
        super(x, y, width, height, angle);

        Rectangle group = new Rectangle();
        group.setX(x);
        group.setY(y);
        group.setWidth(width);
        group.setHeight(height / 4);
        group.setFill(property.getStreet().getColor());
        group.setStroke(STROKECOLOR);
        group.setStrokeWidth(STROKEWIDTH);
        getChildren().add(group);

        // Replace space chars with newline
        String name = formatText(property.getName());

        Text title = new Text(name.toUpperCase());
        title.setX(x);
        title.setY(y + 1.7 * (height / 4));
        title.setWrappingWidth(width);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setLineSpacing(-7.5);
        title.setFont(TITLEFONT);
        title.setStroke(Color.BLACK);
        title.setStrokeWidth(0.4);
        getChildren().add(title);

        Text caption = new Text("M" + property.getCost());
        caption.setX(x);
        caption.setY(y + height - (height / 12));
        caption.setWrappingWidth(width);
        caption.setTextAlignment(TextAlignment.CENTER);
        caption.setFont(CAPTIONFONT);
        caption.setStroke(Color.BLACK);
        caption.setStrokeWidth(0.4);
        getChildren().add(caption);
    }
}