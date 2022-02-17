package ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class PropertyUITile extends RectangleUITile {

    protected static final Font TITLEFONT = Font.loadFont("file:assets/fonts/Kabel.ttf", 9);
    protected static final Font COSTFONT = Font.loadFont("file:assets/fonts/Kabel.ttf", 7);

    public PropertyUITile(int x, int y, double width, double height, double angle, StreetColor streetGroup, String name, int cost) {
        super(x, y, width, height, angle);

        Rectangle group = new Rectangle();
        group.setX(x);
        group.setY(y);
        group.setWidth(width);
        group.setHeight(height / 4);
        group.setFill(streetGroup.getColor());
        group.setStroke(STROKECOLOR);
        group.setStrokeWidth(STROKEWIDTH);
        getChildren().add(group);

        // Replace space chars with newline
        name.replaceAll("( )+", "\n");

        Text title = new Text(name);
        title.setX(x);
        title.setY(y + 1.7 * (height / 4));
        title.setWrappingWidth(width);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setFont(TITLEFONT);
        title.setStroke(Color.BLACK);
        title.setStrokeWidth(0.4);
        getChildren().add(title);

        Text caption = new Text("M" + cost);
        caption.setX(x);
        caption.setY(y + height - (height / 8));
        caption.setWrappingWidth(width);
        caption.setTextAlignment(TextAlignment.CENTER);
        caption.setFont(COSTFONT);
        caption.setStroke(Color.BLACK);
        caption.setStrokeWidth(0.4);
        getChildren().add(caption);
    }
}
