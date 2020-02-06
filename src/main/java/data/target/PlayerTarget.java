package data.target;

import data.TargetDataset;

public class PlayerTarget implements TargetDataset
{
    private int playerId;
    private String playerName;

    public PlayerTarget(int playerId, String playerName)
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
