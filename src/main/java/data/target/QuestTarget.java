package data.target;

import data.TargetDataset;

import java.sql.SQLException;

public class QuestTarget implements TargetDataset
{
    private final int questId;
    private final String name;
    private final String involvedCharacters;
    private final String dialogue;

    public QuestTarget(final int questId, final String name, final String involvedCharacters, final String dialogue)
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

    public String getDialogue()
    {
        return dialogue;
    }

    @Override
    public String toString()
    {
        return String.format("Quest [ %d, %s, { %s }, %s... ]", this.questId, this.name, this.involvedCharacters, this.dialogue.substring(0, 10));
    }
}
