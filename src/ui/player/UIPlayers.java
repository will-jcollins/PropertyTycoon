package ui.player;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import model.Player.Player;
import model.board.Board;
import ui.Sizes;
import ui.board.UIBoard;

import java.util.HashMap;
import java.util.List;
/**
 * Class responsible for crating and moving player tokens
 * @Author Will Collins
 */
public class UIPlayers extends Group {

    private static final double OPACITY = 0.5;

    public static final Color[] PLAYER_COLORS = {new Color(0.58,0.67,0.59,1), new Color(0.78,0.52,0.6,1), Color.BLUE, Color.GREEN, Color.PINK, Color.PURPLE};
    public static final String[] PLAYER_IMGS = {"file:assets/images/token1.png","file:assets/images/token2.png","file:assets/images/token3.png","file:assets/images/token4.png","file:assets/images/token5.png","file:assets/images/token6.png",};
    private HashMap<Player, ImageView> tokens;

    private boolean finished = false;
    /**
     * Constructor of UIPlayers class
     * @param players List of players in the game
     * @param board Instance of UIBoard
     */
    public UIPlayers(List<Player> players, UIBoard board) {
        this.tokens = new HashMap<>();

        // Invisible nodes at top left and bottom right corner
        // Forces player tokens into correct position
        Line startBound = new Line();
        startBound.setStartX(1);
        startBound.setStartY(2);
        startBound.setEndX(2);
        startBound.setEndY(2);
        startBound.setOpacity(2);
        getChildren().add(startBound);

        Line endBound = new Line();
        endBound.setStartX(board.getSize() - 1);
        endBound.setStartY(board.getSize() - 1);
        endBound.setEndX(board.getSize() - 1);
        endBound.setEndY(board.getSize() - 1);
        endBound.setOpacity(0);
        getChildren().add(endBound);


        for (int i = 0; i < players.size(); i++) {

            ImageView tempImg = new ImageView(PLAYER_IMGS[players.get(i).getId()]);
            tempImg.setFitWidth(Sizes.getTokenSize());
            tempImg.setFitHeight(Sizes.getTokenSize());
            tempImg.setOpacity(OPACITY);
            getChildren().add(tempImg);

            tokens.put(players.get(i), tempImg);
        }

        initPlayers(board);
    }
    /**
     * Method responsible for matching player with tokens
     * @param board
     */
    private void initPlayers(UIBoard board) {
        for (Player player : tokens.keySet()) {
            ImageView token = tokens.get(player);
            token.setX(board.getXTilePos(player.getPos()) - token.getBoundsInLocal().getWidth() / 2);
            token.setY(board.getYTilePos(player.getPos()) - token.getBoundsInLocal().getHeight() / 2);
        }
    }
    /**
     * Method responisble for updating player position on the board
     * @param player Instance of class Player
     * @param board Instance of class UIBoard
     * @param onFinish EventHandler responisble for animation of the player movement
     * @throws InterruptedException
     */
    public void updatePlayers(Player player, UIBoard board, EventHandler onFinish) {
        finished = false;

        // Move pieces to the correct tile, changing tile player is positioned on by 1 at a time
        ImageView token = tokens.get(player);
        SequentialTransition seqTransition = new SequentialTransition(token);


        if (player.getTurnsInJail() == 1) {
            // Move player into jail on their first turn
            TranslateTransition transTransition = new TranslateTransition(Duration.millis(500));
            transTransition.setByX(board.getXJailPos() - board.getXTilePos(player.getPos()));
            transTransition.setByY(board.getYJailPos() - board.getYTilePos(player.getPos()));
            seqTransition.getChildren().add(transTransition);
        } else if (player.hasLeftJail()) {
            // Move player into jail on their first turn
            TranslateTransition transTransition = new TranslateTransition(Duration.millis(500));
            transTransition.setByX(board.getXTilePos(player.getPos()) - board.getXJailPos());
            transTransition.setByY(board.getYTilePos(player.getPos()) - board.getYJailPos());
            seqTransition.getChildren().add(transTransition);
        } else if (!player.inJail()) {
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
        }

        // Play transitions constructed in sequence
        seqTransition.play();
        seqTransition.setOnFinished(onFinish);
    }

    /**
     * Method responisible for highlighting player whose move is currently on
     * @param p Instance of class Player
     */
    public void higlightPlayer(Player p) {
        ImageView token = tokens.get(p);
        token.toFront();
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
