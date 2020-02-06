package data.target;

import data.TargetDataset;

public class AbilityTarget implements TargetDataset
{
    private final int abilityId;
    private final String abilityName;
    private final String abilityDescription;
    private final float abilityLevel;

    public AbilityTarget(final int abilityId, final String abilityName, final String abilityDescription, final float abilityLevel)
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
