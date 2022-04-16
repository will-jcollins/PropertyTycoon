package ui.menu;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import ui.Sizes;
/**
 * Class responsible for drawing an arrow
 */
public class Arrow extends Group {
    /**
     * Arrow class Constructor
     * @param width width value
     * @param height height value
     */
    public Arrow(double width, double height) {
        super();

        Line line1 = new Line();
        line1.setStartX(0);
        line1.setStartY(height / 2);
        line1.setEndX(width);
        line1.setEndY(height / 2);
        line1.setStroke(Color.BLACK);
        line1.setStrokeWidth(Sizes.getSmallStroke());
        getChildren().add(line1);

        Line line2 = new Line();
        line2.setStartX(width);
        line2.setStartY(height / 2);
        line2.setEndX(width - width / 8);
        line2.setEndY(0);
        line2.setStroke(Color.BLACK);
        line2.setStrokeWidth(Sizes.getSmallStroke());
        getChildren().add(line2);
    }
}
