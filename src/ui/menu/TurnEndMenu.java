package ui.menu;

import javafx.geometry.HPos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.board.Street;
import ui.Sizes;

public class TurnEndMenu extends Menu {

    private boolean outcome = false;
    private boolean finished = false;

    public TurnEndMenu() {
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
