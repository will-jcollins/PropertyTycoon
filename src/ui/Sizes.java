package ui;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;


/**
 * Container for static attributes
 * Used to adjust element's sizes for different displays when explicit values are needed by JavaFX
 * @Author Will Collins
 */
public class Sizes {

    // Font Sizes
    private static double FONT_HEADING;
    private static double FONT_BODY;
    private static double FONT_PROP_TITLE;
    private static double FONT_PROP_COST;
    private static double LINE_SPACING;

    // Stroke sizes
    private static double LARGE_STROKE;
    private static double SMALL_STROKE;

    // Element spacing
    private static int PADDING;

    // Button sizes
    private static double BUTTON_WIDTH;
    private static double BUTTON_HEIGHT;

    // Arrow sizes
    private static double ARROW_WIDTH;
    private static double ARROW_HEIGHT;

    // Dice size
    private static double DICE_SIZE;

    // Buyable card size
    private static double CARD_SIZE;

    // Player token sizes
    private static double TOKEN_SIZE;

    // Min menu size
    private static double MENU_SIZE;

    /**
     * Calculates sizes of elements based on display's properties
     * @Author Will Collins
     */
    public static void computeSizes() {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double height = screenBounds.getHeight();

        // Unfortunate use of magic numbers,
        // Calculated from sizes on MacBook Pro 13" 2020 (scaled to 1440x900)

        // Menu Font sizes
        FONT_HEADING = height / 45;
        FONT_BODY = height / 60;

        // Property font sizes and spacing
        FONT_PROP_TITLE = height / 100;
        FONT_PROP_COST = height / 128;
        LINE_SPACING = -(height / 120);

        // Stroke widths
        LARGE_STROKE = height / 225;
        SMALL_STROKE = LARGE_STROKE / 2;

        // Padding value
        PADDING = (int) height / 90;

        // Button sizes
        BUTTON_HEIGHT = height / 18;
        BUTTON_WIDTH = height / 6;

        // Arrow sizes
        ARROW_HEIGHT = height / 45;
        ARROW_WIDTH = height / 9;

        // Dice size
        DICE_SIZE = height / 12;

        // Card size
        CARD_SIZE = height / 3.5;

        // Player token size
        TOKEN_SIZE = height / 26;

        // Minimum menu size (Mainly for develop menu since scrollPane shrinks by default)
        MENU_SIZE = height / 2;
    }

    public static double getFontHeading() {
        return FONT_HEADING;
    }

    public static double getFontBody() {
        return FONT_BODY;
    }

    public static double getFontPropTitle() {
        return FONT_PROP_TITLE;
    }

    public static double getFontPropCost() {
        return FONT_PROP_COST;
    }

    public static double getLineSpacing() {
        return LINE_SPACING;
    }

    public static double getLargeStroke() {
        return LARGE_STROKE;
    }

    public static double getSmallStroke() {
        return SMALL_STROKE;
    }

    public static int getPadding() {
        return PADDING;
    }

    public static double getButtonWidth() {
        return BUTTON_WIDTH;
    }

    public static double getButtonHeight() {
        return BUTTON_HEIGHT;
    }

    public static double getArrowWidth() {
        return ARROW_WIDTH;
    }

    public static double getArrowHeight() {
        return ARROW_HEIGHT;
    }

    public static double getDiceSize() {
        return DICE_SIZE;
    }

    public static double getCardSize() {
        return CARD_SIZE;
    }

    public static double getTokenSize() {
        return TOKEN_SIZE;
    }

    public static double getMenuSize() {
        return MENU_SIZE;
    }
}
