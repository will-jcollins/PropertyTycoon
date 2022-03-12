package ui.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TextButton extends Group {

    private static final int PADDING = 5;
    private static final double OUTLINE_WIDTH = 2.5;

    private static final Font FONT = Font.loadFont("file:assets/fonts/Kabel.ttf", 20);

    private Text buttonText;

    public TextButton(int width, int height, Color color, String text) {
        super();

        Rectangle back = new Rectangle();
        back.setWidth(width);
        back.setHeight(height);
        back.setFill(color);
        getChildren().add(back);

        Rectangle outline = new Rectangle();
        outline.setX(PADDING);
        outline.setY(PADDING);
        outline.setFill(Color.TRANSPARENT);
        outline.setWidth(width - 2 * PADDING);
        outline.setHeight(height - 2 * PADDING);
        outline.setStroke(Color.WHITE);
        outline.setStrokeWidth(OUTLINE_WIDTH);
        getChildren().add(outline);

        buttonText = new Text(text);
        buttonText.setTextAlignment(TextAlignment.CENTER);
        buttonText.setFont(FONT);
        buttonText.setFill(Color.WHITE);
        buttonText.setStrokeWidth(0.5);
        buttonText.setStroke(Color.WHITE);
        buttonText.setX(width / 2 - (buttonText.getBoundsInLocal().getWidth() / 2));
        buttonText.setY(height / 2 + (buttonText.getBoundsInLocal().getHeight() / 3));
        getChildren().add(buttonText);
    }
}
