package ui.player;

import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import model.Player.Player;
import model.board.Board;
import ui.board.UIBoard;
import ui.board.UITile;

import java.util.ArrayList;
import java.util.HashMap;

public class UIPlayers extends Group {

    private static final Color[] PLAYER_COLORS = {Color.RED, Color.PINK, Color.BLUE, Color.GREEN, Color.PINK, Color.PURPLE};
    private HashMap<Player, Circle> tokens;

    public UIPlayers(ArrayList<Player> players, UIBoard board) {
        this.tokens = new HashMap<>();

        Line startBound = new Line();
        startBound.setStartX(0);
        startBound.setStartY(0);
        startBound.setEndX(0);
        startBound.setEndY(0);
        startBound.setOpacity(0);
        getChildren().add(startBound);

        Line endBound = new Line();
        endBound.setStartX(810);
        endBound.setStartY(810);
        endBound.setEndX(810);
        endBound.setEndY(810);
        endBound.setOpacity(0);
        getChildren().add(endBound);


        for (int i = 0; i < players.size(); i++) {
            Circle tempCirc = new Circle();
            tempCirc.setRadius(10);
            tempCirc.setFill(PLAYER_COLORS[i]);
            tempCirc.setOpacity(0.25);
            getChildren().add(tempCirc);

            tokens.put(players.get(i), tempCirc);
        }

        updatePlayers(board);
    }

    public void updatePlayers(UIBoard board) {
        // Move pieces to the correct tile, changing tile player is positioned on by 1 at a time
        for (Player player : tokens.keySet()) {
            Circle token = tokens.get(player);

            token.setCenterX(board.getXTilePos(player.getPos()));
            token.setCenterY(board.getYTilePos(player.getPos()));
        }
    }
}
