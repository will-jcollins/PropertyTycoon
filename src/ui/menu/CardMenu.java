package ui.menu;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import model.board.Card;

/**
 * Class describing card menu
 */
public class CardMenu extends Menu {

    public CardMenu(String title, String description) {
        Text titleTxt = new Text(title);
        titleTxt.setFont(TITLE_FONT);
        add(titleTxt,0,0);

        Text descTxt = new Text(description);
        descTxt.setFont(TEXT_FONT);
        add(descTxt,0,1);
    }

    @Override
    public int getEndLatency() {
        return 3000;
    }
}
