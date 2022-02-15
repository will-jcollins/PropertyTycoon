package model;

import java.util.Locale;

public enum AssetType {
    STATION, UTILITY;

    public static AssetType fromString(String in) {
        in = in.toUpperCase(Locale.ROOT);
        AssetType[] values = AssetType.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].toString().equals(in)) {
                return values[i];
            }
        }
        throw new EnumConstantNotPresentException(AssetType.class, in);
    }
}
