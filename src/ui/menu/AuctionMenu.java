package ui.menu;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Player.Player;
import model.board.BuyableTile;
import model.board.Street;
import model.game.Bid;
import model.game.Game;
import ui.Sizes;
import ui.player.UIPlayers;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AuctionMenu extends Menu {

    private UITimer timer;
    private Text maxBid;
    private Map<Player,TextButton> buttonMap;

    public AuctionMenu(Game model) {
        BuyableCard card = new BuyableCard((BuyableTile) model.getBoard().getTile(model.getCurrentPlayer().getPos()), Sizes.getCardSize());
        setHalignment(card, HPos.CENTER);
        add(card,0,0,2,1);

        timer = new UITimer(Game.AUCTION_TIME);
        add(timer,0,1);

        maxBid = new Text("CURRENT BID: $0");
        maxBid.setFont(TITLE_FONT);
        add(maxBid,1,1);

        TextField bidField = new TextField();
        bidField.setFont(TEXT_FONT);
        bidField.textProperty().addListener((observable,oldValue,newValue) -> {

            // If input is not a number reset to previous input
            if (newValue.length() > 0) {
                if (!Character.isDigit(newValue.toCharArray()[newValue.length() - 1])) {
                    bidField.setText(oldValue);
                }
            }

            // Limit numbers to 4 digits so they don't go past max int value
            if (newValue.length() > 4) {
                bidField.setText(oldValue);
            }

            // Reset all buttons to be enabled
            for (TextButton txtButton : buttonMap.values()) {
                txtButton.setDisable(false);
                txtButton.setFill(Street.GREEN.getColor());
            }

            // If input is less than current bid, disable all bid buttons
            if (model.getHighestBid() != null && bidField.getText().length() > 0) {
                if (model.getHighestBid().getAmount() > Integer.parseInt(bidField.getText())) {
                    for (TextButton txtButton : buttonMap.values()) {
                        txtButton.setDisable(true);
                        txtButton.setFill(Color.GRAY);
                    }
                }
            }

            // Gray out and disable buttons for players that can't afford input bid
            if (bidField.getText().length() > 0) {
                for (Player p : buttonMap.keySet()) {
                    if (p.getMoney() < Integer.parseInt(bidField.getText())) {
                        buttonMap.get(p).setDisable(true);
                        buttonMap.get(p).setFill(Color.GRAY);
                    }
                }
            }
        });
        add(bidField,0,2,2,1);

        HBox bidButtons = new HBox();
        bidButtons.setAlignment(Pos.CENTER);
        bidButtons.setSpacing(Sizes.getPadding());

        ArrayList<Player> players = new ArrayList<>(model.getPlayers());

        buttonMap = new HashMap<>();

        for (Player p : players) {
            VBox tempVBox = new VBox();
            tempVBox.setAlignment(Pos.CENTER);
            tempVBox.setSpacing(Sizes.getPadding());

            HBox tempHBox = new HBox();
            tempHBox.setAlignment(Pos.CENTER);
            tempHBox.setSpacing(Sizes.getPadding());

            ImageView tempToken = new ImageView(UIPlayers.PLAYER_IMGS[p.getId()]);
            tempToken.setFitWidth(Sizes.getTokenSize());
            tempToken.setFitHeight(Sizes.getTokenSize());
            tempHBox.getChildren().add(tempToken);

            Text tempTxt = new Text(p.getName());
            tempTxt.setFont(TEXT_FONT);
            tempHBox.getChildren().add(tempTxt);

            tempVBox.getChildren().add(tempHBox);

            TextButton bidButton = new TextButton(Sizes.getButtonWidth() / 2,Sizes.getButtonHeight(), Street.GREEN.getColor(),"BID");
            bidButton.addEventHandler(MouseEvent.MOUSE_CLICKED,e -> {
                if (model.addBid(new Bid(p,Integer.parseInt(bidField.getText())))) {
                    maxBid.setText(model.getHighestBid().getPlayer().getName().toUpperCase() + "BID: $" + model.getHighestBid().getAmount());
                }
                bidField.setText("");
            });
            tempVBox.getChildren().add(bidButton);
            buttonMap.put(p,bidButton);

            bidButtons.getChildren().add(tempVBox);
        }

        add(bidButtons,0,3,2,1);
    }

    public void start() {
        timer.start();
    }

    @Override
    public int getEndLatency() {
        return 0;
    }

    @Override
    public boolean isFinished() {
        return timer.isFinished();
    }
}
