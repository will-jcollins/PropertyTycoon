package ui.board;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.board.PropertyTile;
import ui.menu.BuyableCard;

public class PropertyUITile extends UITile {

    private static final String HOUSE_PATH = "file:assets/images/house.png";
    private static final String HOTEL_PATH = "file:assets/images/hotel.png";

    private static final Font HOUSE_FONT = Font.loadFont("file:assets/fonts/Kabel.ttf", 15);

    private ImageView houseIcon;
    private Text houseCount;

    private ImageView hotelIcon;

    private PropertyTile property;

    public PropertyUITile(int x, int y, double width, double height, double angle, PropertyTile property) {
        super(x, y, width, height, angle);

        this.property = property;

        Rectangle group = new Rectangle();
        group.setX(x);
        group.setY(y);
        group.setWidth(width);
        group.setHeight(height / 4);
        group.setFill(property.getStreet().getColor());
        group.setStroke(STROKECOLOR);
        group.setStrokeWidth(STROKEWIDTH);
        getChildren().add(group);

        // Setup house and hotel icons
        houseIcon = new ImageView(HOUSE_PATH);
        houseIcon.setX(x + STROKEWIDTH);
        houseIcon.setY(y + STROKEWIDTH);
        houseIcon.setFitWidth(height / 4 - (2 * STROKEWIDTH));
        houseIcon.setFitHeight(height / 4 - (2 * STROKEWIDTH));
        houseIcon.setVisible(false);
        getChildren().add(houseIcon);

        houseCount = new Text("1");
        houseCount.setFont(HOUSE_FONT);
        houseCount.setFill(BuyableCard.getTextColor(property.getStreet().getColor()));
        houseCount.setX(x + height / 4);
        houseCount.setY(y + height / 4 - houseCount.getBoundsInLocal().getHeight() / 2);
        houseCount.setVisible(false);
        getChildren().add(houseCount);

        hotelIcon = new ImageView(HOTEL_PATH);
        hotelIcon.setX(houseCount.getX() + houseCount.getBoundsInLocal().getWidth() * 2);
        hotelIcon.setY(y + STROKEWIDTH);
        hotelIcon.setFitWidth(height / 4 - (2 * STROKEWIDTH));
        hotelIcon.setFitHeight(height / 4 - (2 * STROKEWIDTH));
        hotelIcon.setVisible(false);
        getChildren().add(hotelIcon);

        // Replace space chars with newline
        String name = formatText(property.getName());

        Text title = new Text(name.toUpperCase());
        title.setX(x);
        title.setY(y + 1.7 * (height / 4));
        title.setWrappingWidth(width);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setLineSpacing(-7.5);
        title.setFont(TITLEFONT);
        title.setStroke(Color.BLACK);
        title.setStrokeWidth(0.4);
        getChildren().add(title);

        Text caption = new Text("$" + property.getCost());
        caption.setX(x);
        caption.setY(y + height - (height / 12));
        caption.setWrappingWidth(width);
        caption.setTextAlignment(TextAlignment.CENTER);
        caption.setFont(CAPTIONFONT);
        caption.setStroke(Color.BLACK);
        caption.setStrokeWidth(0.4);
        getChildren().add(caption);
    }

    public void update() {
        if (property.getNoHouses() > 0) {
            houseIcon.setVisible(true);
            houseCount.setVisible(true);
            int houses = Math.min(property.getNoHouses(),PropertyTile.MAX_NO_HOUSES - 1);
            Platform.runLater(() -> houseCount.setText(Integer.toString(houses)));
        }

        if (property.getNoHouses() >= PropertyTile.MAX_NO_HOUSES) {
            hotelIcon.setVisible(true);
        }
    }
}