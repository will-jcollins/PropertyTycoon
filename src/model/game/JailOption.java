package model.game;

/**
 * Enumerates choices for a player that is in jail
 * @Author Will Collins
 */
public enum JailOption {
    WAIT, // Player waits until 3 turns have taken place in jail
    ROLL_DICE, // Player has rolled a double in jail
    PAY, // Player has paid $50 to leave jail
    JAILCARD // Player has used a jail card
}
