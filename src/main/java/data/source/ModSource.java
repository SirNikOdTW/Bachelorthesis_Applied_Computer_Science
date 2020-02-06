package data.source;

import data.SourceDataset;

import java.sql.Blob;
import java.time.LocalDate;

public class ModSource implements SourceDataset
{
    private final int modId;
    private final String name;
    private final LocalDate installationDate;
    private final Blob binary;

    public ModSource(final int modId, final String name, final LocalDate installationDate, final Blob binary)
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
