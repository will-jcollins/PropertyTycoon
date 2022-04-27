package ui.menu;

import javafx.geometry.HPos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.board.Street;
import model.game.Game;
import ui.Sizes;
/**
 * Class responisible for building End turn menu
 * This class persents player with the option to end turn or to develop any of the properties which they own
 */
public class TurnEndMenu extends Menu {

    private boolean outcome = false;
    private boolean finished = false;
    /**
     * Constructor of the TurnEndMenu class
     */
    public TurnEndMenu(Game model) {
        super();

        TextButton endTurn = new TextButton(Sizes.getButtonWidth(),Sizes.getButtonHeight(), Street.GREEN.getColor(), "END TURN");
        getChildren().add(endTurn);
        setColumnIndex(endTurn,1);
        setRowIndex(endTurn, 0);
        endTurn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            outcome = true;
            finished = true;
            }
        );
        addOption(endTurn);

        TextButton develop = new TextButton(Sizes.getButtonWidth(),Sizes.getButtonHeight(), Street.DEEPBLUE.getColor(), "DEVELOP");
        getChildren().add(develop);
        setColumnIndex(develop,0);
        setRowIndex(develop, 0);
        develop.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    outcome = false;
                    finished = true;
                }
        );

        setStyle("");
        setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));

        if (model.getDevelopProperties(model.getCurrentPlayer()).size() > 0) {
            addOption(develop);
        }
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
