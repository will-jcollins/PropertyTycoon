package model.actions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Action implements Actionable {
    private final ActCode actcode;
    private final int val1;
    private final int val2;

    /**
     * Constructs an action based on an input string in the format "ACTION ACTIONVALUE1 ACTIONVALUE1"
     *
     * @param in string action is constructed based on
     * @author Will Collins
     */
    public Action(String in) {
        in = in.toUpperCase();
        ActCode[] values = ActCode.values();
        StringBuilder sb = new StringBuilder();

        // Build regex with all enumerated actions
        sb.append(values[0].toString());
        for (int i = 1; i < values.length; i++) {
            sb.append("|" + values[i].toString());
        }
        String regex = "(" + sb + ")( [0-9]+)?( [0-9]+)?";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(in);

        // Match regex and assign attributes from pattern
        if (matcher.find()) {
            this.actcode = ActCode.fromString(matcher.group(1));
            this.val1 = (matcher.group(2) != null) ? Integer.valueOf(matcher.group(2).replaceAll("\\s+","")) : 0;
            this.val2 = (matcher.group(3) != null) ? Integer.valueOf(matcher.group(3).replaceAll("\\s+","")) : 0;
        } else {
            throw new IllegalArgumentException("action \"" + in + "\" is not enumerated");
        }
    }

    /**
     * Constructs an Action using parameters as attribute values
     *
     * @param actcode process action represents
     * @param val1 value 1 for the action
     * @param val2 value 2 for the action
     * @author Will Collins
     */
    public Action(ActCode actcode, int val1, int val2) {
        this.actcode = actcode;
        this.val1 = val1;
        this.val2 = val2;
    }

    public ActCode getActcode() {
        return actcode;
    }

    public Action getAction() {
        return this;
    }

    public int getVal1() {
        return val1;
    }

    public int getVal2() {
        return val2;
    }

    @Override
    public String toString() {
        return "Action{" +
                "action=" + actcode +
                ", val1=" + val1 +
                ", val2=" + val2 +
                '}';
    }
}
