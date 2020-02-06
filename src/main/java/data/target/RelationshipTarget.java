package data.target;

import data.TargetDataset;

public class RelationshipTarget implements TargetDataset
{
    private int playerId;
    private int personId;
    private int relationshipLevel;

    public RelationshipTarget(int playerId, int personId, int relationshipLevel)
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

    public int getRelationshipLevel()
    {
        return relationshipLevel;
    }

    @Override
    public String toString()
    {
        return String.format("Relationship [ %d, %d, %d ]", this.playerId, this.personId, this.relationshipLevel);
    }

}
