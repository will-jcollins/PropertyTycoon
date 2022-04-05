package ui.menu;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.board.BuyableTile;
import model.board.PropertyTile;
import model.board.StationTile;
import model.board.UtilityTile;

public class BuyableCard extends Group {

    public static final int CARD_HEIGHT = 250;
    public static final int CARD_WIDTH = (int) (CARD_HEIGHT / 1.4);

    private static final int PADDING =  CARD_HEIGHT / 40;
    private static final int FONT_SIZE = CARD_HEIGHT / 20;
    private static final Font FONT = Font.loadFont("file:assets/fonts/Kabel.ttf", FONT_SIZE);
    private static final Font CAPTION_FONT = Font.loadFont("file:assets/fonts/Kabel.ttf", FONT_SIZE * 0.9);
    private static final double BOLD_STROKE_SIZE = (double) CARD_HEIGHT / 500;

    private static final int INNER_X = PADDING;
    private static final int INNER_Y = PADDING;
    private static final int INNER_HEIGHT = CARD_HEIGHT - 2 * PADDING;
    private static final int INNER_WIDTH = CARD_WIDTH - 2 * PADDING;
    private static final double INNER_STROKE_SIZE = (double) CARD_HEIGHT / 300;

    // Property geometry variables
    private static final int HEADER_X = INNER_X + PADDING;
    private static final int HEADER_Y = INNER_Y + PADDING;
    private static final int HEADER_HEIGHT = INNER_HEIGHT / 6;
    private static final int HEADER_WIDTH = INNER_WIDTH - 2 * PADDING;
    private static final double HEADER_STROKE_SIZE = (double) CARD_HEIGHT / 150;

    private static final int BODY_X = HEADER_X + PADDING;
    private static final int BODY_Y = HEADER_Y + HEADER_HEIGHT + 2 * PADDING;
    private static final int BODY_HEIGHT = INNER_HEIGHT - HEADER_HEIGHT - 2 * PADDING;
    private static final int BODY_WIDTH = HEADER_WIDTH - 2 * PADDING;

    // Utility and Station geometry variables
    private static final int IMAGE_WIDTH = (int) (HEADER_WIDTH / 1.5);
    private static final int IMAGE_X = INNER_X + INNER_WIDTH / 2 - IMAGE_WIDTH / 2;
    private static final int IMAGE_Y = INNER_Y + PADDING;


    public BuyableCard(BuyableTile tile) {
        super();

        Rectangle back = new Rectangle();
        back.setHeight(CARD_HEIGHT);
        back.setWidth(CARD_WIDTH);
        back.setFill(Color.WHITE);
        getChildren().add(back);

        Rectangle outline = new Rectangle();
        outline.setX(INNER_X);
        outline.setY(INNER_Y);
        outline.setHeight(INNER_HEIGHT);
        outline.setWidth(INNER_WIDTH);
        outline.setStroke(Color.BLACK);
        outline.setStrokeWidth(INNER_STROKE_SIZE);
        outline.setFill(Color.TRANSPARENT);
        getChildren().add(outline);

        if (tile instanceof PropertyTile) {
            drawProperty((PropertyTile) tile);
        } else if (tile instanceof UtilityTile) {
            drawUtility((UtilityTile) tile);
        } else if (tile instanceof StationTile) {
            drawStation((StationTile) tile);
        }
    }

