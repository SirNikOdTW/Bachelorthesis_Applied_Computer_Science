package data.target;

import data.TargetDataset;

public class PlayerAbilitiesTarget implements TargetDataset
{
    private final int playerId;
    private final int abilityId;

    public PlayerAbilitiesTarget(final int playerId, final int abilityId)
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
