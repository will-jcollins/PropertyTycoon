package ui.board;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static ui.board.UITile.STROKEWIDTH;

public class OwnerRibbon extends Polygon {

    public OwnerRibbon(double x, double y, double width, double height) {
        super();
        getPoints().addAll(new Double[]{
                x, y,
                x, y + (height - height / 4),
                x, y + height,
                x + (width / 2), y + (height - height / 4),
                x + width, y + height,
                x + width, y + (height - height / 4),
                x + width, y
        });
        setStroke(Color.BLACK);
        setStrokeWidth(STROKEWIDTH / 2);
    }
}
