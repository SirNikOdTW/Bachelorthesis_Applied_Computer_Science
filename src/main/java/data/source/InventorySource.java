package data.source;

import data.SourceDataset;

public class InventorySource implements SourceDataset
{
    private int objectId;

    public InventorySource(int objectId)
    {
        this.objectId = objectId;
    }

    public int getObjectId()
    {
        return objectId;
    }

    @Override
    public String toString()
    {
        return String.format("Inventory { %d }", this.objectId);
    }
}
