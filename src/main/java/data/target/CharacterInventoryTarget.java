package data.target;

import data.TargetDataset;

public class CharacterInventoryTarget implements TargetDataset
{
    private final int personId;
    private final int objectId;

    public CharacterInventoryTarget(final int personId, final int objectId)
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
        return String.format("CharacterInventory [ %d, %d ]", this.personId, this.objectId);
    }
}
