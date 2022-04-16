package ui.menu;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Player.Player;
import model.board.BuyableTile;
import model.board.PropertyTile;
import model.board.Street;
import ui.Sizes;
/**
 * Class responisible for buying menu
 */
public class BuyableMenu extends Menu {

    private boolean outcome = false;
    private boolean finished = false;
    /**
     * Constructor of buying menu class
     * @param property instance of BuyableTile
     * @param player INstance of Player
     */
    public BuyableMenu(BuyableTile property, Player player) {
        super();

        Text title =  new Text("PURCHASE " + property.getName().toUpperCase() + "?");
        title.setFont(TITLE_FONT);
        title.setFill(Color.BLACK);
        getChildren().add(title);
        setColumnIndex(title,0);
        setColumnSpan(title,2);
        setRowIndex(title, 0);
        setHalignment(title, HPos.CENTER);

        BuyableCard card = new BuyableCard(property,Sizes.getCardSize());
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

        TextButton auction = new TextButton(Sizes.getButtonWidth(),Sizes.getButtonHeight(), Street.RED.getColor(), "AUCTION");
        getChildren().add(auction);
        setColumnIndex(auction,0);
        setRowIndex(auction,3);
        auction.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            finished = true;
        });

        TextButton accept;

        if (property.getCost() <= player.getMoney()) {
            accept = new TextButton(Sizes.getButtonWidth(),Sizes.getButtonHeight(),Street.GREEN.getColor(), "BUY");
            accept.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                outcome = true;
                finished = true;
            });
        } else {
            accept = new TextButton(Sizes.getButtonWidth(),Sizes.getButtonHeight(), Color.GRAY, "BUY");
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
    }

    public boolean getOutcome() {
        return outcome;
    }

    @Override
    public int getEndLatency() {
        return 0;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
