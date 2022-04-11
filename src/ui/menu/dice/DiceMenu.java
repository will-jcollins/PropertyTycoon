package ui.menu.dice;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.board.Street;
import model.game.Dice;
import ui.Sizes;
import ui.menu.Menu;
import ui.menu.TextButton;


public class DiceMenu extends Menu {

    private UIDie die1;
    private UIDie die2;

    private boolean started = false;

    public DiceMenu(Dice dice) {
        super();

        Text title =  new Text("ROLL THE DICE");
        title.setFont(TITLE_FONT);
        title.setFill(Color.BLACK);
        getChildren().add(title);
        setColumnIndex(title,0);
        setColumnSpan(title,2);
        setRowIndex(title, 0);
        setHalignment(title, HPos.CENTER);

        die1 = new UIDie(Sizes.getDiceSize());
        getChildren().add(die1);
        setColumnIndex(die1,0);
        setRowIndex(die1,1);

        die2 = new UIDie(Sizes.getDiceSize());
        getChildren().add(die2);
        setColumnIndex(die2,1);
        setRowIndex(die2,1);

        TextButton accept = new TextButton(Sizes.getButtonWidth(),Sizes.getButtonHeight(), Street.GREEN.getColor(), "ROLL");
        getChildren().add(accept);
        setColumnIndex(accept,0);
        setColumnSpan(accept,2);
        setRowIndex(accept,2);
        setHalignment(accept, HPos.CENTER);
        accept.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {
            if (!started) {
                started = true;
                die1.animateRoll(dice.getRoll()[0]);
                die2.animateRoll(dice.getRoll()[1]);
            }
        });
    }

    @Override
    public boolean isFinished() {
        return die1.isFinished() && die2.isFinished();
    }
}
