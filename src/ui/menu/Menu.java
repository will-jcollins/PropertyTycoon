package ui.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public abstract class Menu extends GridPane {

    protected static final int PADDING = 10;
    protected static final Color BACKGROUND_COLOR = new Color(1,1,1,1);

    protected static final Font TITLE_FONT = Font.loadFont("file:assets/fonts/Kabel.ttf", 20);
    protected static final Font TEXT_FONT = Font.loadFont("file:assets/fonts/Kabel.ttf", 15);

    public Menu() {
        super();

        setStyle("-fx-border-style: solid inside;" +
                "-fx-border-width: 3;" +
                "");

        setPadding(new Insets(PADDING,PADDING * 2,PADDING,PADDING * 2));
        setHgap(PADDING);
        setVgap(PADDING);
        setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR,null,null)));
        setAlignment(Pos.BASELINE_CENTER);
        setMaxHeight(0);
        setMaxWidth(0);
    }

    public int getEndLatency() {
        return 1000;
    }

    public boolean isFinished() {
        return true;
    }
}
