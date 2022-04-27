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
import model.board.BuyableTile;
import model.board.Street;
import ui.Sizes;

import java.util.ArrayList;

public class SellingMenu extends Menu
{
    private BuyableTile outcome;
    protected boolean finished = false;
    protected TextButton exitButton;

    public SellingMenu(ArrayList<BuyableTile> ownedProperties)
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

        for (BuyableTile buyable: ownedProperties)
        {
            HBox row = new HBox();
            row.setAlignment(Pos.CENTER);
            row.setSpacing(PADDING);

            BuyableCard card = new BuyableCard(buyable,Sizes.getCardSize());
            row.getChildren().add(card);

            VBox buttonAndText = new VBox();
            buttonAndText.setAlignment(Pos.CENTER);
            buttonAndText.setSpacing(PADDING);

            TextButton sell = new TextButton(Sizes.getButtonWidth(),Sizes.getButtonHeight(),Street.GREEN.getColor(),"SELL FOR " + buyable.getCost()/2);
            sell.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                outcome = buyable;
                finished = true;
            });
            buttonAndText.getChildren().add(sell);

            row.getChildren().add(buttonAndText);
            root.getChildren().add(row);
        }

        scrollView.setContent(root);
        getChildren().add(scrollView);
        setColumnIndex(scrollView,0);
        setRowIndex(scrollView,0);
        setColumnSpan(scrollView,2);
        setRowSpan(scrollView,2);

        exitButton = new TextButton(Sizes.getButtonWidth(),Sizes.getButtonHeight(), Street.RED.getColor(), "CANCEL");
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            outcome = null;
            finished = true;
        });

        getChildren().add(exitButton);
        setColumnIndex(exitButton,0);
        setRowIndex(exitButton,2);
        setRowSpan(exitButton,2);
        setHalignment(exitButton, HPos.CENTER);
        setValignment(exitButton, VPos.CENTER);
    }

    public BuyableTile getOutcome() {
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
