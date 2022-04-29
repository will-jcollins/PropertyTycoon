package ui.menu;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import model.Player.Player;
import ui.Sizes;
import ui.player.UIPlayers;

/**
 * Class for menu if a player win the auction
 */
public class AuctionWonMenu extends Menu {

    public AuctionWonMenu(Player player) {
        Text wonTxt = new Text("AUCTION WON BY...");
        wonTxt.setFont(TITLE_FONT);
        setHalignment(wonTxt, HPos.CENTER);
        add(wonTxt,0,0,2,1);

        ImageView tempToken = new ImageView(UIPlayers.PLAYER_IMGS[player.getId()]);
        tempToken.setFitWidth(Sizes.getTokenSize());
        tempToken.setFitHeight(Sizes.getTokenSize());
        setHalignment(tempToken,HPos.CENTER);
        add(tempToken,0,1);

        Text tempTxt = new Text(player.getName());
        tempTxt.setFont(TITLE_FONT);
        setHalignment(tempTxt,HPos.CENTER);
        add(tempTxt, 1,1);

        setHgap(Sizes.getPadding() / 2);
    }

    @Override
    public int getEndLatency() {
        return 2000;
    }
}
