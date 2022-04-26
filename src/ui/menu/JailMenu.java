package ui.menu;

import javafx.geometry.HPos;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Player.Player;
import model.board.Street;
import model.game.Game;
import model.game.JailOption;
import ui.Sizes;

/**
 * Class responsible for creating JailMenu, for player already in jail
 */
public class JailMenu extends Menu {

    private boolean finished = false;
    private JailOption outcome;

    public JailMenu(Player player) {
        super();

        TextButton waitButton = new TextButton(Sizes.getButtonWidth(),Sizes.getButtonHeight(), Street.GREEN.getColor(), "WAIT");
        add(waitButton,0,0);
        waitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    outcome = JailOption.WAIT;
                    finished = true;
                }
        );
        addOption(waitButton);

        TextButton rollButton = new TextButton(Sizes.getButtonWidth(),Sizes.getButtonHeight(), Street.DEEPBLUE.getColor(), "ROLL");
        add(rollButton,1,0);
        rollButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    finished = true;
                    outcome = JailOption.ROLL_DICE;
                }
        );
        addOption(rollButton);

        TextButton payButton;
        if (player.getMoney() > Game.JAIL_COST) {
            payButton = new TextButton(Sizes.getButtonWidth(), Sizes.getButtonHeight(), Street.DEEPBLUE.getColor(), "PAY");
            payButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        finished = true;
                        outcome = JailOption.PAY;
                    }
            );
            addOption(payButton);
        } else {
            payButton = new TextButton(Sizes.getButtonWidth(), Sizes.getButtonHeight(), Color.GRAY, "PAY");
        }
        add(payButton, 0, 1);

        Text cost = new Text("COST: $" + Game.JAIL_COST);
        cost.setFont(TITLE_FONT);
        cost.setFill(Color.BLACK);
        cost.setTextAlignment(TextAlignment.CENTER);
        setHalignment(cost, HPos.CENTER);
        add(cost,0,2);

        TextButton cardButton;
        if (player.getJailCards() > 0) {
            cardButton = new TextButton(Sizes.getButtonWidth(), Sizes.getButtonHeight(), Street.DEEPBLUE.getColor(), "USE CARD");
            cardButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        finished = true;
                        outcome = JailOption.JAILCARD;
                    }
            );
            addOption(cardButton);
        } else {
            cardButton = new TextButton(Sizes.getButtonWidth(), Sizes.getButtonHeight(), Color.GRAY, "USE CARD");
        }
        add(cardButton, 1, 1);
    }

    @Override
    public int getEndLatency() {
        return 0;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    public JailOption getOutcome() {
        return outcome;
    }
}
