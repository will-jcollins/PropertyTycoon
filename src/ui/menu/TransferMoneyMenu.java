package ui.menu;

import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Player.Player;
import ui.Sizes;

/**
 * Class responsible for creaing Transfer money menu between two players
 */
public class TransferMoneyMenu extends Menu {
    BalanceText receivingBalance;
    BalanceText payingBalance;

    public TransferMoneyMenu(Player player, boolean playerPay, String payReason) {
        super();

        if (!playerPay) {
            Text title = new Text(player.getName().toUpperCase() + ", COLLECT MONEY FROM " + payReason + "!");
            title.setFont(TITLE_FONT);
            title.setFill(Color.BLACK);
            getChildren().add(title);
            setRowIndex(title, 0);
            setColumnIndex(title, 0);
            setMargin(title, new Insets(0, 0, PADDING * 2, 0));

            Text receivingName = new Text(player.getName().toUpperCase());
            receivingName.setFont(TITLE_FONT);
            receivingName.setFill(Color.BLACK);

            receivingBalance = new BalanceText(player.getPrevMoney(), player.getMoney());

            VBox receivingNodes = new VBox(receivingName, receivingBalance);

            Text payingName = new Text(payReason);
            payingName.setFont(TITLE_FONT);
            payingName.setFill(Color.BLACK);

            payingBalance = new BalanceText(player.getMoney() - player.getPrevMoney(), 0);

            VBox payingNodes = new VBox(payingName, payingBalance);

            Arrow arrow = new Arrow(Sizes.getArrowWidth(), Sizes.getArrowHeight());
            setMargin(arrow, new Insets(0, PADDING, 0, PADDING));

            HBox captionNodes = new HBox(payingNodes, arrow, receivingNodes);
            getChildren().add(captionNodes);
            setRowIndex(captionNodes, 1);
            setColumnIndex(captionNodes, 0);
            captionNodes.setAlignment(Pos.CENTER);

        } else {
            Text title = new Text(player.getName().toUpperCase() + ", PAY MONEY TO " + payReason + "!");
            title.setFont(TITLE_FONT);
            title.setFill(Color.BLACK);

            getChildren().add(title);

            setRowIndex(title,0);
            setColumnIndex(title,0);
            setMargin(title, new Insets(0,0,PADDING * 2,0));

            Text receivingName = new Text(payReason);
            receivingName.setFont(TITLE_FONT);
            receivingName.setFill(Color.BLACK);

            receivingBalance = new BalanceText(0, player.getPrevMoney()-player.getMoney());

            VBox receivingNodes = new VBox(receivingName, receivingBalance);

            Text payingName = new Text(player.getName().toUpperCase());
            payingName.setFont(TITLE_FONT);
            payingName.setFill(Color.BLACK);

            payingBalance = new BalanceText(player.getPrevMoney(), player.getMoney());

            VBox payingNodes = new VBox(payingName, payingBalance);

            Arrow arrow = new Arrow(Sizes.getArrowWidth(), Sizes.getArrowHeight());
            setMargin(arrow, new Insets(0,PADDING,0,PADDING));

            HBox captionNodes = new HBox(payingNodes, arrow, receivingNodes);
            getChildren().add(captionNodes);
            setRowIndex(captionNodes, 1);
            setColumnIndex(captionNodes, 0);
            captionNodes.setAlignment(Pos.CENTER);
        }
    }

    public void startAnimation() {
        Task animateTask = new Task() {
            @Override
            protected Object call() throws Exception {
                Thread.sleep(1000);
                payingBalance.update();
                receivingBalance.update();
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