    private void drawProperty(PropertyTile property) {
        Rectangle header = new Rectangle();
        header.setX(HEADER_X);
        header.setY(HEADER_Y);
        header.setHeight(HEADER_HEIGHT);
        header.setWidth(HEADER_WIDTH);
        header.setStrokeWidth(HEADER_STROKE_SIZE);
        header.setStroke(Color.BLACK);
        header.setFill(property.getStreet().getColor());
        getChildren().add(header);

        Text title = new Text("T I T L E  D E E D");
        title.setTextAlignment(TextAlignment.CENTER);
        title.setFont(FONT);
        title.setFill(getTextColor(property.getStreet().getColor()));
        title.setX(HEADER_X + (HEADER_WIDTH / 2) - (title.getBoundsInLocal().getWidth() / 2));
        title.setY(HEADER_Y + PADDING * 0.6 + title.getBoundsInLocal().getHeight());
        getChildren().add(title);

        Text propTitle = new Text(property.getName().toUpperCase());
        propTitle.setTextAlignment(TextAlignment.CENTER);
        propTitle.setFont(FONT);
        propTitle.setFill(getTextColor(property.getStreet().getColor()));
        propTitle.setX(HEADER_X + (HEADER_WIDTH / 2) - (propTitle.getBoundsInLocal().getWidth() / 2));
        propTitle.setY(HEADER_Y + HEADER_HEIGHT - PADDING);
        propTitle.setStroke(getTextColor(property.getStreet().getColor()));
        propTitle.setStrokeWidth(BOLD_STROKE_SIZE);
        getChildren().add(propTitle);

        Text rent = new Text("RENT    $" + property.getRent(0));
        rent.setTextAlignment(TextAlignment.CENTER);
        rent.setFont(FONT);
        rent.setFill(Color.BLACK);
        rent.setX(BODY_X + (BODY_WIDTH / 2) - (rent.getBoundsInLocal().getWidth() / 2));
        rent.setY(BODY_Y + rent.getBoundsInLocal().getHeight());
        getChildren().add(rent);

        int spacing = BODY_HEIGHT / 10;

        for (int i = 1; i < PropertyTile.MAX_NO_HOUSES; i++) {
            Text houseRent = new Text("With " + i + " House" + (i > 1 ? "s" : ""));
            houseRent.setTextAlignment(TextAlignment.LEFT);
            houseRent.setFont(FONT);
            houseRent.setFill(Color.BLACK);
            houseRent.setX(BODY_X);
            houseRent.setY(BODY_Y + (spacing * i) + rent.getBoundsInLocal().getHeight());
            getChildren().add(houseRent);

            Text amount = new Text("$" + property.getRent(i));
            amount.setTextAlignment(TextAlignment.RIGHT);
            amount.setFont(FONT);
            amount.setFill(Color.BLACK);
            amount.setX(BODY_X + BODY_WIDTH - amount.getBoundsInLocal().getWidth());
            amount.setY(BODY_Y + (spacing * i) + rent.getBoundsInLocal().getHeight());
            getChildren().add(amount);
        }

        Text hotelRent = new Text("With HOTEL $" + property.getRent(PropertyTile.MAX_NO_HOUSES));
        hotelRent.setTextAlignment(TextAlignment.CENTER);
        hotelRent.setFont(FONT);
        hotelRent.setFill(Color.BLACK);
        hotelRent.setX(BODY_X + (BODY_WIDTH / 2) - (hotelRent.getBoundsInLocal().getWidth() / 2));
        hotelRent.setY(BODY_Y + (spacing * PropertyTile.MAX_NO_HOUSES) + rent.getBoundsInLocal().getHeight());
        getChildren().add(hotelRent);

        Text mortgageVal = new Text("Mortgage Value $" + property.getCost() / 2);
        mortgageVal.setTextAlignment(TextAlignment.CENTER);
        mortgageVal.setFont(FONT);
        mortgageVal.setFill(Color.BLACK);
        mortgageVal.setX(BODY_X + (BODY_WIDTH / 2) - (mortgageVal.getBoundsInLocal().getWidth() / 2));
        mortgageVal.setY(BODY_Y + (spacing * (PropertyTile.MAX_NO_HOUSES + 1)) + rent.getBoundsInLocal().getHeight());
        getChildren().add(mortgageVal);

        Text housesCost = new Text("Houses cost $" + property.getStreet().getDevelopCost() + ". each");
        housesCost.setTextAlignment(TextAlignment.CENTER);
        housesCost.setFont(FONT);
        housesCost.setFill(Color.BLACK);
        housesCost.setX(BODY_X + (BODY_WIDTH / 2) - (housesCost.getBoundsInLocal().getWidth() / 2));
        housesCost.setY(BODY_Y + (spacing * (PropertyTile.MAX_NO_HOUSES + 2)) + rent.getBoundsInLocal().getHeight());
        getChildren().add(housesCost);

        Text hotelCost = new Text("Hotels, $" + property.getStreet().getDevelopCost() + ". plus 4 houses");
        hotelCost.setTextAlignment(TextAlignment.CENTER);
        hotelCost.setFont(FONT);
        hotelCost.setFill(Color.BLACK);
        hotelCost.setX(BODY_X + (BODY_WIDTH / 2) - (hotelCost.getBoundsInLocal().getWidth() / 2));
        hotelCost.setY(BODY_Y + (spacing * (PropertyTile.MAX_NO_HOUSES + 3)) + rent.getBoundsInLocal().getHeight());
        getChildren().add(hotelCost);
    }

