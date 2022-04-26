package ui.menu;

import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import ui.Sizes;
/**
 * Class responisible for building button with text
 */
public class TextButton extends Group {

    private static final int PADDING = Sizes.getPadding() / 2;
    private static final double OUTLINE_WIDTH = Sizes.getSmallStroke();

    private static final Font FONT = Font.loadFont("file:assets/fonts/Kabel.ttf", Sizes.getFontHeading());

    private Text buttonText;

    /**
     * Constructor of TextButton class
     * @param width width value
     * @param height height value
     * @param color color value
     * @param text text in the button
     */
    public TextButton(double width, double height, Color color, String text) {
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
        buttonText.setX(width / 2 - (buttonText.getBoundsInLocal().getWidth() / 2));
        buttonText.setY(height / 2 + (buttonText.getBoundsInLocal().getHeight() / 3));
        getChildren().add(buttonText);

        addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(5),this);
            scaleTransition.setToX(0.95);
            scaleTransition.setToY(0.95);
            scaleTransition.play();
        });

        addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(5),this);
            scaleTransition.setToX(1);
            scaleTransition.setToY(1);
            scaleTransition.play();
        });
    }

    public void fire() {
        ScaleTransition shrinkTransition = new ScaleTransition(Duration.millis(50),this);
        shrinkTransition.setToX(0.9);
        shrinkTransition.setToY(0.9);

        ScaleTransition growTransition = new ScaleTransition(Duration.millis(50),this);
        growTransition.setToX(1);
        growTransition.setToY(1);

        SequentialTransition sequentialTransition = new SequentialTransition(shrinkTransition,growTransition);
        sequentialTransition.setOnFinished(e -> {
            fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, // double x,
                    0, // double y,
                    0, // double screenX,
                    0, // double screenY,
                    MouseButton.PRIMARY, // MouseButton button,
                    0, // int clickCount,
                    false, // boolean shiftDown,
                    false, // boolean controlDown,
                    false, // boolean altDown,
                    false, // boolean metaDown,
                    true, // boolean primaryButtonDown,
                    false, // boolean middleButtonDown,
                    false, // boolean secondaryButtonDown,
                    false, // boolean synthesized,
                    false, // boolean popupTrigger,
                    false, // boolean stillSincePress,
                    null // PickResult pickResult
            ));
        });
        sequentialTransition.play();
    }
}
