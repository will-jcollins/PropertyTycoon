package model.game;

import java.util.Arrays;
import java.util.Random;
/**
 * Class used to simulate dice roll
 */
public class Dice {

    private int[] vals;
    private int range;
    private int doubles = 0; // Number of doubles in a row
    private Random rand;

    /**
     * Constructor of class Dice
     * @param noDice number of dice
     * @param range max value on the dice
     */
    public Dice(int noDice, int range) {
        this.range = range;
        // Construct and populate values array
        vals = new int[noDice];
        Arrays.fill(vals, 1);

        rand = new Random();
    }
    /**
     * Generates n random numbers within the range passed in constructor
     * @return resulting array of random values
     */
    public int[] roll() {
        // Populate array with random values
        for (int i = 0; i < vals.length; i++) {
            vals[i] = rand.nextInt(range) + 1;
        }

        // If a double was rolled, increment double counter, otherwise reset it to zero
        doubles = isDouble() ? (doubles + 1) : 0;

        return vals;
    }

    /**
     * Provides an array of values for the last roll
     * @return array of random values
     */
    public int[] getRoll() {
        return vals;
    }

    /**
     * Add all values from last roll
     * @return sum of values from last roll
     */
    public int getRollTotal() {
        return Arrays.stream(vals).sum();
    }

    /**
     * Checks if the dice roll is a double
     * @return true if it is a double, false otherwise
     */
    public boolean isDouble() {
        for (int i = 0; i < vals.length - 1; i++) {
            if (vals[i] != vals[i + 1]) {
                return false;
            } else if (vals[i] == 0) {
                // Return false if any value is zero as that means dice has been reset since last roll
                return false;
            }
        }
        return true;
    }

    /**
     * Returns number of times a double has been rolled in a row since last time Dice was reset
     * @return n times double has been rolled by this dice
     */
    public int getDoubles() {
        return doubles;
    }

    /**
     * Resets number of doubles to zero and clears last roll
     */
    public void reset() {
        doubles = 0;

        for (int i = 0; i < vals.length; i++) {
            vals[i] = 0;
        }
    }
}
