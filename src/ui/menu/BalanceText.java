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

    public BalanceText(int startVal, int endVal) {
        super();

        update(startVal,endVal);

        setText("$" + this.startVal);
        setFont(Font.loadFont("file:assets/fonts/Kabel.ttf", Sizes.getFontBody()));
        setFill(Color.BLACK);
    }

    public void animateText() {
        Platform.runLater(() -> animateText(startVal + differenceSign));
    }

    public void update(int startVal, int endVal) {
        this.startVal = startVal;
        this.endVal = endVal;
        this.differenceSign = (int) Math.signum(this.endVal - this.startVal);
    }

    private void animateText(int currentVal) {
        if (currentVal == endVal) {
            setText("$" + currentVal);
            finished = true;
        } else {
            setText("$" + currentVal);
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
