package ui.menu;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Player.Player;
import model.board.PropertyTile;
import model.board.Street;
import ui.Sizes;

import java.util.ArrayList;

public class SellingMenu extends Menu
{
    private PropertyTile property;
    private boolean finished = false;

    public SellingMenu(ArrayList<PropertyTile> ownedProperties, Player p)
    {
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

        if (ownedProperties.size() <= 0) {
            Text noDevelopTxt = new Text("NOTHING TO SELL!");
            noDevelopTxt.setFont(TITLE_FONT);
            root.getChildren().add(noDevelopTxt);
        }

        for(PropertyTile prop: ownedProperties)
        {
            HBox row = new HBox();
            row.setAlignment(Pos.CENTER);
            row.setSpacing(PADDING);

            BuyableCard card = new BuyableCard(prop,Sizes.getCardSize());
            row.getChildren().add(card);

            VBox buttonAndText = new VBox();
            buttonAndText.setAlignment(Pos.CENTER);
            buttonAndText.setSpacing(PADDING);

            TextButton sell = new TextButton(Sizes.getButtonWidth(),Sizes.getButtonHeight(),Street.GREEN.getColor(),"SELL FOR " + String.valueOf(prop.getCost()));
            sell.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                property = prop;
                finished = true;
            });
            buttonAndText.getChildren().add(sell);

        }

        scrollView.setContent(root);
        getChildren().add(scrollView);
        setColumnIndex(scrollView,0);
        setRowIndex(scrollView,0);
        setColumnSpan(scrollView,2);
        setRowSpan(scrollView,2);

        TextButton exitButton = new TextButton(Sizes.getButtonWidth(),Sizes.getButtonHeight(), Street.RED.getColor(), "CANCEL");
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            property = null;
            finished = true;
        });

        getChildren().add(exitButton);
        setColumnIndex(exitButton,0);
        setRowIndex(exitButton,2);
        setRowSpan(exitButton,2);
        setHalignment(exitButton, HPos.CENTER);
        setValignment(exitButton, VPos.CENTER);
    }

    @Override
    public int getEndLatency() {
        return 0;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    public PropertyTile getSelectedProperty() {
        return property;
    }
}
