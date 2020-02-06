package data.source;

import data.SourceDataset;

public class PersonSource implements SourceDataset
{
    private final int personId;
    private final String name;
    private final boolean mortal;

    public PersonSource(final int personId, final String name, final boolean mortal)
    {
        this.personId = personId;
        this.name = name;
        this.mortal = mortal;
    }

    public int getPersonId()
    {
        return personId;
    }

    public String getName()
    {
        return name;
    }

    public boolean isMortal()
    {
        return mortal;
    }

    @Override
    public String toString()
    {
        return String.format("Person { %d, %s, %s }", this.personId, this.name, this.mortal);
    }
}
