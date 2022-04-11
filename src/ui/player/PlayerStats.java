package ui.player;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Player.Player;
import ui.menu.BalanceText;

public class PlayerStats extends GridPane {

    private final static int ICON_LENGTH = 50;
    private final static int ICON_STROKE = 5;
    private final static int PADDING = 5;

    protected static final Color BACKCOLOR = new Color(0.75,0.86,0.68,1.0);

    protected static final Font NAME_FONT = Font.loadFont("file:assets/fonts/Kabel.ttf", 20);
    protected static final Font MONEY_FONT = Font.loadFont("file:assets/fonts/Kabel.ttf", 17);

    private Player player;
    private BalanceText balance;

    public PlayerStats(Player player) {

        this.player = player;

        ImageView iconImage = new ImageView();
        iconImage.setFitHeight(ICON_LENGTH);
        iconImage.setFitWidth(ICON_LENGTH);

        BorderPane icon = new BorderPane(iconImage);
        icon.setStyle("-fx-border-color: black;\n" +
        "-fx-border-style: solid;\n" +
        "-fx-border-width: " + ICON_STROKE + ";");
        icon.maxHeight(ICON_LENGTH);
        icon.maxHeight(ICON_LENGTH);
        getChildren().add(icon);
        setColumnIndex(icon, 0);
        setRowIndex(icon, 0);
        setMargin(icon, new Insets(PADDING, PADDING, PADDING, PADDING));

        Text nameDisplay = new Text(player.getName().toUpperCase());
        nameDisplay.maxHeight(ICON_LENGTH);
        nameDisplay.setFont(NAME_FONT);
        getChildren().add(nameDisplay);
        setRowIndex(nameDisplay,0);
        setColumnIndex(nameDisplay,1);
        setMargin(nameDisplay, new Insets(PADDING, PADDING, 0, PADDING));

        balance = new BalanceText(player.getMoney(),player.getMoney());
        balance.maxHeight(ICON_LENGTH);
        balance.setFont(MONEY_FONT);
        getChildren().add(balance);
        setRowIndex(balance, 1);
        setColumnIndex(balance, 0);
        setColumnSpan(balance, 2);
        setMargin(balance, new Insets(PADDING, PADDING, PADDING, PADDING));

        setBackground(new Background(new BackgroundFill(BACKCOLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        setStyle("-fx-border-color: black;\n" +
                "-fx-border-style: solid;\n" +
                "-fx-border-width: " + ICON_STROKE + ";");

        setMaxHeight(100);
        setMaxWidth(200);
    }

    public void update() {

        if (player.getMoney() != balance.getEndVal()) {
            balance.update(balance.getEndVal(),player.getMoney());
            Platform.runLater(() -> balance.animateText());
        }

    }
}
