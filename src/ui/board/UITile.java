package ui.board;

import javafx.geometry.Insets;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;

public class UITile extends Group {

    protected static final int STROKEWIDTH = 4;
    protected static final Color BACKCOLOR = new Color(0.75,0.86,0.68,1.0);
    protected static final Color STROKECOLOR = Color.BLACK;

    protected static final Font TITLEFONT = Font.loadFont("file:assets/fonts/Kabel.ttf", 9);
    protected static final Font CAPTIONFONT = Font.loadFont("file:assets/fonts/Kabel.ttf", 7);

    protected static final int MAXCHARSPERLINE = 7;

    private final double width;
    private final double height;
    private final int minX;
    private final int maxX;
    private final int minY;
    private final int maxY;

    public UITile(int x, int y, double width, double height, double angle) {
        Rectangle back = new Rectangle();
        back.setX(x);
        back.setY(y);
        back.setWidth(width);
        back.setHeight(height);
        back.setFill(BACKCOLOR);
        back.setStroke(STROKECOLOR);
        back.setStrokeWidth(STROKEWIDTH);
        back.setCache(true);
        getChildren().add(back);

        Rotate rotate = new Rotate();
        rotate.setPivotX(x);
        rotate.setPivotY(y);
        rotate.setAngle(angle);
        getTransforms().add(rotate);

        // Works out min / max of x & y with rotation factored in
        double[] sizeArr = {0,width, height};
        int[] xDifference = {1,-2,-1,2};
        int[] yDifference = {2,1,-2,-1};

        minX = Math.min(x, (int) ( x + sizeArr[Math.abs(xDifference[(int) angle / 90])] * Math.signum(xDifference[(int) angle / 90])));
        minY = Math.min(y, (int) (y + sizeArr[Math.abs(yDifference[(int) angle / 90])]* Math.signum(yDifference[(int) angle / 90])));
        maxX = Math.max(x, (int) ( x + sizeArr[Math.abs(xDifference[(int) angle / 90])] * Math.signum(xDifference[(int) angle / 90])));
        maxY = Math.max(y, (int) (y + sizeArr[Math.abs(yDifference[(int) angle / 90])]* Math.signum(yDifference[(int) angle / 90])));;
        this.width = width;
        this.height = height;
    }

    protected String formatText(String in) {
        String[] words = in.split("( )+");

        if (words.length > 1) {
            StringBuilder sb = new StringBuilder();
            int length = words[0].length();

            for (int i = 0; i < words.length - 1; i++) {
                length += words[i + 1].length();
                sb.append(words[i]);
                if (length > MAXCHARSPERLINE) {
                    sb.append("\n");
                    length = 0;
                } else {
                    sb.append(" ");
                }
            }

            sb.append(words[words.length - 1]);

            return sb.toString();
        }
        else {
            return in;
        }
    }

    public double getWidth() {
        return width;
    }


    public double getHeight() {
        return height;
    }

    public int getMinX() {
        return minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxY() {
        return maxY;
    }
}
