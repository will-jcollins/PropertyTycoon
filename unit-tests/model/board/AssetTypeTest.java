package model.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssetTypeTest {
    @Test
    void testFromString() {
        AssetType[] codes = AssetType.values();
        for (AssetType code : codes) {
            assertEquals(AssetType.fromString(code.toString()),code);
        }
    }
}