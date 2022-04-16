package ui.menu;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import ui.Sizes;

public class BalanceText extends Text {

    private int startVal;
    private int endVal;
    private int differenceSign;

    private boolean finished = false;
    /**
     * Constructor sets the beginning value and the value which is supposed to be shown at the end
     * @param startVal - starting value
     * @param endVal - ending value
     */
    public BalanceText(int startVal, int endVal) {
        super();

        update(startVal,endVal);

        setText("$" + this.startVal);
        setFont(Font.loadFont("file:assets/fonts/Kabel.ttf", Sizes.getFontBody()));
        setFill(Color.BLACK);
    }

    /**
     * Function determines if the transaction is positive or negative
     * This means whether a player is gaining or losing money
     * @param startVal - starting value
     * @param endVal - ending value
     */
    public void update(int startVal, int endVal) {
        this.startVal = startVal;
        this.endVal = endVal;
        this.differenceSign = (int) Math.signum(this.endVal - this.startVal);
    }

    public void animateText() {
        Platform.runLater(() -> animateText(startVal + differenceSign));
    }

    /**
     * Method responsible for animating text
     * @param currentVal
     */
    private void animateText(int currentVal) {
        // Termination condition
        if (currentVal == endVal) {
            setText("$" + currentVal);
            finished = true;
        } else {
            setText("$" + currentVal);
            // Spawn new thread to wait on in order to not block the UI thread
            Task nextTask = new Task() {
                @Override
                protected Object call() throws Exception {
                    Thread.sleep(15);
                    Platform.runLater(() -> animateText(currentVal + differenceSign));
                    return null;
                }
            };
            Thread nextThread = new Thread(nextTask);
            nextThread.setDaemon(true);
            nextThread.start();
        }
    }

    public int getEndVal() {
        return endVal;
    }

    public boolean isFinished() {
        return finished;
    }
}
