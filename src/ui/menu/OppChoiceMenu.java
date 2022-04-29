package ui.menu;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Player.Player;
import model.board.Street;
import ui.Sizes;

public class OppChoiceMenu extends Menu {

    boolean finished;
    boolean outcome;

    public OppChoiceMenu(Player player) {
        super();

        Text title = new Text(player.getName().toUpperCase() + ", FEELING LUCKY?");
        title.setFont(TITLE_FONT);
        title.setFill(Color.BLACK);
        getChildren().add(title);
        setRowIndex(title, 0);
        setColumnIndex(title, 0);
        setColumnSpan(title, 2);
        setMargin(title, new Insets(0, 0, PADDING * 2, 0));
        setHalignment(title, HPos.CENTER);

        TextButton choosePay;
        if (player.getMoney() >= 10) {
            choosePay = new TextButton(Sizes.getButtonWidth(), Sizes.getButtonHeight(), Street.RED.getColor(), "PAY $10");
            choosePay.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                finished = true;
                outcome = false;
            });
            addOption(choosePay);
        } else {
            choosePay = new TextButton(Sizes.getButtonWidth(), Sizes.getButtonHeight(), Color.GRAY, "PAY $10");
        }
        getChildren().add(choosePay);
        setColumnIndex(choosePay,1);
        setRowIndex(choosePay,3);

        TextButton chooseOpp = new TextButton(Sizes.getButtonWidth(),Sizes.getButtonHeight(), Street.DEEPBLUE.getColor(), "OPPORTUNITY");
        getChildren().add(chooseOpp);
        setColumnIndex(chooseOpp,0);
        setRowIndex(chooseOpp,3);
        chooseOpp.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            finished = true;
            outcome = true;
        });



    }

    public boolean getOutcome() {
        return outcome;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
