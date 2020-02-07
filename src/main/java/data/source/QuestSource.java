package data.source;

import data.SourceDataset;

import java.sql.SQLException;
import java.util.Objects;

public class QuestSource implements SourceDataset
{
    private final int questId;
    private final String name;
    private final String dialogue;
    private final int personId;

    public QuestSource(final int questId, final String name, final String dialogue, final int personId)
    {
        this.questId = questId;
        this.name = name;
        this.dialogue = dialogue;
        this.personId = personId;
    }

    public int getQuestId()
    {
        return questId;
    }

    public String getName()
    {
        return name;
    }

    public String getDialogue()
    {
        return dialogue;
    }

    public int getPersonId()
    {
        return personId;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        final QuestSource that = (QuestSource) o;
        return questId == that.questId;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(questId);
    }

    @Override
    public String toString()
    {
        return String.format("Quest { %d, %s, %s..., %d }", this.questId, this.name, this.dialogue.substring(0, 10), this.personId);
    }
}
