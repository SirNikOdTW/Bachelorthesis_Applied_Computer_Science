package data.target;

import data.TargetDataset;

public class ActiveQuestsTarget implements TargetDataset
{
    private int playerId;
    private int questId;
    private int questProgress;

    public ActiveQuestsTarget(int playerId, int questId, int questProgress)
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
