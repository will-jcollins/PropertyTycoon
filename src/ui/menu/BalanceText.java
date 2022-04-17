package ui.menu;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import ui.Sizes;

import java.util.Queue;

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

        this.startVal = startVal;
        this.endVal = endVal;
        this.differenceSign = (int) Math.signum(this.endVal - this.startVal);

        setText("$" + this.startVal);
        setFont(Font.loadFont("file:assets/fonts/Kabel.ttf", Sizes.getFontBody()));
        setFill(Color.BLACK);
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

    public boolean isFinished() {
        return finished;
    }
}
