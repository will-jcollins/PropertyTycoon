package ui.board;

import javafx.animation.FadeTransition;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.board.BuyableTile;
import ui.player.UIPlayers;

public class AssetUITile extends ImgCostUITile {

    private BuyableTile asset;
    private OwnerRibbon ribbon;

    public AssetUITile(int x, int y, double width, double height, double angle, BuyableTile tile) {
        super(x, y, width, height, angle, tile);

        this.asset = tile;

        ribbon = new OwnerRibbon(x + width / 2 - width / 16, y + height, width / 8, height / 4);
        ribbon.setOpacity(0);
        ribbon.setStroke(Color.BLACK);
        ribbon.setStrokeWidth(STROKEWIDTH / 2);
        ribbon.toBack();
        getChildren().add(ribbon);
    }

    public void update() {
        if (asset.getOwner() != null && ribbon.getOpacity() < 1) {
            ribbon.setFill(UIPlayers.PLAYER_COLORS[asset.getOwner().getId()]);
            ribbon.setOpacity(1);
        }
    }
}
