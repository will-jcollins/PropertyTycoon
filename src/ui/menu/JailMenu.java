package ui.menu;

import javafx.scene.input.MouseEvent;
import model.board.Street;
import model.game.JailOption;
import ui.Sizes;

/**
 * Class responsible for creating JailMenu, for player already in jail
 */
public class JailMenu extends Menu {

    private boolean finished = false;
    private JailOption outcome;

    public JailMenu(boolean canUseJailcard) {
        super();

        TextButton waitButton = new TextButton(Sizes.getButtonWidth(),Sizes.getButtonHeight(), Street.GREEN.getColor(), "WAIT");
        add(waitButton,0,0);
        waitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    outcome = JailOption.WAIT;
                    finished = true;
                }
        );

        TextButton rollButton = new TextButton(Sizes.getButtonWidth(),Sizes.getButtonHeight(), Street.DEEPBLUE.getColor(), "ROLL");
        add(rollButton,1,0);
        rollButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    finished = true;
                    outcome = JailOption.ROLL_DICE;
                }
        );

        TextButton payButton = new TextButton(Sizes.getButtonWidth(),Sizes.getButtonHeight(), Street.DEEPBLUE.getColor(), "PAY");
        add(payButton,0,1);
        payButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    finished = true;
                    outcome = JailOption.PAY;
                }
        );

        if (canUseJailcard) {
            TextButton cardButton = new TextButton(Sizes.getButtonWidth(), Sizes.getButtonHeight(), Street.DEEPBLUE.getColor(), "USE CARD");
            add(cardButton, 1, 1);
            cardButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        finished = true;
                        outcome = JailOption.JAILCARD;
                    }
            );
        }
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
