package ui.menu.dice;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class UIDie extends Group {

    private static final int DEPTH_BEFORE_FINISH = 50;
    private static final int TIME_BETWEEN_NUM = 20;

    private static final int PADDING = 10;

    private final int dotRadius;
    private final int width;

    private Circle dot1,dot2,dot3,dot4,dot5,dot6;

    private boolean finished = false;

    public UIDie(int width) {

        Rectangle back = new Rectangle();
        back.setWidth(width);
        back.setHeight(width);
        back.setStroke(Color.BLACK);
        back.setStrokeWidth(width / 20);
        back.setFill(Color.WHITE);
        getChildren().add(back);


        this.width = width;
        dotRadius = width / 10;
        displayNum(1);
    }

    public void animateRoll(int finalNum) {
        animateRoll(finalNum, DEPTH_BEFORE_FINISH);
    }

    private void animateRoll(int finalNum, int depth) {
        // Change displayed number to random value until depth of 0 is reached
        // When depth of 0 is reached, display actual roll value
        if (depth == 0) {
            displayNum(finalNum);
            finished = true;
        } else {
            Random rand = new Random();
            displayNum(rand.nextInt(6) + 1);

            // Run on separate thread to prevent UI from freezing
            Platform.runLater(() -> {
                try {
                    Thread.sleep(TIME_BETWEEN_NUM);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                animateRoll(finalNum,depth - 1);
            });
        }
    }

    private void displayNum(int i) {

        // Reset dice display to nothing
        getChildren().remove(dot1);
        getChildren().remove(dot2);
        getChildren().remove(dot3);
        getChildren().remove(dot4);
        getChildren().remove(dot5);
        getChildren().remove(dot6);

        // Draw dots depending on i
        switch (i) {
            case 1:
                dot1 = new Circle();
                dot1.setRadius(dotRadius);
                dot1.setCenterX(width / 2);
                dot1.setCenterY(width / 2);
                getChildren().add(dot1);
                break;
            case 3:
                dot3 = new Circle();
                dot3.setRadius(dotRadius);
                dot3.setCenterX(width / 2);
                dot3.setCenterY(width / 2);
                getChildren().add(dot3);
            case 2:
                dot1 = new Circle();
                dot1.setRadius(dotRadius);
                dot1.setCenterX(width / 4);
                dot1.setCenterY(width / 4);
                getChildren().add(dot1);

                dot2 = new Circle();
                dot2.setRadius(dotRadius);
                dot2.setCenterX(width - (width / 4));
                dot2.setCenterY(width - (width / 4));
                getChildren().add(dot2);
                break;
            case 5:
                dot5 = new Circle();
                dot5.setRadius(dotRadius);
                dot5.setCenterX(width / 2);
                dot5.setCenterY(width / 2);
                getChildren().add(dot5);
            case 4:
                dot1 = new Circle();
                dot1.setRadius(dotRadius);
                dot1.setCenterX(width / 4);
                dot1.setCenterY(width / 4);
                getChildren().add(dot1);

                dot2 = new Circle();
                dot2.setRadius(dotRadius);
                dot2.setCenterX(width - (width / 4));
                dot2.setCenterY(width - (width / 4));
                getChildren().add(dot2);

                dot3 = new Circle();
                dot3.setRadius(dotRadius);
                dot3.setCenterX(width / 4);
                dot3.setCenterY(width - width / 4);
                getChildren().add(dot3);

                dot4 = new Circle();
                dot4.setRadius(dotRadius);
                dot4.setCenterX(width - width / 4);
                dot4.setCenterY(width / 4);
                getChildren().add(dot4);
                break;
            case 6:
                dot1 = new Circle();
                dot1.setRadius(dotRadius);
                dot1.setCenterY(width / 4);
                dot1.setCenterX(width / 4);
                getChildren().add(dot1);

                dot2 = new Circle();
                dot2.setRadius(dotRadius);
                dot2.setCenterY(width / 2);
                dot2.setCenterX(width / 4);
                getChildren().add(dot2);

                dot3 = new Circle();
                dot3.setRadius(dotRadius);
                dot3.setCenterY(width - width / 4);
                dot3.setCenterX(width / 4);
                getChildren().add(dot3);

                dot4 = new Circle();
                dot4.setRadius(dotRadius);
                dot4.setCenterY(width / 4);
                dot4.setCenterX(width - width / 4);
                getChildren().add(dot4);

                dot5 = new Circle();
                dot5.setRadius(dotRadius);
                dot5.setCenterY(width / 2);
                dot5.setCenterX(width - width / 4);
                getChildren().add(dot5);

                dot6 = new Circle();
                dot6.setRadius(dotRadius);
                dot6.setCenterY(width - width / 4);
                dot6.setCenterX(width - width / 4);
                getChildren().add(dot6);

                break;
            default:
                throw new IllegalStateException("Dice can not display a number outside the bounds of 1-6");
        }
    }

    public boolean isFinished() {
        return finished;
    }
}
