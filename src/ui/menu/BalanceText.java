package ui.menu;


import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import ui.Sizes;

public class BalanceText extends Text {

    private int displayedValue;
    private int goalValue;
    private boolean updating; // Whether animation is happening right now
    private boolean finished; // Whether animation has finished

    public BalanceText(int startValue, int goalValue) {
        this.displayedValue = startValue;
        this.goalValue = goalValue;
        this.updating = false;
        this.finished = false;

        setText("$" + displayedValue);
        setFont(Font.loadFont("file:assets/fonts/Kabel.ttf", Sizes.getFontHeading()));
        setFill(Color.BLACK);
    }

    public void update() {
        if (!updating && goalValue != displayedValue) {
            updating = true;
            finished = false;
            Platform.runLater(() -> stepText());
        }
    }

    /**
     * Triggers animation of balance text until
     * displayed value matches player's balance
     */
    public void update(int goalValue) {
        this.goalValue = goalValue;
        update();
    }

    private void stepText() {
        // Calculate next value to display
        displayedValue = displayedValue + (int) Math.signum(goalValue - displayedValue);

        // Termination condition
        if (displayedValue == goalValue) {
            setText("$" + displayedValue);
            updating = false;
            finished = true;
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

    public boolean isFinished() {
        return finished;
    }
}
