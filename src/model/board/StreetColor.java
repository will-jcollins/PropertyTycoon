package model.board;

import javafx.scene.paint.Color;

public enum StreetColor {
    BROWN(Color.SADDLEBROWN),BLUE(Color.PALETURQUOISE),DEEPBLUE(Color.DARKSLATEBLUE),PURPLE(Color.DEEPPINK),
    ORANGE(Color.ORANGE),RED(Color.ORANGERED),YELLOW(Color.YELLOW),GREEN(Color.FORESTGREEN);

    private Color color;

    StreetColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public static StreetColor fromString(String in) {
        in = in.toUpperCase();
        StreetColor[] values = StreetColor.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].toString().equals(in)) {
                return values[i];
            }
        }
        throw new EnumConstantNotPresentException(StreetColor.class, in);
    }
}
