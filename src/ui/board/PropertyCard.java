package ui.board;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.board.PropertyTile;

public class PropertyCard extends Group {

    public static final int CARD_HEIGHT = 300;
    public static final int CARD_WIDTH = (int) (CARD_HEIGHT / 1.4);

    private static final int PADDING =  CARD_HEIGHT / 40;
    private static final int FONT_SIZE = CARD_HEIGHT / 20;
    private static final Font FONT = Font.loadFont("file:assets/fonts/Kabel.ttf", FONT_SIZE);
    private static final double BOLD_STROKE_SIZE = (double) CARD_HEIGHT / 500;


    private static final int INNER_X = PADDING;
    private static final int INNER_Y = PADDING;
    private static final int INNER_HEIGHT = CARD_HEIGHT - 2 * PADDING;
    private static final int INNER_WIDTH = CARD_WIDTH - 2 * PADDING;
    private static final double INNER_STROKE_SIZE = (double) CARD_HEIGHT / 300;

    private static final int HEADER_X = PADDING * 2;
    private static final int HEADER_Y = PADDING * 2;
    private static final int HEADER_HEIGHT = INNER_HEIGHT / 6;
    private static final int HEADER_WIDTH = INNER_WIDTH - 2 * PADDING;
    private static final double HEADER_STROKE_SIZE = (double) CARD_HEIGHT / 150;

    private static final int BODY_X = HEADER_X + PADDING;
    private static final int BODY_Y = HEADER_Y + HEADER_HEIGHT + 2 * PADDING;
    private static final int BODY_HEIGHT = INNER_HEIGHT - HEADER_HEIGHT - 2 * PADDING;
    private static final int BODY_WIDTH = HEADER_WIDTH - 2 * PADDING;


    public PropertyCard(PropertyTile prop) {
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

        Rectangle header = new Rectangle();
        header.setX(HEADER_X);
        header.setY(HEADER_Y);
        header.setHeight(HEADER_HEIGHT);
        header.setWidth(HEADER_WIDTH);
        header.setStrokeWidth(HEADER_STROKE_SIZE);
        header.setStroke(Color.BLACK);
        header.setFill(prop.getStreet().getColor());
        getChildren().add(header);

        Text title = new Text("T I T L E  D E E D");
        title.setTextAlignment(TextAlignment.CENTER);
        title.setFont(FONT);
        title.setFill(getTxtColor(prop.getStreet().getColor()));
        title.setX(HEADER_X + (HEADER_WIDTH / 2) - (title.getBoundsInLocal().getWidth() / 2));
        title.setY(HEADER_Y + PADDING + title.getBoundsInLocal().getHeight());
        getChildren().add(title);

        Text propTitle = new Text(prop.getName().toUpperCase());
        propTitle.setTextAlignment(TextAlignment.CENTER);
        propTitle.setFont(FONT);
        propTitle.setFill(getTxtColor(prop.getStreet().getColor()));
        propTitle.setX(HEADER_X + (HEADER_WIDTH / 2) - (propTitle.getBoundsInLocal().getWidth() / 2));
        propTitle.setY(HEADER_Y + HEADER_HEIGHT - PADDING);
        propTitle.setStroke(getTxtColor(prop.getStreet().getColor()));
        propTitle.setStrokeWidth(BOLD_STROKE_SIZE);
        getChildren().add(propTitle);

        Text rent = new Text("RENT    $" + prop.getRent(0));
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

            Text amount = new Text("$" + prop.getRent(i));
            amount.setTextAlignment(TextAlignment.RIGHT);
            amount.setFont(FONT);
            amount.setFill(Color.BLACK);
            amount.setX(BODY_X + BODY_WIDTH - amount.getBoundsInLocal().getWidth());
            amount.setY(BODY_Y + (spacing * i) + rent.getBoundsInLocal().getHeight());
            getChildren().add(amount);
        }

        Text hotelRent = new Text("With HOTEL $" + prop.getRent(PropertyTile.MAX_NO_HOUSES));
        hotelRent.setTextAlignment(TextAlignment.CENTER);
        hotelRent.setFont(FONT);
        hotelRent.setFill(Color.BLACK);
        hotelRent.setX(BODY_X + (BODY_WIDTH / 2) - (hotelRent.getBoundsInLocal().getWidth() / 2));
        hotelRent.setY(BODY_Y + (spacing * PropertyTile.MAX_NO_HOUSES) + rent.getBoundsInLocal().getHeight());
        getChildren().add(hotelRent);

        Text mortgageVal = new Text("Mortgage Value $" + prop.getCost() / 2);
        mortgageVal.setTextAlignment(TextAlignment.CENTER);
        mortgageVal.setFont(FONT);
        mortgageVal.setFill(Color.BLACK);
        mortgageVal.setX(BODY_X + (BODY_WIDTH / 2) - (mortgageVal.getBoundsInLocal().getWidth() / 2));
        mortgageVal.setY(BODY_Y + (spacing * (PropertyTile.MAX_NO_HOUSES + 1)) + rent.getBoundsInLocal().getHeight());
        getChildren().add(mortgageVal);

        Text housesCost = new Text("Houses cost $150. each");
        housesCost.setTextAlignment(TextAlignment.CENTER);
        housesCost.setFont(FONT);
        housesCost.setFill(Color.BLACK);
        housesCost.setX(BODY_X + (BODY_WIDTH / 2) - (housesCost.getBoundsInLocal().getWidth() / 2));
        housesCost.setY(BODY_Y + (spacing * (PropertyTile.MAX_NO_HOUSES + 2)) + rent.getBoundsInLocal().getHeight());
        getChildren().add(housesCost);

        Text hotelCost = new Text("Hotels, $150. plus 4 houses");
        hotelCost.setTextAlignment(TextAlignment.CENTER);
        hotelCost.setFont(FONT);
        hotelCost.setFill(Color.BLACK);
        hotelCost.setX(BODY_X + (BODY_WIDTH / 2) - (hotelCost.getBoundsInLocal().getWidth() / 2));
        hotelCost.setY(BODY_Y + (spacing * (PropertyTile.MAX_NO_HOUSES + 3)) + rent.getBoundsInLocal().getHeight());
        getChildren().add(hotelCost);
    }

    /**
     * Returns the most visible colour for a given background colour
     * Based on answer by Mark Ransom at https://stackoverflow.com/questions/3942878/how-to-decide-font-color-in-white-or-black-depending-on-background-color
     * @author Will Collins
     * @param c background color
     * @return white or black color
     */
    public static Color getTxtColor(Color c) {
        // Factors based on colour theory
        if ((c.getRed()*255*0.299 + c.getGreen()*255*0.587 + c.getBlue()*255*0.114) > 186) {
            return Color.BLACK;
        } else {
            return Color.WHITE;
        }
    }
}
