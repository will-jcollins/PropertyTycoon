package model.board;

import javafx.scene.paint.Color;

public enum Street {
    BROWN(Color.SADDLEBROWN,50),BLUE(Color.PALETURQUOISE,50),DEEPBLUE(Color.ROYALBLUE,200),PURPLE(Color.DEEPPINK,100),
    ORANGE(Color.ORANGE,100),RED(Color.ORANGERED,150),YELLOW(Color.YELLOW,150),GREEN(Color.FORESTGREEN,200);

    private Color color;
    private int developCost;

    Street(Color color, int developCost) {
        this.color = color;
        this.developCost = developCost;
    }

    public Color getColor() {
        return color;
    }

    public int getDevelopCost() {
        return developCost;
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
