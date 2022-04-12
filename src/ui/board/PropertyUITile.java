package ui.board;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import model.board.PropertyTile;
import ui.Sizes;
import ui.menu.BuyableCard;
import ui.player.UIPlayers;

import java.security.acl.Owner;

public class PropertyUITile extends UITile {

    private static final String HOUSE_PATH = "file:assets/images/house.png";
    private static final String HOTEL_PATH = "file:assets/images/hotel.png";

    private static final Font HOUSE_FONT = Font.loadFont("file:assets/fonts/Kabel.ttf", Sizes.getFontBody());

    private ImageView houseIcon;
    private Text houseCount;

    private ImageView hotelIcon;

    private Polygon ribbon;

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
        group.setStrokeWidth(Sizes.getLargeStroke());
        getChildren().add(group);

        // Setup house and hotel icons
        houseIcon = new ImageView(new Image(HOUSE_PATH,height / 4 - (2 * Sizes.getLargeStroke()),height / 4 - (2 * Sizes.getLargeStroke()),false,true));
        houseIcon.setX(x + Sizes.getLargeStroke());
        houseIcon.setY(y + Sizes.getLargeStroke());
        houseIcon.setVisible(false);
        getChildren().add(houseIcon);

        houseCount = new Text("1");
        houseCount.setFont(HOUSE_FONT);
        houseCount.setFill(BuyableCard.getTextColor(property.getStreet().getColor()));
        houseCount.setX(x + height / 4);
        houseCount.setY(y + height / 4 - houseCount.getBoundsInLocal().getHeight() / 2);
        houseCount.setVisible(false);
        getChildren().add(houseCount);

        hotelIcon = new ImageView(new Image(HOTEL_PATH,height / 4 - (2 * Sizes.getLargeStroke()),height / 4 - (2 * Sizes.getLargeStroke()),false,true));
        hotelIcon.setX(houseCount.getX() + houseCount.getBoundsInLocal().getWidth() * 2);
        hotelIcon.setY(y + Sizes.getLargeStroke());
        hotelIcon.setVisible(false);
        getChildren().add(hotelIcon);

        // Replace space chars with newline
        String name = formatText(property.getName());

        Text title = new Text(name.toUpperCase());
        title.setX(x);
        title.setY(y + 1.7 * (height / 4));
        title.setWrappingWidth(width);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setLineSpacing(Sizes.getLineSpacing());
        title.setFont(TITLEFONT);
        getChildren().add(title);

        Text caption = new Text("$" + property.getCost());
        caption.setX(x);
        caption.setY(y + height - (height / 12));
        caption.setWrappingWidth(width);
        caption.setTextAlignment(TextAlignment.CENTER);
        caption.setFont(CAPTIONFONT);
        getChildren().add(caption);

        ribbon = new OwnerRibbon(x + width / 2 - width / 16, y + height, width / 8, height / 4);
        ribbon.setOpacity(0);
        ribbon.setStroke(Color.BLACK);
        ribbon.setStrokeWidth(Sizes.getSmallStroke());
        ribbon.toBack();
        getChildren().add(ribbon);
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

        if (property.getOwner() != null && ribbon.getOpacity() < 1) {
            ribbon.setFill(UIPlayers.PLAYER_COLORS[property.getOwner().getId()]);
            ribbon.setOpacity(1);
        }
    }
}