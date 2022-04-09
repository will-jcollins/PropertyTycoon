package ui.player;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import model.Player.Player;
import model.board.Board;
import ui.board.UIBoard;

import java.util.HashMap;
import java.util.List;

public class UIPlayers extends Group {

    private static final double OPACITY = 0.5;

    public static final Color[] PLAYER_COLORS = {new Color(0.58,0.67,0.59,1), Color.GRAY, Color.BLUE, Color.GREEN, Color.PINK, Color.PURPLE};
    public static final String[] PLAYER_IMGS = {"file:assets/images/token1.png","file:assets/images/token2.png","file:assets/images/token3.png","file:assets/images/token4.png","file:assets/images/token5.png","file:assets/images/token6.png",};
    private HashMap<Player, ImageView> tokens;

    private boolean finished = false;

    public UIPlayers(List<Player> players, UIBoard board) {
        this.tokens = new HashMap<>();

        // Forces group to be positioned correctly over board
        Line startBound = new Line();
        startBound.setStartX(2);
        startBound.setStartY(2);
        startBound.setEndX(2);
        startBound.setEndY(2);
        startBound.setOpacity(2);
        getChildren().add(startBound);

        Line endBound = new Line();
        endBound.setStartX(board.getSize() - 2);
        endBound.setStartY(board.getSize() - 2);
        endBound.setEndX(board.getSize() - 2);
        endBound.setEndY(board.getSize() - 2);
        endBound.setOpacity(0);
        getChildren().add(endBound);


        for (int i = 0; i < players.size(); i++) {
//            Circle tempCirc = new Circle();
//            tempCirc.setRadius(10);
//            tempCirc.setFill(PLAYER_COLORS[i]);
//            tempCirc.setOpacity(OPACITY);
//            getChildren().add(tempCirc);

            ImageView tempImg = new ImageView(PLAYER_IMGS[players.get(i).getId()]);
            tempImg.setFitWidth(35);
            tempImg.setFitHeight(35);
            tempImg.setOpacity(OPACITY);
            getChildren().add(tempImg);

            tokens.put(players.get(i), tempImg);
        }

        initPlayers(board);
    }

    private void initPlayers(UIBoard board) {
        for (Player player : tokens.keySet()) {
            ImageView token = tokens.get(player);
            token.setX(board.getXTilePos(player.getPos()) - token.getBoundsInLocal().getWidth() / 2);
            token.setY(board.getYTilePos(player.getPos()) - token.getBoundsInLocal().getHeight() / 2);
        }
    }

    public void updatePlayers(Player player, UIBoard board, EventHandler onFinish) throws InterruptedException {
        finished = false;

        // Move pieces to the correct tile, changing tile player is positioned on by 1 at a time
        ImageView token = tokens.get(player);
        SequentialTransition seqTransition = new SequentialTransition(token);

        // Build all transitions required to move token to next position one by one
        int nextPos = player.getPrevPos();
        int prevPos = nextPos;

        while (nextPos != player.getPos()) {
            nextPos = (nextPos + 1) % Board.SIZE;

            TranslateTransition transTransition = new TranslateTransition(Duration.millis(500));
            transTransition.setByX(board.getXTilePos(nextPos) - board.getXTilePos(prevPos));
            transTransition.setByY(board.getYTilePos(nextPos) - board.getYTilePos(prevPos));
            seqTransition.getChildren().add(transTransition);

            prevPos = nextPos;
        }

        // Play transitions constructed in sequence
        seqTransition.play();
        seqTransition.setOnFinished(onFinish);
    }

    public void higlightPlayer(Player p) {
        ImageView token = tokens.get(p);
        FadeTransition inFade = new FadeTransition(Duration.millis(500),token);
        inFade.setFromValue(token.getOpacity());
        inFade.setToValue(1);
        inFade.play();
    }

    public void dismissPlayer(Player p) {
        ImageView token = tokens.get(p);
        FadeTransition outFade = new FadeTransition(Duration.millis(500),token);
        outFade.setFromValue(1);
        outFade.setToValue(OPACITY);
        outFade.play();
    }

    public boolean isFinished() {
        return finished;
    }
}
