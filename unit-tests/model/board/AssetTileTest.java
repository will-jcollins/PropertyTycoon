package model.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssetTileTest {

    @Test
    void testGetters() {
        String name = "Falmer Station";
        AssetType type = AssetType.STATION;
        int cost = 50;
        AssetTile assTile = new AssetTile(name, type, cost);
        assertEquals(assTile.getName(), name);
        assertEquals(assTile.getAssetType(), type);
        assertEquals(assTile.getCost(), cost);
        assertEquals(assTile.getOwner(), -1);
    }
}