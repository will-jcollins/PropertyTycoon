package ui.menu;

import javafx.concurrent.Task;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Player.Player;
import model.game.Game;

import java.util.Locale;

public class GoMenu extends Menu {

    private BalanceText receivingBalance;
    private BalanceText payingBalance;


    public GoMenu(Player player) {
        super();

        Text title =  new Text(player.getName().toUpperCase() + ", COLLECT $" + Game.GO_REWARD + " FOR PASSING GO");
        title.setFont(TITLE_FONT);
        title.setFill(Color.BLACK);
        title.setStrokeWidth(0.5);
        title.setStroke(Color.BLACK);
        getChildren().add(title);
        setRowIndex(title,0);
        setColumnIndex(title,0);
        setMargin(title, new Insets(0,0,PADDING * 2,0));

        Text receivingName =  new Text(player.getName().toUpperCase());
        receivingName.setFont(TITLE_FONT);
        receivingName.setFill(Color.BLACK);
        receivingName.setStrokeWidth(0.5);
        receivingName.setStroke(Color.BLACK);

        receivingBalance = new BalanceText(player.getPrevMoney(),player.getMoney(),15);

        VBox receivingNodes = new VBox(receivingName,receivingBalance);

        Text payingName =  new Text("BANK");
        payingName.setFont(TITLE_FONT);
        payingName.setFill(Color.BLACK);
        payingName.setStrokeWidth(0.5);
        payingName.setStroke(Color.BLACK);

        payingBalance =  new BalanceText(Game.GO_REWARD,0,15);

        VBox payingNodes = new VBox(payingName,payingBalance);

        Arrow arrow = new Arrow(100, 20);

        HBox captionNodes = new HBox(payingNodes,arrow,receivingNodes);
        getChildren().add(captionNodes);
        setRowIndex(captionNodes,1);
        setColumnIndex(captionNodes,0);
        captionNodes.setAlignment(Pos.CENTER);
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
