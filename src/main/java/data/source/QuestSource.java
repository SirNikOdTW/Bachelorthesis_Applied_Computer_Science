package data.source;

import data.SourceDataset;

public class QuestSource implements SourceDataset
{
    private int questId;
    private String name;
    private String dialogue;

    public QuestSource(int questId, String name, String dialogue)
    {
        this.questId = questId;
        this.name = name;
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

    public String getDialogue()
    {
        return dialogue;
    }

    @Override
    public String toString()
    {
        return String.format("Quest { %d, %s, %s... }", this.questId, this.name, this.dialogue.substring(0, 10));
    }
}
