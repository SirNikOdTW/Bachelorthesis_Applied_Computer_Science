package data.target;

import data.TargetDataset;

public class RelationshipTarget implements TargetDataset
{
    private final int playerId;
    private final int personId;
    private final float relationshipLevel;

    public RelationshipTarget(final int playerId, final int personId, final float relationshipLevel)
    {
        this.playerId = playerId;
        this.personId = personId;
        this.relationshipLevel = relationshipLevel;
    }

    public int getPlayerId()
    {
        return playerId;
    }

    public int getPersonId()
    {
        return personId;
    }

    public float getRelationshipLevel()
    {
        return relationshipLevel;
    }

    @Override
    public String toString()
    {
        return String.format("Relationship [ %d, %d, %f ]", this.playerId, this.personId, this.relationshipLevel);
    }

}
