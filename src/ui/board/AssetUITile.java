package ui.board;

import javafx.animation.FadeTransition;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.board.BuyableTile;
import model.board.StationTile;
import ui.Sizes;
import ui.player.UIPlayers;

/**
 * Adds ownership ribbon to ImgCostUITile
 * @Author Will Collins
 */
public class AssetUITile extends ImgCostUITile {

    private static final String STATIONIMGPATH = "file:assets/images/station.png";
    private static final String UTILITYIMGPATH = "file:assets/images/utility.png";

    private BuyableTile asset;
    private OwnerRibbon ribbon;

    /**
     * Constructor of AssetUITile class
     * @param x position x
     * @param y position y
     * @param width width value
     * @param height height value
     * @param angle angile value
     * @param tile title of the card
     */
    public AssetUITile(int x, int y, double width, double height, double angle, BuyableTile tile) {
        super(x, y, width, height, angle, tile.getName(),tile.getCost(),(tile instanceof StationTile) ? STATIONIMGPATH : UTILITYIMGPATH);

        this.asset = tile;

        ribbon = new OwnerRibbon(x + width / 2 - width / 16, y + height, width / 8, height / 4);
        ribbon.setOpacity(0);
        ribbon.setStroke(Color.BLACK);
        ribbon.setStrokeWidth(Sizes.getSmallStroke());
        getChildren().add(ribbon);
        ribbon.toBack();
    }
    public void update() {
        if (asset.getOwner() != null && ribbon.getOpacity() < 1) {
            ribbon.setFill(UIPlayers.PLAYER_COLORS[asset.getOwner().getId()]);
            ribbon.setOpacity(1);
        }
    }
}
