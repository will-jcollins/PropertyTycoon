package ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.board.AssetTile;

public class AssetUITile extends UITile {

    private static final String STATIONIMGPATH = "file:assets/images/station.png";
    private static final String UTILITYIMGPATH = STATIONIMGPATH;

    public AssetUITile(int x, int y, double width, double height, double angle, AssetTile asset) {
        super(x, y, width, height, angle);

        // Replace space chars with newline
        String name = asset.getName().replaceAll("( )+", "\n");

        // Draw station's name
        Text title = new Text(name.toUpperCase());
        title.setX(x);
        title.setY(y + 1.7 * (height / 10));
        title.setWrappingWidth(width);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setLineSpacing(-7.5);
        title.setFont(TITLEFONT);
        title.setStroke(Color.BLACK);
        title.setStrokeWidth(0.4);
        getChildren().add(title);

        // Draw station's cost
        Text caption = new Text("M" + asset.getCost());
        caption.setX(x);
        caption.setY(y + height - (height / 12));
        caption.setWrappingWidth(width);
        caption.setTextAlignment(TextAlignment.CENTER);
        caption.setFont(CAPTIONFONT);
        caption.setStroke(Color.BLACK);
        caption.setStrokeWidth(0.4);
        getChildren().add(caption);

        ImageView img;

        switch (asset.getAssetType()) {
            case STATION:
                img = new ImageView(new Image(STATIONIMGPATH));
                break;
            case UTILITY:
                img = new ImageView(new Image(UTILITYIMGPATH));
                break;
            default:
                throw new IllegalStateException("AssetUIile: Asset has no type");
        }

        img.setX(x + (width - width / 1.4) / 2);
        img.setY(y + 1.25 * height / 4);
        img.setFitWidth(width / 1.4);
        img.setFitHeight(width / 1.4);
        getChildren().add(img);
    }
}
