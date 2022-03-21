package model.board;

import javafx.scene.paint.Color;

public enum Street {
    BROWN(Color.SADDLEBROWN),BLUE(Color.PALETURQUOISE),DEEPBLUE(Color.ROYALBLUE),PURPLE(Color.DEEPPINK),
    ORANGE(Color.ORANGE),RED(Color.ORANGERED),YELLOW(Color.YELLOW),GREEN(Color.FORESTGREEN);

    private Color color;

    Street(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public static Street fromString(String in) {
        in = in.toUpperCase();
        Street[] values = Street.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].toString().equals(in)) {
                return values[i];
            }
        }
        throw new EnumConstantNotPresentException(Street.class, in);
    }
}
