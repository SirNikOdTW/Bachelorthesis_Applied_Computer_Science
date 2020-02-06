package data.source;

import data.SourceDataset;

import java.sql.Blob;
import java.time.LocalDate;

public class ModSource implements SourceDataset
{
    private int modId;
    private String name;
    private LocalDate installationDate;
    private Blob binary;

    public ModSource(int modId, String name, LocalDate installationDate, Blob binary)
    {
        this.modId = modId;
        this.name = name;
        this.installationDate = installationDate;
        this.binary = binary;
    }

    public int getModId()
    {
        return modId;
    }

    public String getName()
    {
        return name;
    }

    public LocalDate getInstallationDate()
    {
        return installationDate;
    }

    public Blob getBinary()
    {
        return binary;
    }

    @Override
    public String toString()
    {
        return String.format("Mod { %d, %s, Binary: %s }", this.modId, this.name, this.binary);
    }
}
