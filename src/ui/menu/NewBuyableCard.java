package ui.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.board.PropertyTile;
import ui.Sizes;

import java.util.Locale;

public class NewBuyableCard extends StackPane {

    protected static final Font TITLE_FONT = Font.loadFont("file:assets/fonts/Kabel.ttf", Sizes.getFontHeading());
    protected static final Font TEXT_FONT = Font.loadFont("file:assets/fonts/Kabel.ttf", Sizes.getFontBody());

    public NewBuyableCard(PropertyTile prop) {
        super();

        GridPane content = new GridPane();
        content.setStyle("-fx-border-style: solid inside;" +
                "-fx-border-width: " + Sizes.getSmallStroke() + ";");

        // Container for title
        VBox header = new VBox();
        header.setStyle("-fx-border-style: solid inside;" +
                "-fx-border-width: " + Sizes.getSmallStroke() + ";");
        header.setBackground(new Background(new BackgroundFill(prop.getStreet().getColor(), null,null)));
        header.setAlignment(Pos.CENTER);
        header.setSpacing(Sizes.getPadding());
        header.setPadding(new Insets(Sizes.getPadding(),Sizes.getPadding(),Sizes.getPadding(),Sizes.getPadding()));
        content.add(header,0,0,2,1);

        Text titleDeedTxt = new Text("T I T L E  D E E D");
        titleDeedTxt.setFont(TITLE_FONT);
        titleDeedTxt.setFill(getTextColor(prop.getStreet().getColor()));
        titleDeedTxt.setTextAlignment(TextAlignment.CENTER);
        header.getChildren().add(titleDeedTxt);

        Text propNameTxt = new Text(prop.getName().toUpperCase());
        propNameTxt.setFont(TITLE_FONT);
        propNameTxt.setFill(getTextColor(prop.getStreet().getColor()));
        propNameTxt.setTextAlignment(TextAlignment.CENTER);
        header.getChildren().add(propNameTxt);

        Text rentTxt = new Text("RENT     $" + prop.getRent(0));
        rentTxt.setFont(TEXT_FONT);
        rentTxt.setFill(Color.BLACK);
        rentTxt.setTextAlignment(TextAlignment.CENTER);
        content.add(rentTxt,0,1,2,1);

        getChildren().add(content);
    }

    /**
     * Returns the most visible colour for a given background colour
     * Based on answer by Mark Ransom at https://stackoverflow.com/questions/3942878/how-to-decide-font-color-in-white-or-black-depending-on-background-color
     * @author Will Collins
     * @param c background color
     * @return white or black color
     */
    public static Color getTextColor(Color c) {
        // If intensity is above a 'magic' threshold black is returned, otherwise white is returned
        if ((c.getRed()*255*0.299 + c.getGreen()*255*0.587 + c.getBlue()*255*0.114) > 186) {
            return Color.BLACK;
        } else {
            return Color.WHITE;
        }
    }
}
