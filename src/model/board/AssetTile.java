package model.board;

import com.sun.istack.internal.NotNull;

public class AssetTile extends Tile {

    @NotNull private AssetType type;
    private int cost;
    private int owner = -1;

    public AssetTile(String name, AssetType type, int cost) {
        super(name);

        this.type = type;
        this.cost = cost;
    }

    public AssetType getAssetType() {
        return type;
    }

    public int getCost() {
        return cost;
    }

    public int getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return "AssetTile{" +
                "type=" + type +
                ", cost=" + cost +
                ", owner=" + owner +
                '}';
    }
}
