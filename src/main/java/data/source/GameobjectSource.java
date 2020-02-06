package data.source;

import data.SourceDataset;

public class GameobjectSource implements SourceDataset
{
    private int objectId;
    private String name;
    private String description;

    public GameobjectSource(int objectId, String name, String description)
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
        return String.format("Gameobject { %d, %s, %s }", this.objectId, this.name, this.description);
    }
}
