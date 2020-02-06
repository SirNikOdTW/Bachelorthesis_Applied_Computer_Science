package data.target;

import data.TargetDataset;

public class QuestTarget implements TargetDataset
{
    private int questId;
    private String name;
    private String involvedCharacters;
    private String dialogue;

    public QuestTarget(int questId, String name, String involvedCharacters, String dialogue)
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
        return String.format("Quest [ %d, %s, %s, %s... ]", this.questId, this.name, this.involvedCharacters, this.dialogue.substring(0, 10));
    }
}
