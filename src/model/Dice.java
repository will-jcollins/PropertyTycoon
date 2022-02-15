package model;

import java.util.Arrays;
import java.util.Random;

// what in the hyperfocus is this class
public class Dice {

    private int[] vals;
    private int range;
    private int matches = 0;
    private Random rand;

    public Dice(int noDice, int range) {
        // Construct and populate values array
        vals = new int[noDice];
        Arrays.fill(vals, 0);

        this.range = range;

        rand = new Random();
    }

    public int[] roll() {
        for (int i = 0; i < vals.length; i++) {
            vals[i] = rand.nextInt(range) + 1;
        }

        matches = doRollsMatch() ? (matches + 1) : 0;

        return vals;
    }

    public int[] getPrevRoll() {
        return vals;
    }

    public int getRollTotal() {
        return Arrays.stream(vals).sum();
    }

    private boolean doRollsMatch() {
        for (int i = 0; i < vals.length - 1; i++) {
            if (vals[i] != vals[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public int getMatches() {
        return matches;
    }

    public void reset() {
        matches = 0;
    }

    public static void main(String[] args) {
        Dice d = new Dice(3, 6);
        int[] roll = d.roll();

        for (int i = 0; i < 100; i++) {
            System.out.println(Arrays.toString(roll));
            System.out.println(d.doRollsMatch());
            roll = d.roll();
        }
    }
}
