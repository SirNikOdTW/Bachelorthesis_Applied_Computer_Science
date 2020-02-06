package data.source;

import data.SourceDataset;

public class PersonSource implements SourceDataset
{
    private int personId;
    private String name;
    private boolean mortal;

    public PersonSource(int personId, String name, boolean mortal)
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
