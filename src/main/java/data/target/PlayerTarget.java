package data.target;

import data.TargetDataset;

public class PlayerTarget implements TargetDataset
{
    private final int playerId;
    private final String playerName;

    public PlayerTarget(final int playerId, final String playerName)
    {
        this.playerId = playerId;
        this.playerName = playerName;
    }

    public int getPlayerId()
    {
        return playerId;
    }

    public String getPlayerName()
    {
        return playerName;
    }

    @Override
    public String toString()
    {
        return String.format("Player [ %d, %s ]", this.playerId, this.playerName);
    }
}
