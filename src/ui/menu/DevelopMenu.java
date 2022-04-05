package ui.menu;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import model.board.PropertyTile;
import model.board.Street;
import model.game.Game;

import java.util.ArrayList;

public class DevelopMenu extends Menu {

    private PropertyTile selectedProperty;
    private boolean finished = false;

    public DevelopMenu(ArrayList<PropertyTile> properties) {
        super();
        ScrollPane scrollView = new ScrollPane();
        scrollView.setFitToHeight(true);
        scrollView.setFitToWidth(true);
        scrollView.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollView.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollView.setMinViewportHeight(Screen.getPrimary().getBounds().getWidth() / 4);
        scrollView.setMinViewportWidth(Screen.getPrimary().getBounds().getWidth() / 4);
        scrollView.setStyle("-fx-background: transparent; -fx-background-color: transparent; ");

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);

        if (properties.size() <= 0) {
            Text noPropertiesTxt = new Text("NOTHING TO DEVELOP");
            noPropertiesTxt.setFont(TITLE_FONT);
            root.getChildren().add(noPropertiesTxt);
        }

        for (PropertyTile prop : properties) {
            HBox row = new HBox();
            row.setAlignment(Pos.CENTER);
            row.setSpacing(PADDING);

            BuyableCard card = new BuyableCard(prop);
            row.getChildren().add(card);

            VBox buttonAndText = new VBox();
            buttonAndText.setAlignment(Pos.CENTER);
            buttonAndText.setSpacing(PADDING);

            TextButton develop = new TextButton(150,50, Street.GREEN.getColor(),"DEVELOP");
            develop.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                selectedProperty = prop;
                finished = true;
            });
            buttonAndText.getChildren().add(develop);

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

        TextButton exitButton = new TextButton(150,50,Street.RED.getColor(), "CANCEL");
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
