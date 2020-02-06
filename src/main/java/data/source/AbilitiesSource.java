package data.source;

import data.SourceDataset;

public class AbilitiesSource implements SourceDataset
{
    private final String name;
    private final String description;
    private final int level;

    public AbilitiesSource(final String name, final String description, final int level)
    {
        this.name = name;
        this.description = description;
        this.level = level;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public int getLevel()
    {
        return level;
    }

    @Override
    public String toString()
    {
        return String.format("Ability { %s, %s, %d }", this.name, this.description, this.level);
    }
}
