package model.actions;

import java.util.Locale;

public enum ActCode {
    BANKPAY, // Recieve money from bank (val1 represents money player receives)
    PAYBANK, // Pay money to bank (val1 represents money player pays)
    PAYFINE, // Place money under free parking (val1 represents money player pays)
    PAYFINEOROPPURTUNITY, // Place money under free parking or pick up oppurtunity knock (val1 represents money player pays)
    FINEPAY, // Receive all the money under free parking
    PAYASSETS, // Pay money to bank as a factor of the number of houses / hotels (val1 represents factor for houses, val2 represents factor for hotels)
    MOVETO, // Move to space specified (val1 represents space to move to, val2 represents if player should collect go reward)
    MOVEN, // Move n spaces (val1 represents n, val2 represents if player should collect go reward)
    JAIL, // Go straight to jail (no operands)
    JAILCARD, // Receive a get out of jail free card (no operands)
    POTLUCK, // Receive a potluck card (no operands)
    OPPURTUNITY, // Receive an opportunity knock card (no operands)
    NOP; // Do nothing (no operands)

    public static ActCode fromString(String in) {
        in = in.toUpperCase(Locale.ROOT);
        ActCode[] values = ActCode.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].toString().equals(in)) {
                return values[i];
            }
        }
        throw new EnumConstantNotPresentException(ActCode.class, in);
    }
}
