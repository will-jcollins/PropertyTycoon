package ui.menu;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Player.Player;
import model.board.PropertyTile;
import model.board.Street;

public class PropertyMenu extends GridPane {

    private static final int PADDING = 10;
    private static final Color BACKGROUND_COLOR = new Color(1,1,1,0.75);

    private static final int HEIGHT = 300;
    private static final int WIDTH = 300;

    private static final Font TITLE_FONT = Font.loadFont("file:assets/fonts/Kabel.ttf", 20);
    private static final Font TEXT_FONT = Font.loadFont("file:assets/fonts/Kabel.ttf", 15);

    private boolean outcome;

    public PropertyMenu(PropertyTile property, Player player) {
        super();

        outcome = false;

        setStyle("-fx-border-style: solid inside;" +
                 "-fx-border-width: 3;" +
                 "");

        Text title =  new Text("PURCHASE " + property.getName().toUpperCase() + "?");
        title.setFont(TITLE_FONT);
        title.setFill(Color.BLACK);
        title.setStrokeWidth(0.5);
        title.setStroke(Color.BLACK);
        getChildren().add(title);
        setColumnIndex(title,0);
        setColumnSpan(title,2);
        setRowIndex(title, 0);
        setHalignment(title, HPos.CENTER);

        PropertyCard card = new PropertyCard(property);
        getChildren().add(card);
        setColumnIndex(card, 0);
        setColumnSpan(card, 2);
        setRowIndex(card,1);
        setHalignment(card, HPos.CENTER);

        Text costBalance = new Text("COST $" + property.getCost() + "  BALANCE $" + player.getMoney());
        costBalance.setFont(TEXT_FONT);
        costBalance.setFill(Color.BLACK);
        getChildren().add(costBalance);
        setColumnIndex(costBalance, 0);
        setColumnSpan(costBalance, 2);
        setRowIndex(costBalance, 2);
        setHalignment(costBalance, HPos.CENTER);

        TextButton auction = new TextButton(150,50, Street.RED.getColor(), "AUCTION");
        getChildren().add(auction);
        setColumnIndex(auction,0);
        setRowIndex(auction,3);

        TextButton accept;

        if (property.getCost() <= player.getMoney()) {
            accept = new TextButton(150,50,Street.GREEN.getColor(), "BUY");
        } else {
            accept = new TextButton(150, 50, Color.GRAY, "BUY");

            Text alert = new Text("BALANCE TOO LOW");
            alert.setFont(TEXT_FONT);
            getChildren().add(alert);
            setColumnIndex(alert,1);
            setRowIndex(alert,4);
            setHalignment(alert,HPos.CENTER);
        }

        getChildren().add(accept);
        setColumnIndex(accept,1);
        setRowIndex(accept, 3);


        setPadding(new Insets(PADDING,PADDING * 2,PADDING,PADDING * 2));
        setHgap(PADDING);
        setVgap(PADDING);
        setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR,null,null)));
        setAlignment(Pos.BASELINE_CENTER);
        setMaxHeight(WIDTH);
        setMaxWidth(HEIGHT);
    }

    public boolean getOutcome() {
        return outcome;
    }
}
