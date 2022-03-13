package ui.menu;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Player.Player;
import model.board.BuyableTile;

public class RentMenu extends GridPane {

    private static final int HEIGHT = 300;
    private static final int WIDTH = 300;
    private static final int PADDING = 10;
    private static final Color BACKGROUND_COLOR = new Color(1,1,1,1);

    private static final Font TITLE_FONT = Font.loadFont("file:assets/fonts/Kabel.ttf", 20);
    private static final Font TEXT_FONT = Font.loadFont("file:assets/fonts/Kabel.ttf", 15);

    private BalanceText receivingBalance;
    private BalanceText payingBalance;

    public RentMenu(BuyableTile tile, Player payingPlayer) {
        super();

        Text title =  new Text("PAY RENT TO " + tile.getOwner().getName().toUpperCase());
        title.setFont(TITLE_FONT);
        title.setFill(Color.BLACK);
        title.setStrokeWidth(0.5);
        title.setStroke(Color.BLACK);
        getChildren().add(title);
        setColumnIndex(title,0);
        setColumnSpan(title,3);
        setRowIndex(title, 0);
        setHalignment(title, HPos.CENTER);

        BuyableCard card = new BuyableCard(tile);
        getChildren().add(card);
        setColumnIndex(card, 1);
        setRowIndex(card, 1);
        setHalignment(card, HPos.CENTER);

        Text receivingName =  new Text(tile.getOwner().getName().toUpperCase());
        receivingName.setFont(TITLE_FONT);
        receivingName.setFill(Color.BLACK);
        receivingName.setStrokeWidth(0.5);
        receivingName.setStroke(Color.BLACK);

        receivingBalance =  new BalanceText(tile.getOwner().getPrevMoney(),tile.getOwner().getMoney(),15);

        VBox receivingNodes = new VBox(receivingName,receivingBalance);
        getChildren().add(receivingNodes);
        setColumnIndex(receivingNodes,2);
        setRowIndex(receivingNodes, 2);
        setHalignment(receivingNodes, HPos.CENTER);
        setValignment(receivingNodes, VPos.BOTTOM);

        Text payingName =  new Text(payingPlayer.getName().toUpperCase());
        payingName.setFont(TITLE_FONT);
        payingName.setFill(Color.BLACK);
        payingName.setStrokeWidth(0.5);
        payingName.setStroke(Color.BLACK);

        payingBalance =  new BalanceText(payingPlayer.getPrevMoney(),payingPlayer.getMoney(),15);

        VBox payingNodes = new VBox(payingName,payingBalance);
        getChildren().add(payingNodes);
        setColumnIndex(payingNodes,0);
        setRowIndex(payingNodes, 2);
        setHalignment(payingNodes, HPos.CENTER);
        setValignment(payingNodes, VPos.BOTTOM);

        Arrow arrow = new Arrow(100, 20);
        getChildren().add(arrow);
        setColumnIndex(arrow, 1);
        setRowIndex(arrow, 2);
        setHalignment(arrow,HPos.CENTER);
        setValignment(arrow, VPos.CENTER);


        setStyle("-fx-border-style: solid inside;" +
                "-fx-border-width: 3;" +
                "");

        setPadding(new Insets(PADDING,PADDING * 2,PADDING,PADDING * 2));
        setHgap(PADDING);
        setVgap(PADDING);
        setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR,null,null)));
        setAlignment(Pos.BASELINE_CENTER);
        setMaxHeight(0);
        setMaxWidth(0);
    }

    public void startAnimation() {
        Task animateTask = new Task() {
            @Override
            protected Object call() throws Exception {
                Thread.sleep(1000);
                payingBalance.animateText();
                receivingBalance.animateText();
                return null;
            }
        };
        Thread animateThread = new Thread(animateTask);
        animateThread.setDaemon(true);
        animateThread.start();
    }

    public boolean isFinished() {
        return payingBalance.isFinished() && receivingBalance.isFinished();
    }
}
