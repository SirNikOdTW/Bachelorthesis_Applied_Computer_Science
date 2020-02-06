package data.target;

import data.TargetDataset;

import java.sql.Clob;
import java.sql.SQLException;

public class QuestTarget implements TargetDataset
{
    private final int questId;
    private final String name;
    private final String involvedCharacters;
    private final Clob dialogue;

    public QuestTarget(final int questId, final String name, final String involvedCharacters, final Clob dialogue)
    {
        this.questId = questId;
        this.name = name;
        this.involvedCharacters = involvedCharacters;
        this.dialogue = dialogue;
    }

    public int getQuestId()
    {
        return questId;
    }

    public String getName()
    {
        return name;
    }

    public String getInvolvedCharacters()
    {
        return involvedCharacters;
    }

    public Clob getDialogue()
    {
        return dialogue;
    }

    @Override
    public String toString()
    {
        try
        {
            return String.format("Quest [ %d, %s, { %s }, %s... ]", this.questId, this.name, this.involvedCharacters, this.dialogue.getSubString(1, 10));
        }
        catch (final SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
