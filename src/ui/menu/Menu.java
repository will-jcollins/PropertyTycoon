package ui.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ui.Sizes;
/**
 * Abstract class which sets the style of the menu as well as
 * padding and background color
 */
public abstract class Menu extends GridPane {

    protected static final int PADDING = Sizes.getPadding();
    protected static final Color BACKGROUND_COLOR = Color.WHITE;

    protected static final Font TITLE_FONT = Font.loadFont("file:assets/fonts/Kabel.ttf", Sizes.getFontHeading());
    protected static final Font TEXT_FONT = Font.loadFont("file:assets/fonts/Kabel.ttf", Sizes.getFontBody());

    /**
     * Constructor of Menu class
     */
    public Menu() {
        super();

        setStyle("-fx-border-style: solid inside;" +
                "-fx-border-width: " + Sizes.getLargeStroke() + ";");

        setPadding(new Insets(PADDING,PADDING * 2,PADDING,PADDING * 2));
        setHgap(PADDING);
        setVgap(PADDING);
        setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR,null,null)));
        setAlignment(Pos.BASELINE_CENTER);
        setMaxHeight(0);
        setMaxWidth(0);
    }

    public int getEndLatency() {
        return 1000;
    }

    public boolean isFinished() {
        return true;
    }
}
