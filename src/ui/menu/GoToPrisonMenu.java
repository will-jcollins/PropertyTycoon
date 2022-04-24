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

        Text title = new Text (player.getName () + " GO TO PRISON!");

        title.setFont(TITLE_FONT);
        title.setFill(Color.BLACK);
        getChildren().add(title);
        setColumnIndex(title,0);
        setColumnSpan(title,2);
        setRowIndex(title, 0);
        setHalignment(title, HPos.CENTER);

        TextButton contiunue = new TextButton (Sizes.getButtonWidth (), Sizes.getButtonHeight (), Street.RED.getColor (), "CONTINUE");
        getChildren ().add(contiunue);
        setColumnIndex (contiunue,0);
        setRowIndex (contiunue,1);
        setHalignment (contiunue,HPos.CENTER);

        contiunue.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    finished = true;
                }
        );

        setStyle("");
        setBackground(new Background (new BackgroundFill (Color.TRANSPARENT,null,null)));

    }

    @Override
    public boolean isFinished()
    {
        return finished;
    }
}
