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

import javax.swing.*;

/**
 * Class responisible for building End turn menu
 * This class persents player with the option to end turn or to develop any of the properties which they own
 */
public class TurnEndMenu extends Menu {

    private short outcome = 0;
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
            outcome = 0;
            finished = true;
            }
        );
        addOption(endTurn);

        TextButton develop = new TextButton(Sizes.getButtonWidth(),Sizes.getButtonHeight(), Street.DEEPBLUE.getColor(), "DEVELOP");
        getChildren().add(develop);
        setColumnIndex(develop,0);
        setRowIndex(develop, 0);
        develop.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    outcome = 1;
                    finished = true;
                }
        );


        TextButton mortageMenu  =new TextButton(Sizes.getButtonWidth(),Sizes.getButtonHeight(),Street.ORANGE.getColor(), "MORTGAGE");
        getChildren().add(mortageMenu);
        setColumnIndex(mortageMenu, 0);
        setRowIndex(mortageMenu,1);
        mortageMenu.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            outcome = 2;
            finished = true;
        });

        TextButton sellMenu = new TextButton(Sizes.getButtonWidth(), Sizes.getButtonHeight(),Street.RED.getColor(), "SELL");
        getChildren().add(sellMenu);
        setColumnIndex(sellMenu,1);
        setRowIndex(sellMenu,1);
        sellMenu.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {
            outcome = 3;
            finished = true;
        });

        setStyle("");
        setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));

        if (model.getDevelopProperties(model.getCurrentPlayer()).size() > 0) {
            addOption(develop);
        }
    }

    public short getOutcome() {
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
