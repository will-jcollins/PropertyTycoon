package ui.menu;

import javafx.geometry.HPos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Player.Player;
import model.board.Street;
import model.game.Game;
import ui.Sizes;

import java.util.Locale;

/**
 * Class for creating Bankrupt menu
 */
public class BankruptMenu extends SellingMenu {

    private boolean concede = false;

    /**
     * Constructor of BunkruptMenu class
     * @param model - game object representing current monopoly game
     */
    public BankruptMenu(Game model) {
        super(model.ownedByPlayer(model.getCurrentPlayer()));

        // Prevent player from being able to exit the menu without conceding
        getChildren().remove(exitButton);

        Text nameTxt = new Text(model.getCurrentPlayer().getName().toUpperCase() + " IS BANKRUPT\nSELL PROPERTIES OR CONCEDE");
        nameTxt.setFont(TITLE_FONT);
        nameTxt.setTextAlignment(TextAlignment.CENTER);
        nameTxt.setLineSpacing(Sizes.getLineSpacing());
        add(nameTxt,0,4);

        TextButton concedeButton = new TextButton(Sizes.getButtonWidth(),Sizes.getButtonHeight(), Street.RED.getColor(), "CONCEDE");
        add(concedeButton,1,4);
        setHalignment(concedeButton, HPos.CENTER);
        concedeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            finished = true;
            concede = true;
        });
    }

    public boolean didConcede() {
        return concede;
    }
}
