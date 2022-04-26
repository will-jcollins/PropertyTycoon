package ui.menu;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ui.Sizes;

import java.util.ArrayList;
import java.util.Random;

/**
 * Abstract class which sets the style of the menu as well as
 * padding and background color
 */
public abstract class Menu extends GridPane {

    protected static final int PADDING = Sizes.getPadding();
    protected static final Color BACKGROUND_COLOR = Color.WHITE;

    protected static final Font TITLE_FONT = Font.loadFont("file:assets/fonts/Kabel.ttf", Sizes.getFontHeading());
    protected static final Font TEXT_FONT = Font.loadFont("file:assets/fonts/Kabel.ttf", Sizes.getFontBody());

    private ArrayList<TextButton> options;

    /**
     * Constructor of Menu class
     */
    public Menu() {
        super();

        options = new ArrayList<>();

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

    /**
     * Indicates to UI how long to show the menu for (in milliseconds)
     * once isFinished returns true
     * @return time
     */
    public int getEndLatency() {
        return 1000;
    }

    public boolean isFinished() {
        return true;
    }

    /**
     * Adds a button to list of options that can be randomly pressed
     * By AIPlayers
     * @param option TextButton
     */
    protected void addOption(TextButton option) {
        options.add(option);
    }

    /**
     * Randomly presses one of the TextButton options (if any exist)
     */
    public void autoFire() {
        if (options.size() > 0) {
            Random random = new Random();
            options.get(random.nextInt(options.size())).fire();
            setDisable(true);
        }
    }
}
