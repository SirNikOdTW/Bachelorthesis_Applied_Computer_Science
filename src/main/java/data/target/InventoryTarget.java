package data.target;

import data.TargetDataset;

public class InventoryTarget implements TargetDataset
{
    private final int playerId;
    private final int objectId;
    private final boolean stolen;

    public InventoryTarget(int playerId, int objectId)
    {
        this(playerId, objectId, false);
    }

    public InventoryTarget(final int playerId, final int objectId, final boolean stolen)
    {
        this.playerId = playerId;
        this.objectId = objectId;
        this.stolen = stolen;
    }

    public int getPlayerId()
    {
        return playerId;
    }

    public int getObjectId()
    {
        return objectId;
    }

    public boolean isStolen()
    {
        return stolen;
    }

    @Override
    public String toString()
    {
        return String.format("Inventory [ %d, %d, %s ]", this.playerId, this.objectId, this.stolen);
    }
}
