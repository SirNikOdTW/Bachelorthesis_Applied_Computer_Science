package data.target;

import data.TargetDataset;

public class GameobjectTarget implements TargetDataset
{
    private final int objectId;
    private final String name;
    private final String description;

    public GameobjectTarget(final int objectId, final String name, final String description)
    {
        this.objectId = objectId;
        this.name = name;
        this.description = description;
    }

    public int getObjectId()
    {
        return objectId;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    @Override
    public String toString()
    {
        return String.format("Gameobject [ %d, %s, %s ]", this.objectId, this.name, this.description);
    }
}
