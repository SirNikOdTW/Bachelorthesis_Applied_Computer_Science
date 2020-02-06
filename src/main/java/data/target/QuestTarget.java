package data.target;

import data.TargetDataset;

import java.sql.Clob;
import java.sql.SQLException;

public class QuestTarget implements TargetDataset
{
    private int questId;
    private String name;
    private String involvedCharacters;
    private Clob dialogue;

    public QuestTarget(int questId, String name, String involvedCharacters, Clob dialogue)
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
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
