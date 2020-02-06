package data.source;

import data.SourceDataset;

import java.sql.Clob;
import java.sql.SQLException;
import java.util.Objects;

public class QuestSource implements SourceDataset
{
    private final int questId;
    private final String name;
    private final Clob dialogue;
    private final int personId;

    public QuestSource(final int questId, final String name, final Clob dialogue, final int personId)
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

    public Clob getDialogue()
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
        try
        {
            return String.format("Quest { %d, %s, %s..., %d }", this.questId, this.name, this.dialogue.getSubString(1, 10), this.personId);
        }
        catch (final SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
