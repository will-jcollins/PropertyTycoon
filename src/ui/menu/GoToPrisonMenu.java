package ui.menu;

import javafx.geometry.HPos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Player.Player;
import model.board.Street;
import ui.Sizes;

/**
 * Class for creating GoToPrisonMenu
 */
public class GoToPrisonMenu extends Menu
{

    private boolean finished = false;
    public GoToPrisonMenu(Player player)
    {
        super();

        Text title = new Text (player.getName() + ", GO TO JAIL");

        title.setFont(TITLE_FONT);
        title.setFill(Color.BLACK);
        getChildren().add(title);
        setColumnIndex(title,0);
        setRowIndex(title, 0);
        setHalignment(title, HPos.CENTER);

        TextButton contButton = new TextButton(Sizes.getButtonWidth(), Sizes.getButtonHeight(), Street.RED.getColor(), "CONTINUE");
        getChildren().add(contButton);
        setColumnIndex(contButton,0);
        setRowIndex(contButton,1);
        setHalignment(contButton,HPos.CENTER);

        contButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> finished = true);

    }

    @Override
    public boolean isFinished()
    {
        return finished;
    }

    @Override
    public int getEndLatency() {
        return 0;
    }
}
