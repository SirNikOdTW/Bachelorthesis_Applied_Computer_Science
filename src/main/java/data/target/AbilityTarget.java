package data.target;

import data.TargetDataset;

public class AbilityTarget implements TargetDataset
{
    private int abilityId;
    private String abilityName;
    private String abilityDescription;
    private float abilityLevel;

    public AbilityTarget(int abilityId, String abilityName, String abilityDescription, float abilityLevel)
    {
        this.abilityId = abilityId;
        this.abilityName = abilityName;
        this.abilityDescription = abilityDescription;
        this.abilityLevel = abilityLevel;
    }

    public int getAbilityId()
    {
        return abilityId;
    }

    public String getAbilityName()
    {
        return abilityName;
    }

    public String getAbilityDescription()
    {
        return abilityDescription;
    }

    public float getAbilityLevel()
    {
        return abilityLevel;
    }

    @Override
    public String toString()
    {
        return String.format("Ability [ %d, %s, %s, %f ]", this.abilityId, this.abilityName, this.abilityDescription, this.abilityLevel);
    }

}
