package ui.menu.dice;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.board.Street;
import model.game.Dice;
import ui.menu.TextButton;


public class DiceMenu extends GridPane {

    private static final int PADDING = 10;
    private static final Font TITLE_FONT = Font.loadFont("file:assets/fonts/Kabel.ttf", 20);

    private UIDie die1;
    private UIDie die2;

    private boolean started = false;

    public DiceMenu(Dice dice) {
        super();

        setStyle("-fx-border-style: solid inside;" +
                "-fx-border-width: 3;" +
                "");

        Text title =  new Text("ROLL THE DICE");
        title.setFont(TITLE_FONT);
        title.setFill(Color.BLACK);
        title.setStrokeWidth(0.5);
        title.setStroke(Color.BLACK);
        getChildren().add(title);
        setColumnIndex(title,0);
        setColumnSpan(title,2);
        setRowIndex(title, 0);
        setHalignment(title, HPos.CENTER);

        die1 = new UIDie(75);
        getChildren().add(die1);
        setColumnIndex(die1,0);
        setRowIndex(die1,1);

        die2 = new UIDie(75);
        getChildren().add(die2);
        setColumnIndex(die2,1);
        setRowIndex(die2,1);

        TextButton accept = new TextButton(150,50, Street.GREEN.getColor(), "ROLL");
        getChildren().add(accept);
        setColumnIndex(accept,0);
        setColumnSpan(accept,2);
        setRowIndex(accept,2);
        setHalignment(accept, HPos.CENTER);
        accept.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {
            if (!started) {
                started = true;
                die1.animateRoll(dice.getRoll()[0]);
                die2.animateRoll(dice.getRoll()[1]);
            }
        });

        setHgap(PADDING);
        setVgap(PADDING);
        setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
        setMaxHeight(0);
        setMaxWidth(0);
    }

    public boolean isFinished() {
        return die1.isFinished() && die2.isFinished();
    }
}
