package migration;

import data.source.GameobjectSource;
import data.target.GameobjectTarget;

import java.sql.Connection;
import java.util.List;

public class GameobjectMigration extends ETL<GameobjectSource, GameobjectTarget>
{
    public GameobjectMigration(Connection source, Connection target)
    {
        super(source, target);
    }

    @Override
    protected List<GameobjectSource> extract()
    {
        return null;
    }

    @Override
    protected List<GameobjectTarget> transform(List<GameobjectSource> extractedData)
    {
        return null;
    }

    @Override
    protected void load(List<GameobjectTarget> transformedData)
    {

    }
}
