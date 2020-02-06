package data.target;

import data.TargetDataset;

public class CharacterInventoryTarget implements TargetDataset
{
    private int personId;
    private int objectId;

    public CharacterInventoryTarget(int personId, int objectId)
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
