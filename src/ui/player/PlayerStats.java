package ui.player;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Player.Player;
import ui.Sizes;
import ui.menu.BalanceText;

/**
 * Class responisble for showing player info in the top left corner
 */
public class PlayerStats extends GridPane {

    private static final Color BACKCOLOR = new Color(0.75,0.86,0.68,1.0);

    private static final Font NAME_FONT = Font.loadFont("file:assets/fonts/Kabel.ttf", Sizes.getFontHeading());
    private static final Font CAPTION_FONT = Font.loadFont("file:assets/fonts/Kabel.ttf", Sizes.getFontBody());

    private Player player;
    private BalanceText balance;
    private Text jailCards;

    /**
     * Constructor of class PlayerStats
     * @param player Instance of class Player
     */
    public PlayerStats(Player player) {

        this.player = player;

        HBox topRow = new HBox();
        topRow.setAlignment(Pos.CENTER);
        topRow.setSpacing(Sizes.getPadding() / 2);

        ImageView icon = new ImageView(UIPlayers.PLAYER_IMGS[player.getId()]);
        icon.setFitHeight(Sizes.getTokenSize());
        icon.setFitWidth(Sizes.getTokenSize());
        topRow.getChildren().add(icon);

        Text nameDisplay = new Text(player.getName().toUpperCase());
        nameDisplay.setFont(NAME_FONT);
        topRow.getChildren().add(nameDisplay);

        balance = new BalanceText(player.getMoney(),player.getMoney());
        balance.setFont(CAPTION_FONT);
        add(balance,2,0);
        topRow.getChildren().add(balance);

        add(topRow,0,0);

        jailCards = new Text(player.getJailCards() + " GET OUT OF JAIL FREE CARDS");
        jailCards.setFont(CAPTION_FONT);
        add(jailCards,0,1);

        setStyle("-fx-border-color: black;\n" +
                "-fx-border-style: solid;\n" +
                "-fx-border-width: " + Sizes.getLargeStroke() + ";");
        setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
        setPadding(new Insets(Sizes.getPadding() / 2, Sizes.getPadding() / 2, Sizes.getPadding() / 2,Sizes.getPadding() / 2));

        setHgap(Sizes.getPadding() / 2);
        setVgap(Sizes.getPadding() / 2);

        setMaxHeight(0);
        setMaxWidth(0);
    }
    /**
     * Method for updating players money
     */
    public void update() {
        balance.update(player.getMoney());
        Platform.runLater(() -> jailCards.setText(player.getJailCards() + " GET OUT OF JAIL FREE CARD" + (player.getJailCards()==1 ? "" : "S")));
    }
}
