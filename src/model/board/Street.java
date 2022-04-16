package model.board;

import javafx.scene.paint.Color;

public enum Street {
    /**
     * Colors used to crating street colors
     */
    BROWN(Color.SADDLEBROWN,50),BLUE(Color.PALETURQUOISE,50),DEEPBLUE(Color.ROYALBLUE,200),PURPLE(Color.DEEPPINK,100),
    ORANGE(Color.ORANGE,100),RED(Color.ORANGERED,150),YELLOW(Color.YELLOW,150),GREEN(Color.FORESTGREEN,200);

    private Color color;
    private int developCost;

    /**
     * Constructor of class street
     * @param color Street color
     * @param developCost cost of developing the street
     */
    Street(Color color, int developCost) {
        this.color = color;
        this.developCost = developCost;
    }

    /**
     * Color getter
     * @return color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Getter of development cost
     * @return developCost value
     */
    public int getDevelopCost() {
        return developCost;
    }
    /**
     * Method used to get the specific street out of array
     * @param in Name of a street
     * @return street name if there is one
     * @exception throw exception if there isn't any street named in in Street.values()
     */
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
