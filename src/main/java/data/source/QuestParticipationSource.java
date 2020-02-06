package data.source;

import data.SourceDataset;

public class QuestParticipationSource implements SourceDataset
{
    private int questId;
    private int personId;

    public QuestParticipationSource(int questId, int personId)
    {
        this.questId = questId;
        this.personId = personId;
    }

    public int getPersonId()
    {
        return personId;
    }

    public int getQuestId()
    {
        return questId;
    }

    @Override
    public String toString()
    {
        return String.format("QuestParticipation { %d, %d }", this.questId, this.personId);
    }
}
