package ui.menu;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import model.Player.Player;
import model.board.PropertyTile;
import model.board.Street;
import model.game.Game;
import ui.Sizes;

import java.util.ArrayList;
/**
 * Class for building develop menu, used to build houses and hotels on the property
 */
public class DevelopMenu extends Menu {

    private PropertyTile selectedProperty;
    private boolean finished = false;

    /**
     * Constructor of DevelopMenu class
     * @param properties ArrrayList of properties
     * @param p Instance of Player class
     */
    public DevelopMenu(ArrayList<PropertyTile> properties, Player p) {
        super();
        ScrollPane scrollView = new ScrollPane();
        scrollView.setFitToHeight(true);
        scrollView.setFitToWidth(true);
        scrollView.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollView.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollView.setMinViewportHeight(Sizes.getMenuSize());
        scrollView.setMinViewportWidth(Sizes.getMenuSize());
        scrollView.setStyle("-fx-background: transparent; -fx-background-color: transparent; ");

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);

        if (properties.size() <= 0) {
            Text noDevelopTxt = new Text("NOTHING TO DEVELOP");
            noDevelopTxt.setFont(TITLE_FONT);
            root.getChildren().add(noDevelopTxt);
        }

        for (PropertyTile prop : properties) {
            HBox row = new HBox();
            row.setAlignment(Pos.CENTER);
            row.setSpacing(PADDING);

            BuyableCard card = new BuyableCard(prop,Sizes.getCardSize());
            row.getChildren().add(card);

            VBox buttonAndText = new VBox();
            buttonAndText.setAlignment(Pos.CENTER);
            buttonAndText.setSpacing(PADDING);

            TextButton develop;

            if (p.getMoney() >= prop.getStreet().getDevelopCost()) {
                develop = new TextButton(Sizes.getButtonWidth(),Sizes.getButtonHeight(), Street.GREEN.getColor(),"DEVELOP");
                develop.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    selectedProperty = prop;
                    finished = true;
                });
                buttonAndText.getChildren().add(develop);
            } else {
                develop = new TextButton(Sizes.getButtonWidth(),Sizes.getButtonHeight(), Color.GRAY,"DEVELOP");
                buttonAndText.getChildren().add(develop);

                Text lowBalanceTxt = new Text("BALANCE TOO LOW");
                lowBalanceTxt.setFont(TEXT_FONT);
                buttonAndText.getChildren().add(lowBalanceTxt);
            }



            int houses = Math.min(prop.getNoHouses(),PropertyTile.MAX_NO_HOUSES - 1);
            int hotels = prop.getNoHouses() / 5;
            Text noHouses = new Text(houses + " HOUSES, " + hotels + " HOTELS");
            noHouses.setFont(TEXT_FONT);
            buttonAndText.getChildren().add(noHouses);

            row.getChildren().add(buttonAndText);
            root.getChildren().add(row);
        }

        scrollView.setContent(root);
        getChildren().add(scrollView);
        setColumnIndex(scrollView,0);
        setRowIndex(scrollView,0);
        setColumnSpan(scrollView,2);
        setRowSpan(scrollView,2);

        TextButton exitButton = new TextButton(Sizes.getButtonWidth(),Sizes.getButtonHeight(),Street.RED.getColor(), "CANCEL");
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {
            selectedProperty = null;
            finished = true;
        });
        getChildren().add(exitButton);
        setColumnIndex(exitButton,0);
        setRowIndex(exitButton,2);
        setRowSpan(exitButton,2);
        setHalignment(exitButton, HPos.CENTER);
        setValignment(exitButton, VPos.CENTER);
    }

    public PropertyTile getSelectedProperty() {
        return selectedProperty;
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
