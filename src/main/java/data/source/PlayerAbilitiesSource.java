package data.source;

import data.SourceDataset;

public class PlayerAbilitiesSource implements SourceDataset
{
    private int playerId;
    private int abilityId;

    public PlayerAbilitiesSource(int playerId, int abilityId)
    {
        this.playerId = playerId;
        this.abilityId = abilityId;
    }

    public int getPlayerId()
    {
        return playerId;
    }

    public int getAbilityId()
    {
        return abilityId;
    }

    @Override
    public String toString()
    {
        return String.format("PlayerAbility [ %d, %d ]", this.playerId, this.abilityId);
    }

}
