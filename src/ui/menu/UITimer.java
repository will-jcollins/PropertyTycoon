package ui.menu;


import javafx.application.Platform;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import ui.Sizes;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UITimer extends Text {

    private int secondsLeft;
    private boolean finished = false; // Whether timer has started

    protected static final Font FONT = Font.loadFont("file:assets/fonts/Kabel.ttf", Sizes.getFontHeading());

    public UITimer(int seconds) {
        this.secondsLeft = seconds;
        setFont(FONT);
        setText(formatInt(secondsLeft / 60) + ":" + formatInt(secondsLeft % 60));
    }

    public void start() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable timerRunnable = () -> {
            secondsLeft = Math.max(secondsLeft - 1, 0);
            updateText();

            if (secondsLeft <= 0) {
                finished = true;
                scheduler.shutdown();
            }
        };
        scheduler.scheduleAtFixedRate(timerRunnable,1,1, TimeUnit.SECONDS);
    }

    public boolean isFinished() {
        return finished;
    }

    private void updateText() {
        Platform.runLater(() -> setText(formatInt(secondsLeft / 60) + ":" + formatInt(secondsLeft % 60)));
    }

    private String formatInt(int n) {
        // Adds a zero in front of number if there is only 1 digit
        return Integer.toString(n).length()==1 ? "0" + n : Integer.toString(n);
    }
}
