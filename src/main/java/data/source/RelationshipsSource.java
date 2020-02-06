package data.source;

import data.SourceDataset;

public class RelationshipsSource implements SourceDataset
{
    private final int personId;
    private final int relationshipLevel;

    public RelationshipsSource(final int personId, final int relationshipLevel)
    {
        this.personId = personId;
        this.relationshipLevel = relationshipLevel;
    }

    public int getPersonId()
    {
        return personId;
    }

    public int getRelationshipLevel()
    {
        return relationshipLevel;
    }

    @Override
    public String toString()
    {
        return String.format("Relationship { %d, %d }", this.personId, this.relationshipLevel);
    }
}
