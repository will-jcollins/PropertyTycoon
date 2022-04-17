package ui.player;


import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Player.Player;
import ui.Sizes;

public class StatBalanceText extends Text {

    private Player player; // Player who's balance is tracked
    private int displayedValue;
    private boolean updating;

    public StatBalanceText(Player player) {
        this.player = player;
        this.displayedValue = player.getMoney();
        this.updating = false;

        setText("$" + displayedValue);
        setFont(Font.loadFont("file:assets/fonts/Kabel.ttf", Sizes.getFontHeading()));
        setFill(Color.BLACK);
    }

    /**
     * Triggers animation of balance text until
     * displayed value matches player's balance
     */
    public void update() {
        if (!updating) {
            updating = true;
            Platform.runLater(() -> stepText());
        }
    }

    private void stepText() {
        // Calculate next value to display
        displayedValue = displayedValue + (int) Math.signum(player.getMoney() - displayedValue);

        // Termination condition
        if (displayedValue == player.getMoney()) {
            setText("$" + displayedValue);
            updating = false;
        } else {
            setText("$" + displayedValue);
            // Spawn new thread to wait on in order to not block the UI thread
            Task nextTask = new Task() {
                @Override
                protected Object call() throws Exception {
                    Thread.sleep(15);
                    Platform.runLater(() -> stepText());
                    return null;
                }
            };
            Thread nextThread = new Thread(nextTask);
            nextThread.setDaemon(true);
            nextThread.start();
        }
    }
}
