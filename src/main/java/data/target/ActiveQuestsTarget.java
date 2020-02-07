package data.target;

import data.TargetDataset;

public class ActiveQuestsTarget implements TargetDataset
{
    private final int playerId;
    private final int questId;
    private final float questProgress;

    public ActiveQuestsTarget(final int playerId, final int questId, final float questProgress)
    {
        this.playerId = playerId;
        this.questId = questId;
        this.questProgress = questProgress;
    }

    public int getPlayerId()
    {
        return playerId;
    }

    public int getQuestId()
    {
        return questId;
    }

    public float getQuestProgress()
    {
        return questProgress;
    }

    @Override
    public String toString()
    {
        return String.format("ActiveQuest [ %d, %d, %f ]", this.playerId, this.questId, this.questProgress);
    }
}
