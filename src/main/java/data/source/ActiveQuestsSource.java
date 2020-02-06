package data.source;

import data.SourceDataset;

public class ActiveQuestsSource implements SourceDataset
{
    private final int questId;
    private final int progress;

    public ActiveQuestsSource(final int questId, final int progress)
    {
        this.questId = questId;
        this.progress = progress;
    }

    public int getQuestId()
    {
        return questId;
    }

    public int getProgress()
    {
        return progress;
    }

    @Override
    public String toString()
    {
        return String.format("ActiveQuest { %d, %d }", this.questId, this.progress);
    }
}
