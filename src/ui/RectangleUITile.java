package ui;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class RectangleUITile extends Group {

    protected static final int STROKEWIDTH = 4;
    protected static final Color BACKCOLOR = new Color(0.75,0.86,0.68,1.0);
    protected static final Color STROKECOLOR = Color.BLACK;

    public RectangleUITile(int x, int y, double width, double height, double angle) {
        Rectangle back = new Rectangle();
        back.setX(x);
        back.setY(y);
        back.setWidth(width);
        back.setHeight(height);
        back.setFill(BACKCOLOR);
        back.setStroke(STROKECOLOR);
        back.setStrokeWidth(STROKEWIDTH);
        getChildren().add(back);

        Rotate rotate = new Rotate();
        rotate.setPivotX(x);
        rotate.setPivotY(y);
        rotate.setAngle(angle);
        this.getTransforms().add(rotate);
    }


}
