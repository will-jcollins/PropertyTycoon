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

import java.util.ArrayList;

/**
 * Class for creating gameOverMenu
 */
public class GameOverMenu extends Menu {

    private boolean finished;

    public GameOverMenu(ArrayList<Player> players) {
        super();

        Text title =  new Text(players.get(0).getName() + " WINS!");
        title.setFont(TITLE_FONT);
        title.setFill(Color.BLACK);
        getChildren().add(title);
        setColumnIndex(title,0);
        setColumnSpan(title,2);
        setRowIndex(title, 0);
        setHalignment(title, HPos.CENTER);

        TextButton gameOver = new TextButton(Sizes.getButtonWidth(),Sizes.getButtonHeight(), Street.DEEPBLUE.getColor(), "GAME OVER!");
        getChildren().add(gameOver);
        setColumnIndex(gameOver,0);
        setRowIndex(gameOver, 1);
        setHalignment(gameOver, HPos.CENTER);
        gameOver.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    finished = true;
                    System.exit(0);
                }
        );

        setStyle("");
        setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

}
