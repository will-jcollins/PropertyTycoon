package ui.menu;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Player.Player;
import ui.Sizes;
import ui.player.UIPlayers;

public class TurnMenu extends Menu {

    public TurnMenu(Player player) {

        ImageView icon = new ImageView(UIPlayers.PLAYER_IMGS[player.getId()]);
        icon.setFitWidth(Sizes.getTokenSize() * 1.5);
        icon.setFitHeight(Sizes.getTokenSize() * 1.5);
        add(icon,0,0);

        Text title = new Text(player.getName().toUpperCase() + "'S TURN");
        title.setFont(TITLE_FONT);
        title.setFill(Color.BLACK);
        add(title,1,0);

        setHgap(Sizes.getPadding());
    }

    @Override
    public int getEndLatency() {
        return 2000;
    }
}
