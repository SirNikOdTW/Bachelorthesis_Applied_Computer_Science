package data.target;

import data.TargetDataset;

import java.sql.Blob;
import java.time.LocalDate;

public class ModTarget implements TargetDataset
{
    private final int modId;
    private final String modName;
    private final LocalDate modInstallationDate;
    private final Blob modBinary;

    public ModTarget(final int modId, final String modName, final LocalDate modInstallationDate, final Blob modBinary)
    {
        this.modId = modId;
        this.modName = modName;
        this.modInstallationDate = modInstallationDate;
        this.modBinary = modBinary;
    }

    public int getModId()
    {
        return modId;
    }

    public String getName()
    {
        return modName;
    }

    public LocalDate getModInstallationDate()
    {
        return modInstallationDate;
    }

    public Blob getModBinary()
    {
        return modBinary;
    }

    @Override
    public String toString()
    {
        return String.format("Mod { %d, %s, Binary: %s }", this.modId, this.modName, this.modBinary);
    }

}
