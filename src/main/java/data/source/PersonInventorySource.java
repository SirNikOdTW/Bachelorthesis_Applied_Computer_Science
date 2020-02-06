package data.source;

import data.SourceDataset;

public class PersonInventorySource implements SourceDataset
{
    private final int personId;
    private final int objectId;

    public PersonInventorySource(final int personId, final int objectId)
    {
        this.personId = personId;
        this.objectId = objectId;
    }

    public int getPersonId()
    {
        return personId;
    }

    public int getObjectId()
    {
        return objectId;
    }

    @Override
    public String toString()
    {
        return String.format("PersonInventory { %d, %d }", this.personId, this.objectId);
    }
}