    private void drawUtility(UtilityTile utility) {
        ImageView icon = new ImageView(new Image("file:assets/images/utility.png"));
        icon.setX(IMAGE_X);
        icon.setY(IMAGE_Y);
        icon.setFitHeight(IMAGE_WIDTH);
        icon.setFitWidth(IMAGE_WIDTH);
        getChildren().add(icon);

        int y = IMAGE_Y + IMAGE_WIDTH + PADDING * 2;

        Text title = new Text(utility.getName().toUpperCase());
        title.setTextAlignment(TextAlignment.CENTER);
        title.setFont(FONT);
        title.setFill(Color.BLACK);
        title.setStrokeWidth(BOLD_STROKE_SIZE);
        title.setStroke(Color.BLACK);
        title.setX(BODY_X + (BODY_WIDTH / 2) - (title.getBoundsInLocal().getWidth() / 2));
        title.setY(y);
        getChildren().add(title);

        String[] sents = {"If one Utility is owned","rent is 4 times amount","shown on dice."};
        y += title.getBoundsInLocal().getHeight() + PADDING * 2;

        for (String sent : sents) {
            Text caption = new Text(sent);
            caption.setFont(CAPTION_FONT);
            caption.setX(BODY_X + (BODY_WIDTH / 2) - (caption.getBoundsInLocal().getWidth() / 2));
            caption.setY(y);
            caption.setOpacity(0.75);
            getChildren().add(caption);

            y += caption.getBoundsInLocal().getHeight();
        }

        sents = new String[]{"If both Utilities are", "owned rent is 10 times", "amount shown on dice."};
        y += PADDING;


        for (String sent : sents) {
            Text caption = new Text(sent);
            caption.setFont(CAPTION_FONT);
            caption.setX(BODY_X + (BODY_WIDTH / 2) - (caption.getBoundsInLocal().getWidth() / 2));
            caption.setY(y);
            caption.setOpacity(0.75);
            getChildren().add(caption);

            y += caption.getBoundsInLocal().getHeight();
        }

        y += PADDING * 2;

        Text mortgage = new Text("Mortgage Value       $" + (utility.getCost() / 2));
        mortgage.setFont(CAPTION_FONT);
        mortgage.setX(BODY_X + (BODY_WIDTH / 2) - (mortgage.getBoundsInLocal().getWidth() / 2));
        mortgage.setY(y);
        mortgage.setOpacity(0.75);
        getChildren().add(mortgage);
    }

    private void drawStation(StationTile station) {
        ImageView icon = new ImageView(new Image("file:assets/images/station.png"));
        icon.setX(IMAGE_X);
        icon.setY(IMAGE_Y);
        icon.setFitHeight(IMAGE_WIDTH);
        icon.setFitWidth(IMAGE_WIDTH);
        getChildren().add(icon);

        int spacing = (INNER_HEIGHT - 2 * PADDING - IMAGE_WIDTH) / 6;
        int textY = IMAGE_X + IMAGE_WIDTH;

        Text title = new Text(station.getName().toUpperCase());
        title.setTextAlignment(TextAlignment.CENTER);
        title.setFont(FONT);
        title.setFill(Color.BLACK);
        title.setStrokeWidth(BOLD_STROKE_SIZE);
        title.setStroke(Color.BLACK);
        title.setX(BODY_X + (BODY_WIDTH / 2) - (title.getBoundsInLocal().getWidth() / 2));
        title.setY(textY);
        getChildren().add(title);

        for (int i = 1; i <= StationTile.rent.length; i++) {
            Text houseRent = new Text(i < 2 ? "Rent" : i + " Stations Owned");
            houseRent.setTextAlignment(TextAlignment.LEFT);
            houseRent.setFont(FONT);
            houseRent.setFill(Color.BLACK);
            houseRent.setX(BODY_X);
            houseRent.setY(textY + (spacing * i));
            getChildren().add(houseRent);

            Text amount = new Text("$" + StationTile.rent[i - 1]);
            amount.setTextAlignment(TextAlignment.RIGHT);
            amount.setFont(FONT);
            amount.setFill(Color.BLACK);
            amount.setX(BODY_X + BODY_WIDTH - amount.getBoundsInLocal().getWidth());
            amount.setY(textY + (spacing * i));
            getChildren().add(amount);
        }
    }

    /**
     * Returns the most visible colour for a given background colour
     * Based on answer by Mark Ransom at https://stackoverflow.com/questions/3942878/how-to-decide-font-color-in-white-or-black-depending-on-background-color
     * @author Will Collins
     * @param c background color
     * @return white or black color
     */
    public static Color getTextColor(Color c) {
        // If intensity is above a 'magic' threshold black is returned, otherwise white is returned
        if ((c.getRed()*255*0.299 + c.getGreen()*255*0.587 + c.getBlue()*255*0.114) > 186) {
            return Color.BLACK;
        } else {
            return Color.WHITE;
        }
    }
}
