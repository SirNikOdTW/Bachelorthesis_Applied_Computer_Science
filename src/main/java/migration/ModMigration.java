package migration;

import data.source.ModSource;
import data.target.ModTarget;

import java.sql.Connection;
import java.util.List;

public class ModMigration extends ETL<ModSource, ModTarget>
{
    public ModMigration(Connection source, Connection target)
    {
        super(source, target);
    }

    @Override
    protected List<ModSource> extract()
    {
        return null;
    }

    @Override
    protected List<ModTarget> transform(List<ModSource> extractedData)
    {
        return null;
    }

    @Override
    protected void load(List<ModTarget> transformedData)
    {

    }
}
