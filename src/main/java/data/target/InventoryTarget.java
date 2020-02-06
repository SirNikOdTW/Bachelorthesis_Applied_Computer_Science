package data.target;

import data.TargetDataset;

public class InventoryTarget implements TargetDataset
{
    private int playerId;
    private int objectId;
    private boolean stolen;

    public InventoryTarget(int playerId, int objectId, boolean stolen)
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
