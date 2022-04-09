package model.game;

import java.util.Arrays;
import java.util.Random;

public class Dice {

    private int[] vals;
    private int range;
    private int doubles = 0;
    private Random rand;

    public Dice(int noDice, int range) {
        this.range = range;
        // Construct and populate values array
        vals = new int[noDice];
        Arrays.fill(vals, 1);

        rand = new Random();
    }

    public int[] roll() {
        for (int i = 0; i < vals.length; i++) {
            vals[i] = rand.nextInt(range) + 1;
            vals[0] = 1;
            vals[1] = 0;
        }

        doubles = isDouble() ? (doubles + 1) : 0;

        return vals;
    }

    public int[] getRoll() {
        return vals;
    }

    public int getRollTotal() {
        return Arrays.stream(vals).sum();
    }

    public boolean isDouble() {
        for (int i = 0; i < vals.length - 1; i++) {
            if (vals[i] != vals[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public int getDoubles() {
        return doubles;
    }

    public void reset() {
        doubles = 0;

        for (int i : vals) {
            i = 0;
        }
    }

    public static void main(String[] args) {
        Dice d = new Dice(3, 6);
        int[] roll = d.roll();

        for (int i = 0; i < 100; i++) {
            System.out.println(Arrays.toString(roll));
            System.out.println(d.isDouble());
            roll = d.roll();
        }
    }
}
