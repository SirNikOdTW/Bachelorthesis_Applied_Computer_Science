package data.target;

import data.TargetDataset;

public class ActiveQuestsTarget implements TargetDataset
{
    private final int playerId;
    private final int questId;
    private final int questProgress;

    public ActiveQuestsTarget(final int playerId, final int questId, final int questProgress)
    {
        this.playerId = playerId;
        this.questId = questId;
        this.questProgress = questProgress;
    }

    public int getQuestId()
    {
        return questId;
    }

    public int getQuestProgress()
    {
        return questProgress;
    }

    @Override
    public String toString()
    {
        return String.format("ActiveQuest [ %d, %d, %d ]", this.playerId, this.questId, this.questProgress);
    }
}
