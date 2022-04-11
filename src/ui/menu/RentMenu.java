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
import ui.Sizes;

public class RentMenu extends Menu {

    private BalanceText receivingBalance;
    private BalanceText payingBalance;

    public RentMenu(BuyableTile tile, Player payingPlayer) {
        super();

        Text title =  new Text("PAY RENT TO " + tile.getOwner().getName().toUpperCase());
        title.setFont(TITLE_FONT);
        title.setFill(Color.BLACK);
        getChildren().add(title);
        setColumnIndex(title,0);
        setColumnSpan(title,3);
        setRowIndex(title, 0);
        setHalignment(title, HPos.CENTER);

        BuyableCard card = new BuyableCard(tile,Sizes.getCardSize());
        getChildren().add(card);
        setColumnIndex(card, 1);
        setRowIndex(card, 1);
        setHalignment(card, HPos.CENTER);

        Text receivingName =  new Text(tile.getOwner().getName().toUpperCase());
        receivingName.setFont(TITLE_FONT);
        receivingName.setFill(Color.BLACK);

        receivingBalance =  new BalanceText(tile.getOwner().getPrevMoney(),tile.getOwner().getMoney());

        VBox receivingNodes = new VBox(receivingName,receivingBalance);
        getChildren().add(receivingNodes);
        setColumnIndex(receivingNodes,2);
        setRowIndex(receivingNodes, 2);
        setHalignment(receivingNodes, HPos.CENTER);
        setValignment(receivingNodes, VPos.BOTTOM);

        Text payingName =  new Text(payingPlayer.getName().toUpperCase());
        payingName.setFont(TITLE_FONT);
        payingName.setFill(Color.BLACK);

        payingBalance =  new BalanceText(payingPlayer.getPrevMoney(),payingPlayer.getMoney());

        VBox payingNodes = new VBox(payingName,payingBalance);
        getChildren().add(payingNodes);
        setColumnIndex(payingNodes,0);
        setRowIndex(payingNodes, 2);
        setHalignment(payingNodes, HPos.CENTER);
        setValignment(payingNodes, VPos.BOTTOM);

        Arrow arrow = new Arrow(Sizes.getArrowWidth(), Sizes.getArrowHeight());
        getChildren().add(arrow);
        setColumnIndex(arrow, 1);
        setRowIndex(arrow, 2);
        setHalignment(arrow,HPos.CENTER);
        setValignment(arrow, VPos.CENTER);
        setMargin(arrow, new Insets(0,PADDING,0,PADDING));
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

    @Override
    public boolean isFinished() {
        return payingBalance.isFinished() && receivingBalance.isFinished();
    }
}
