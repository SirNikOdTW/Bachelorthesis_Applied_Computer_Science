package data.target;

import data.TargetDataset;

import java.sql.Blob;
import java.time.LocalDate;

public class ModTarget implements TargetDataset
{
    private int modId;
    private String modName;
    private LocalDate modInstallationDate;
    private Blob modBinary;

    public ModTarget(int modId, String modName, LocalDate modInstallationDate, Blob modBinary)
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
